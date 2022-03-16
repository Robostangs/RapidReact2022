package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;

public class AutoClimb extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();
    private final Climber.Rotator mRotator = mClimber.getRotator();
    private final HandHolder mRotationHandHolder = new HandHolder();
    private final HandHolder mGrabHandHolder = new HandHolder();

    private void switchHands() {
        Climber.Hand tempHand = mRotationHandHolder.hand;
        mRotationHandHolder.hand = mGrabHandHolder.hand;
        mGrabHandHolder.hand = tempHand;
    }

    private void initHands() {
        var hands = mClimber.getHands();
        mRotationHandHolder.hand = hands[0];
        mGrabHandHolder.hand = hands[1];
    }

    public AutoClimb() {
        addCommands(
            new ReleaseElevator(),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new RotateToPosition(Constants.Climber.Rotator.kStartingAngle),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new ProxyScheduleCommand(new DriveToMidBar()),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new InstantCommand(this::initHands),
            new CloseHand(mRotationHandHolder),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new SetHandLockPosition(mRotationHandHolder, Constants.Climber.Hand.kClawLockUnlockedPositon),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Climb1Bar(mRotationHandHolder, mGrabHandHolder, Constants.Climber.kFirstCGPosition).andThen(new PrintCommand("Climb1 done")),
            new InstantCommand(() -> mRotator.setNeutralModeCoast()),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new CloseHand(mRotationHandHolder),
            new SetHandLockPosition(mRotationHandHolder, Constants.Climber.Hand.kClawLockLockedPositon),
            new OpenHand(mRotationHandHolder),
            new SetHandLockPosition(mGrabHandHolder, Constants.Climber.Hand.kClawLockUnlockedPositon),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new InstantCommand(() -> mRotator.setNeutralModeBrake()),
            new InstantCommand(this::switchHands),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Climb1Bar(mRotationHandHolder, mGrabHandHolder, Constants.Climber.kSecondCGPosition).andThen(new PrintCommand("Climb2 done")),
            // new WaitUntilCommand(RobotContainer::getClimbProceed),
            // new OpenHand(mRotationHandHolder),
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
            .withTimeout(1),
            new InstantCommand(() -> mRotator.setNeutralModeCoast()));
    }
}
