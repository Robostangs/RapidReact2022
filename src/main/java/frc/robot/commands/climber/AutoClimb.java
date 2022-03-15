package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.turret.Protect;
import frc.robot.subsystems.Climber;

public class AutoClimb extends SequentialCommandGroup {
    private static final Climber mClimber = Climber.getInstance();
    private static final Climber.Rotator mRotator = mClimber.getRotator();
    private static final Climber.Hand mHandA;
    private static final Climber.Hand mHandB;
    static {
        final Climber.Hand[] hands = mClimber.getHands();
        mHandA = hands[0];
        mHandB = hands[1];
    }

    public AutoClimb() {
        setName("Auto Climb");
        addCommands(
            new ScheduleCommand(new Protect()),
            new ReleaseElevator(),
                        new WaitUntilCommand(Robot::getProceed),
            new RotateToPosition(Constants.Climber.Rotator.kStartingAngle),
                        new WaitUntilCommand(Robot::getProceed),
            new ProxyScheduleCommand(new DriveToMidBar()),
                        new WaitUntilCommand(Robot::getProceed),
            new CloseHand(mHandA),
                        new WaitUntilCommand(Robot::getProceed),
            new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockUnlockedPositon),
                        new WaitUntilCommand(Robot::getProceed),
            new Climb1Bar(mHandA, mHandB, Constants.Climber.kFirstCGPosition),
                        new WaitUntilCommand(Robot::getProceed),
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .withTimeout(0.5),
                        new WaitUntilCommand(Robot::getProceed),
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new CloseHand(mHandA),
                    new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockLockedPositon),
                    new OpenHand(mHandA)),
                new SetHandLockPosition(mHandB, Constants.Climber.Hand.kClawLockUnlockedPositon)),
                        new WaitUntilCommand(Robot::getProceed),
            new Climb1Bar(mHandB, mHandA, Constants.Climber.kSecondCGPosition),
            // new WaitUntilCommand(Robot::getProceed),
            // new OpenHand(mRotationHandHolder),
                        new WaitUntilCommand(Robot::getProceed),
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .withTimeout(1));
    }
}
