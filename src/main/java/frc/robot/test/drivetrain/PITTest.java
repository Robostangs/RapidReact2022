package frc.robot.test.drivetrain;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.ShooterMappings;
import frc.robot.commands.AddSmartdashboard;
import frc.robot.commands.climber.ClimbPrep;
import frc.robot.commands.climber.ReleaseElevator;
import frc.robot.commands.drivetrain.TankDrive;
import frc.robot.commands.elevator.RunElevator;
import frc.robot.commands.feeder.ControlManual;
import frc.robot.commands.intake.Active;
import frc.robot.commands.shooter.SetShooterState;
import frc.robot.commands.turret.Protect;
import frc.robot.commands.turret.Search;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class PITTest extends SequentialCommandGroup {
    PowerDistribution pdp = Robot.mPowerDistributionPanel;
    boolean ran = false;


    public PITTest() {
        this.addRequirements(Drivetrain.getInstance(), Turret.getInstance(), Feeder.getInstance(), Shooter.getInstance(), Intake.getInstance());
        setName("PIT Test");
        SmartDashboard.putString("PIT Test", "Test Starting");
    
        this.addCommands(
            new TankDrive(()-> Constants.PitTest.dtVelo, () -> 0.0).withTimeout(1).beforeStarting(new AddSmartdashboard("LeftDt")),
            new TankDrive(()-> 0.0, () -> Constants.PitTest.dtVelo).withTimeout(1).beforeStarting(new AddSmartdashboard("RightDt")),

            new Active().withTimeout(1).beforeStarting(new AddSmartdashboard("Intake")),
            new ControlManual(() -> Constants.Feeder.kBeltSpeed).withTimeout(1).beforeStarting(new AddSmartdashboard("Feeder Belts")),
            new RunElevator().withTimeout(1).beforeStarting(new AddSmartdashboard("Feeder Wheels (Elevator)")),

            new SetShooterState(ShooterMappings.getShooterState(0)).withTimeout(1.5).beforeStarting(new AddSmartdashboard("Shooter")),
            new Search().withTimeout(1).beforeStarting(new AddSmartdashboard("Turret")).andThen(new Protect().withTimeout(1)),

            new ReleaseElevator().withTimeout(2).beforeStarting(new AddSmartdashboard("Climber Elevator")),
            new ClimbPrep().withTimeout(5).beforeStarting(new AddSmartdashboard("Climber Rotate and Claws")),
            new AddSmartdashboard("Test Complete"),
            new InstantCommand(() -> {ran = true;})
        );
        
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Battery Voltage", () -> RobotController.getBatteryVoltage(), null);
        builder.addDoubleProperty("Drivetrain Left Current", () -> pdp.getCurrent(Constants.Drivetrain.kLeftTopID), null);
        builder.addDoubleProperty("Drivetrain Right Current", () -> pdp.getCurrent(Constants.Drivetrain.kRightTopID), null);
        builder.addDoubleProperty("Intake Current", () -> pdp.getCurrent(Constants.IntakeConstants.kIntakeMotorID), null);
        builder.addDoubleProperty("Feeder Belt Current", () -> pdp.getCurrent(Constants.Feeder.kBeltMotorID), null);
        builder.addDoubleProperty("Feeder Green Wheels Current", () -> pdp.getCurrent(Constants.Feeder.kElevatorMotorID), null);
        builder.addDoubleProperty("Turret Current", () -> pdp.getCurrent(Constants.Turret.kRotationMotorID), null);
        builder.addDoubleProperty("Front Shooter Current", () -> pdp.getCurrent(Constants.Shooter.kLeftShooterID), null);
        builder.addDoubleProperty("Back Shooter Current", () -> pdp.getCurrent(Constants.Shooter.kRightShooterID), null);
        builder.addDoubleProperty("Climber Rotation Current", () -> pdp.getCurrent(Constants.Climber.kRotationMotorID), null);

        builder.addDoubleProperty("Turret Closed Loop Error", () -> frc.robot.subsystems.Turret.getInstance().getError(), null);
        builder.addDoubleProperty("Top Shooter Error", () -> Shooter.getInstance().topShooterError(), null);
        builder.addDoubleProperty("Bottom Shooter Error", () -> Shooter.getInstance().bottomShooterError(), null);
        super.initSendable(builder);
    } 

    public boolean didRun() {
        return ran;
    }
}