package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SetShooterState extends CommandBase {
    private final Shooter mShooter = Shooter.getInstance();
    protected Shooter.State mState;

    public SetShooterState(Shooter.State state) {
        addRequirements(mShooter);
        setName("Set Shooter State");
        mState = state;
    }

    @Override
    public void execute() {
        mShooter.setState(mState);
    }

    @Override
    public boolean isFinished() {
        return mShooter.getState().equals(mState);
    }

    @Override
    public void end(boolean interrupted) {
        mShooter.setState(new Shooter.State(0));
    }
}
