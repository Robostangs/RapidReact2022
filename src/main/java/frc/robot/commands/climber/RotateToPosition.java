package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class RotateToPosition extends CommandBase {
    private final Climber.Rotator mRotator = Climber.getInstance().getRotator();
    private final double mPosition;

    public RotateToPosition(double position) {
        addRequirements(mRotator);
        setName("Rotate Climber to Position");
        mPosition = position;
    }

    @Override
    public void initialize() {
        mRotator.setPosition(mPosition);
    }

    @Override
    public boolean isFinished() {
        return mRotator.atState(mPosition, 0);
    }

    @Override
    public void end(boolean interrupted) {
        mRotator.setPower(0);
    }
}
