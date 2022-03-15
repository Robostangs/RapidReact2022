package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class Climb1Bar extends SequentialCommandGroup {
    private static final Climber mClimber = Climber.getInstance();

    public Climb1Bar(HandHolder rotationHandHolder, HandHolder grabHandHolder, double CGPosition) {
        addRequirements(mClimber);
        setName("Climb 1 Bar");
        addCommands(
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .until(() -> grabHandHolder.hand.getEngaged()), // Rotate to position
            new ParallelDeadlineGroup( // Grab upper bar while holding position
                new CloseHand(grabHandHolder),
                new Rotate(Constants.Climber.Rotator.kClimbHoldSpeed)),
            // new Rotate(Constants.Climber.Rotator.kToCGSpeed) // Rotate to CG
            //     .withInterrupt(() -> mClimber.getRotator().getPosition() <= CGPosition),
            new ParallelDeadlineGroup( // Let go of lower bar
                new OpenHand(rotationHandHolder),
                new Rotate(Constants.Climber.Rotator.kCGHoldSpeed))); // while holding CG
    }

    public Climb1Bar(Climber.Hand rotationHand, Climber.Hand grabHand, double CGPosition) {
        addRequirements(mClimber);
        setName("Climb 1 Bar");
        addCommands(
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .until(() -> grabHand.getEngaged()), // Rotate to position
            new ParallelDeadlineGroup( // Grab upper bar while holding position
                new CloseHand(grabHand),
                new Rotate(Constants.Climber.Rotator.kClimbHoldSpeed)),
            // new Rotate(Constants.Climber.Rotator.kToCGSpeed) // Rotate to CG
            //     .withInterrupt(() -> mClimber.getRotator().getPosition() <= CGPosition),
            new ParallelDeadlineGroup( // Let go of lower bar
                new OpenHand(rotationHand),
                new Rotate(Constants.Climber.Rotator.kCGHoldSpeed))); // while holding CG
    }
}
