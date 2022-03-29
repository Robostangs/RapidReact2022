package frc.robot.test.drivetrain;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.ShooterMappings;
import frc.robot.Constants.Turret;
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
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class PITTest extends SequentialCommandGroup {
    PowerDistribution pdp = new PowerDistribution();


    public PITTest() {
        SmartDashboard.putString("PIT Test", "Test Starting");
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

    @Override
    public void initialize() {
        this.addCommands(
            new ParallelDeadlineGroup(
                new WaitCommand(1), 
                new TankDrive(()-> Constants.PitTest.dtVelo, () -> 0.0)
            ).beforeStarting(new AddSmartdashboard("LeftDt")),

            new ParallelDeadlineGroup(
                new WaitCommand(1), 
                new TankDrive(()-> 0.0, () -> Constants.PitTest.dtVelo)
            ).beforeStarting(new AddSmartdashboard("RightDt")),

            new ParallelDeadlineGroup(
                new WaitCommand(1), 
                new Active()
            ).beforeStarting(new AddSmartdashboard("Intake")),

            new ParallelDeadlineGroup(
                new WaitCommand(1), 
                new ControlManual(() -> Constants.Feeder.kSlowBeltSpeed)
            ).beforeStarting(new AddSmartdashboard("Feeder Belts")),

            new ParallelDeadlineGroup(
                new WaitCommand(1), 
                new RunElevator()
            ).beforeStarting(new AddSmartdashboard("Feeder Wheels (Elevator)")),

            new ParallelDeadlineGroup(
                new WaitCommand(1), 
                new SetShooterState(ShooterMappings.getShooterState(0))
            ).beforeStarting(new AddSmartdashboard("Shooter")),

            new ParallelDeadlineGroup(
                new WaitCommand(1), 
                new Search()
            ).beforeStarting(new AddSmartdashboard("Turret")).andThen(new Protect()),

            new ClimbPrep().beforeStarting(new AddSmartdashboard("Climber Rotate and Claws")),
            new WaitCommand(5),
            new ReleaseElevator().beforeStarting(new AddSmartdashboard("Climber Elevator"))
        );
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putString("PIT Test", "Test Complete");
        super.end(interrupted);
    }

    
}