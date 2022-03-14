package frc.robot.commands.shooter;

import java.util.function.Supplier;

import frc.robot.subsystems.Shooter;

public class SetVariableShooterState extends SetShooterState {
    private final Supplier<Shooter.State> mStateSupplier;

    public SetVariableShooterState(Supplier<Shooter.State> stateSupplier) {
        super(null);
        mStateSupplier = stateSupplier;
    }

    @Override
    public void execute() {
        mState = mStateSupplier.get();
        super.execute();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
