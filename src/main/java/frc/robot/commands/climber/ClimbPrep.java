package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ClimbPrep extends ParallelCommandGroup {
    private final Climber mClimber = Climber.getInstance();

    private final class PrepHand extends SequentialCommandGroup {
        private PrepHand(Climber.Hand hand) {
            addCommands(
                new PrintCommand("Running PrepHand"),
                new CallibrateHand(hand),
                new SetHandLockPosition(hand, Constants.Climber.Hand.kClawLockLockedPositon),
                new PrintCommand("Finished PrepHand"));
        }
    }

    public ClimbPrep() {
        addRequirements(mClimber);
        setName("Climb Prep");
        final Climber.Hand[] hands = mClimber.getHands();
        addCommands(
            new RotateToPosition(Constants.Climber.Rotator.kHorizontalAngle),
            new SequentialCommandGroup(
                new WaitCommand(Constants.Climber.kWaitBeforePrep),
                new ParallelCommandGroup(
                    new PrepHand(hands[0]),
                    new PrepHand(hands[1]))));
    }
}
