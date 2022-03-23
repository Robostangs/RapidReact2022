package frc.robot.commands.climber;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class Climb1Bar extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();

    public Climb1Bar(HandHolder rotationHandHolder, HandHolder grabHandHolder, double CGPosition, Supplier<Double> wiggleSupplier, Supplier<Boolean> limitSwitchInterrupt) {
        addRequirements(mClimber);
        setName("Climb 1 Bar");
        addCommands(
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .withInterrupt(() -> (grabHandHolder.hand.getEngaged() || limitSwitchInterrupt.get())), // Rotate to position
            new ParallelDeadlineGroup( // Grab upper bar while holding position
                new CloseHand(grabHandHolder),
                new Rotate(Constants.Climber.Rotator.kClimbHoldSpeed)),
            // new Rotate(Constants.Climber.Rotator.kToCGSpeed) // Rotate to CG
            //     .withInterrupt(() -> mClimber.getRotator().getPosition() <= CGPosition),
            new ParallelDeadlineGroup( // Let go of lower bar
                new OpenHand(rotationHandHolder).andThen(new PrintCommand("Let go done")).withInterrupt(limitSwitchInterrupt::get),
                new RotateWithWiggle(Constants.Climber.Rotator.kCGHoldSpeed, wiggleSupplier))); // while holding CG
    }
}
