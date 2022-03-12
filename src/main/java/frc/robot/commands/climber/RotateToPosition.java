package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class RotateToPosition extends CommandBase {
    private final Climber mClimber = Climber.getInstance();
    private final double mPosition;

    public RotateToPosition(double position) {
        addRequirements(mClimber);
        setName("Rotate Climber");
        mPosition = position;
    }

    @Override
    public void initialize() {
        mClimber.setRotationMotorPosition(mPosition);
    }

    @Override
    public boolean isFinished() {
        return mClimber.atState(mPosition, 0);
    }

    @Override
    public void end(boolean interrupted) {
        mClimber.setRotationMotorPower(0);
    }
}
