package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class Climb1Bar extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();
    private boolean isRotationLocked = false;

    public Climb1Bar(HandHolder rotationHandHolder, HandHolder grabHandHolder, double CGPosition) {
        addRequirements(mClimber);
        setName("Climb 1 Bar");
        addCommands(
            new ParallelCommandGroup( // Rotate to position and grab
                new SequentialCommandGroup( // Lock rotation hand
                    new SetHandLockPosition(rotationHandHolder, Constants.Climber.Hand.kClawLockUnlockedPositon),
                    new InstantCommand(() -> isRotationLocked = true)),
                new SequentialCommandGroup( // Rotate and grab upper bar
                    new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                        .until(() -> grabHandHolder.hand.getEngaged()), // Rotate to position
                    new ParallelDeadlineGroup( // Grab upper bar while holding position
                        new CloseHand(grabHandHolder),
                        new Rotate(Constants.Climber.Rotator.kClimbHoldSpeed)),
                    new Rotate(Constants.Climber.Rotator.kToCGSpeed) // Rotate to CG
                        .withInterrupt(() -> mClimber.getRotator().getPosition() >= CGPosition),
                    new Rotate(Constants.Climber.Rotator.kCGHoldSpeed) // Hold CG until lower hand is locked
                        .withInterrupt(() -> isRotationLocked))),
            new ParallelCommandGroup( // Let go of lower bar
                new Rotate(Constants.Climber.Rotator.kCGHoldSpeed), // while holding CG
                new OpenHand(rotationHandHolder)));
    }
}
