package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class setShooterPower extends InstantCommand{
    
    private Shooter m_Shooter;
    private double m_leftShooterPower, m_rightShooterPower;

    public setShooterPower(double leftShooterPower, double rightShooterPower) {
        m_Shooter = Shooter.getInstance();
        this.addRequirements(m_Shooter);
        
        m_leftShooterPower = leftShooterPower;
        m_rightShooterPower = rightShooterPower;
    }

    @Override
    public void initialize() {
        m_Shooter.setLeftShooterVelo(m_leftShooterPower);
        m_Shooter.setRightShooterVelo(m_rightShooterPower);
    }

}
