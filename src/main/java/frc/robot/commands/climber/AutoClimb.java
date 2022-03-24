
package frc.robot.commands.climber;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.turret.Protect;
import frc.robot.subsystems.Climber;

public class AutoClimb extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();
    private final Climber.Rotator mRotator = mClimber.getRotator();
    private final HandHolder mRotationHandHolder = new HandHolder();
    private final HandHolder mGrabHandHolder = new HandHolder();
    private boolean interrupt = false;

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

    public AutoClimb(Supplier<Double> wiggleSupplier, Supplier<Boolean> limitSwitchInterrupt) {
        setName("Auto Climb");
        addCommands(
            new Protect().withTimeout(1),
            new ReleaseElevator()
                .andThen(new PrintCommand("Elevator Released")),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new RotateToPosition(Constants.Climber.Rotator.kStartingAngle)
                .andThen(new PrintCommand("Rotated to initial position")),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new ProxyScheduleCommand(new DriveToMidBar().withInterrupt(limitSwitchInterrupt::get))
                .andThen(new PrintCommand("Driven to mid bar")),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new InstantCommand(this::initHands),
            new CloseHand(mRotationHandHolder)
                .andThen(new PrintCommand("Closed first rotation hand")),
            new SetHandLockPosition(mRotationHandHolder, Constants.Climber.Hand.kClawLockUnlockedPositon)
                .andThen(new PrintCommand("Unlocked first rotation hand")),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Climb1Bar(mRotationHandHolder, mGrabHandHolder, Constants.Climber.kFirstCGPosition, wiggleSupplier, limitSwitchInterrupt)
                .andThen(new PrintCommand("Climb1 done")),
            // new InstantCommand(() -> mRotator.setNeutralModeCoast()),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new CloseHand(mRotationHandHolder)
                .andThen(new PrintCommand("Closed rotation hand")),
            new SetHandLockPosition(mRotationHandHolder, Constants.Climber.Hand.kClawLockLockedPositon)
                .andThen(new PrintCommand("Locked rotaton hand")),
            new OpenHand(mRotationHandHolder)
                .andThen(new PrintCommand("Opened rotation hand")),
            new SetHandLockPosition(mGrabHandHolder, Constants.Climber.Hand.kClawLockUnlockedPositon)
                .andThen(new PrintCommand("Unlocked grab hand")),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new InstantCommand(() -> mRotator.setNeutralModeBrake())
                .andThen(new PrintCommand("Rotator brake")),
            new InstantCommand(this::switchHands),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Climb1Bar(mRotationHandHolder, mGrabHandHolder, Constants.Climber.kSecondCGPosition, wiggleSupplier, limitSwitchInterrupt)
                .andThen(new PrintCommand("Climb2 done")),
            // new WaitUntilCommand(RobotContainer::getClimbProceed),
            // new OpenHand(mRotationHandHolder),
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .withTimeout(1)
                .andThen(new PrintCommand("Climber rotated to final position")),
            new InstantCommand(() -> mRotator.setNeutralModeCoast())
                .andThen(new PrintCommand("Rotator coast")));
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }
}
