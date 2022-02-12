package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class TurnToAngle extends CommandBase {
    
    private final Turret m_turret = new Turret().getInstance();
    private final double m_anglePosition;

    public TurnToAngle(double anglePosition) {
        this.addRequirements(m_turret);
        this.setName("Set Turret Angle");
        m_anglePosition = anglePosition;
    }

    @Override
    public void execute() {
        super.execute();
        double currentAngle = m_turret.getMeasurement();
        // double targetAngleInTicks = (m_anglePosition / 360) * 2048; // TODO: Add Smart Turning
        m_turret.setSetpoint(m_anglePosition - currentAngle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
