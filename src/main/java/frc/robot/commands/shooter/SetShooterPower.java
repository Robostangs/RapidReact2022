package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class SetShooterPower extends InstantCommand {

    private final Shooter mShooter;
    private final double mLeftShooterPower, mRightShooterPower;

    public SetShooterPower(double leftShooterPower, double rightShooterPower) {
        mShooter = Shooter.getInstance();
        addRequirements(mShooter);

        mLeftShooterPower = leftShooterPower;
        mRightShooterPower = rightShooterPower;
    }

    @Override
    public void initialize() {
        mShooter.setBottomShooterVelocity(mLeftShooterPower);
        mShooter.setTopShooterVelocity(mRightShooterPower);
    }
}
