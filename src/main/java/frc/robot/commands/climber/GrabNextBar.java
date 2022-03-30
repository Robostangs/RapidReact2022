package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class GrabNextBar extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();

    public GrabNextBar(Climber.Hand grabHand) {
        addRequirements(mClimber);
        setName("Climb 1 Bar");
        addCommands(
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .withInterrupt(() -> (grabHand.getEngaged())), // Rotate to position
            new ParallelDeadlineGroup( // Grab upper bar while holding position
                new CloseHand(grabHand),
                new Rotate(Constants.Climber.Rotator.kClimbHoldSpeed)));
    }
}
