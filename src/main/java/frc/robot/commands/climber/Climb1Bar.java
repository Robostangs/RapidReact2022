package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class Climb1Bar extends CommandBase {
    private final Climber mClimber = Climber.getInstance();

    public Climb1Bar(Climber.Hand rotationHand, Climber.Hand grabHand) {
        addRequirements(mClimber);
        setName("Climb 1 Bar");
    }
}
