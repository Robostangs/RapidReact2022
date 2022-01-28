package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class angle extends CommandBase {
    private final Shooter mShooter = Shooter.getInstance();
    private final double m_anglePosition;

    public angle(double position) {
        this.addRequirements(mShooter);
        this.setName("Set Angle");
        m_anglePosition = position;
    }

    @Override
    public void execute() {
        double currentPosition = mShooter.getAnglePosition();
        double change = m_anglePosition - currentPosition / Constants.Shooter.ticksPerDegree;
        mShooter.setAnglePosition(change);               
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
