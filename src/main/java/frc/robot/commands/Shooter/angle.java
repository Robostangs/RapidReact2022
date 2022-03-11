package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class Angle extends InstantCommand {
    private final Shooter mShooter = Shooter.getInstance();
    private final double mAngleSetpoint;

    public Angle(double position) {
        addRequirements(mShooter);
        setName("Set Angle");
        mAngleSetpoint = position;
    }

    @Override
    public void execute() {
        mShooter.setHoodPositionPID(mAngleSetpoint);
    }
}
