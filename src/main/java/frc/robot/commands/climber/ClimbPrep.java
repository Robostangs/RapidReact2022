package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ClimbPrep extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();

    private final class PrepHand extends SequentialCommandGroup {
        private final Climber.Hand mHand;

        private PrepHand(Climber.Hand hand) {
            mHand = hand;
            addCommands(
                new OpenHand(mHand),
                new InstantCommand(mHand::zeroClawEncoder),
                new CloseHand(mHand),
                new SetHandLockPosition(mHand, Constants.Climber.Hand.kClawLockLockedPositon),
                new OpenHand(mHand));
        }   
    }

    public ClimbPrep() {
        addRequirements(mClimber);
        final Climber.Hand[] hands = mClimber.getHands();
        addCommands(
            new RotateToPosition(Constants.Climber.Rotator.kShortAngle),
            new ParallelCommandGroup(
                new PrepHand(hands[0]),
                new PrepHand(hands[1])));
    }

    @Override
    public void initialize() {
        super.initialize();
        mClimber.setState(Climber.State.kPriming);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        if(!interrupted) {
            mClimber.setState(Climber.State.kPrimed);
        }
    }
}
