package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClawLeft extends CommandBase {

    private final double mPosition;
    private final Climber mClimber = Climber.getInstance();

    public ClawLeft(double position) {
        addRequirements(mClimber);
        setName("Left Claw");
        mPosition = position;
    }

    @Override
    public void initialize() {
        mClimber.setLeftClawPosition(mPosition);
    }
}
