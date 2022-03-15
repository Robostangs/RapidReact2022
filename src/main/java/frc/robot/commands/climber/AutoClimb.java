package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.turret.Protect;
// import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Turret;

public class AutoClimb extends SequentialCommandGroup {

    private final Climber mClimber = Climber.getInstance();
    private final Drivetrain mDrivetrain = Drivetrain.getInstance();
    private final Turret mTurret = Turret.getInstance();

    public AutoClimb() {
        addRequirements(mClimber, mDrivetrain, mTurret);
        setName("Auto Climb");

        addCommands(
            // new DriveDistance(500),
            new Protect(),
            new SpinClimber(Constants.Climber.kMoveClimberSpeed),
            new WaitCommand(2));

        if (mClimber.getRightClawSensor()) {
            addCommands(
                new SpinClimber(0),
                new ClawRight(Constants.Climber.kClawMoveConstant),
                new WaitCommand(0.5));
            // notHeld = new DigitalInput(Constants.Climber.leftClawSensorID);
            // held = new DigitalInput(Constants.Climber.rightClawSensorID);
        } else if (mClimber.getLeftClawSensor()) {
            addCommands(
                new SpinClimber(0),
                new ClawLeft(Constants.Climber.kClawMoveConstant),
                new WaitCommand(0.5));
            // notHeld = new DigitalInput(Constants.Climber.rightClawSensorID);
            // held = new DigitalInput(Constants.Climber.leftClawSensorID);
        }
        addCommands(new SpinClimber(Constants.Climber.kClawMoveConstant));
    }
}
