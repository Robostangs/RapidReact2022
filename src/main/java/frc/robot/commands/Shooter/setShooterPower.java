package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class setShooterPower extends InstantCommand{
    
    private Shooter m_Shooter;
    private double m_leftShooterPower, m_rightShooterPower;

    public setShooterPower(double leftShooterPower, double rightShooterPower) {
        m_Shooter = Shooter.getInstance();
        
        m_leftShooterPower = leftShooterPower;
        m_rightShooterPower = rightShooterPower;
    }

    @Override
    public void initialize() {
        m_Shooter.setLeftShooterPower(m_leftShooterPower);
        m_Shooter.setRightShooterPower(m_rightShooterPower);
    }

}
