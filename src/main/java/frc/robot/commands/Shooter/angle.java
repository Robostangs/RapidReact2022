package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class angle extends InstantCommand {
    private final Shooter mShooter = Shooter.getInstance();
    private final double m_anglePosition;

    public angle(double position) {
        this.addRequirements(mShooter);
        this.setName("Set Angle");
        m_anglePosition = position;
    }

    @Override
    public void execute() {
        mShooter.setAnglePositionPID(m_anglePosition);                  
    }
}
