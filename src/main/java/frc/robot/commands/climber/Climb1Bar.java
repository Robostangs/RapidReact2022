package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climber;

public class Climb1Bar extends SequentialCommandGroup {
    private final Climber mClimber = Climber.getInstance();

    public Climb1Bar(HandHolder rotationHandHolder, HandHolder grabHandHolder) {
        addRequirements(mClimber);
        setName("Climb 1 Bar");
        this.addCommands(
            new Rotate().until(() -> (grabHandHolder.hand.getEngaged())),
            new CloseHand(grabHandHolder),
            new OpenHand(rotationHandHolder));
    }
}
