package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class SetElevatorPower extends InstantCommand {

    private final Shooter mShooter = Shooter.getInstance();
    private final double mPower;

    public SetElevatorPower(double power) {
        addRequirements(mShooter);
        mPower = power;
    }

    @Override
    public void initialize() {
        mShooter.setElevatorPower(mPower);
    }
}
