package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ClimbPrep extends ParallelCommandGroup {
    private static final Climber mClimber = Climber.getInstance();

    private final class PrepHand extends SequentialCommandGroup {
        private final Climber.Hand mHand;

        private PrepHand(Climber.Hand hand) {
            mHand = hand;
            setName("Prep Hand");
            addCommands(
                new PrintCommand("Running PrepHand"),
                new ConditionalCommand(
                    new OpenHand(mHand),
                    new InstantCommand(),
                    () -> mHand.getCallibrationStatus() != Climber.HandCallibrationStatus.kCalibrated),
                new CloseHand(mHand),
                new SetHandLockPosition(mHand, Constants.Climber.Hand.kClawLockLockedPositon),
                new OpenHand(mHand),
                new PrintCommand("Finished PrepHand"));
        }
    }

    public ClimbPrep() {
        addRequirements(mClimber);
        final Climber.Hand[] hands = mClimber.getHands();
        setName("Climb Prep");
        addCommands(
            new RotateToPosition(Constants.Climber.Rotator.kHorizontalAngle),
            new SequentialCommandGroup(
                new WaitCommand(Constants.Climber.kWaitBeforePrep),
                new ParallelCommandGroup(
                    new PrepHand(hands[0]),
                    new PrepHand(hands[1]))));
    }

    @Override
    public void initialize() {
        super.initialize();
        if(mClimber.getState() == Climber.State.kWaiting) {
            mClimber.advanceState();
        } else {
            cancel();
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        if(interrupted) {
            mClimber.setState(Climber.State.kWaiting);
        } else {
            mClimber.advanceState();
        }
    }
}
