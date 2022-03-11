package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClawRighta extends CommandBase {

    private final double mPosition;
    private final Climber mClimber = Climber.getInstance();

    public ClawRighta(double position) {
        addRequirements(mClimber);
        setName("Right Claw");
        mPosition = position;
    }

    @Override
    public void initialize() {
        mClimber.setLeftClawPosition(mPosition);
    }
}
