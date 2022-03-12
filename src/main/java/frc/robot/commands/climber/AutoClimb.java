package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climber;

public class AutoClimb extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();
    private final HandHolder mRotationHandHolder = new HandHolder();
    private final HandHolder mGrabHandHolder = new HandHolder();

    private void switchHands() {
        Climber.Hand tempHand = mRotationHandHolder.hand;
        mRotationHandHolder.hand = mGrabHandHolder.hand;
        mGrabHandHolder.hand = tempHand;
    }

    private void initHands() {
        mRotationHandHolder.hand = mClimber.getEngagedHand();
        mGrabHandHolder.hand = mClimber.getDisengagedHand();
    }

    public AutoClimb() {
        final InstantCommand switchHandsCommand = new InstantCommand(() -> switchHands());
        addCommands(
            new Rotate(),
            new ProxyScheduleCommand(new DriveToMidBar()),
            new InstantCommand(this::initHands),
            new CloseHand(mRotationHandHolder),
            switchHandsCommand,
            new ReleaseElevator(),
            new CloseHand(mGrabHandHolder)
        );
    }
}
