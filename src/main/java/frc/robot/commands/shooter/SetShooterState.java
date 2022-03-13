package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SetShooterState extends CommandBase {
    private final Shooter mShooter = Shooter.getInstance();
    private final Shooter.State mState;

    public SetShooterState(Shooter.State state) {
        addRequirements(mShooter);
        mState = state;
    }

    @Override
    public void initialize() {
        mShooter.setState(mState);
    }

    @Override
    public boolean isFinished() {
        return mShooter.getState().equals(mState);
    }
}
