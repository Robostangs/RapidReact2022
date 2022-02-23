package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class shoot extends CommandBase{
    private final Shooter mShooter = Shooter.getInstance();
    private final double m_allignmentPower, m_leftShooterPower, m_rightShooterPower, m_elevatorShooter;
    private final Boolean m_shoot;

    public shoot(double allignmentPower, double leftShooterPower, double rightShooterPower, double elevatorShooter,Boolean shoot) {
        this.addRequirements(mShooter);
        this.setName("Shoot");
        m_allignmentPower = allignmentPower;
        m_leftShooterPower = leftShooterPower;
        m_rightShooterPower = rightShooterPower;        
        m_elevatorShooter = elevatorShooter;
        m_shoot = shoot;
    }

    @Override
    public void execute() {
        if(m_shoot) {
            mShooter.setRightShooterPower(m_allignmentPower);
            mShooter.setLeftShooterPower(m_leftShooterPower);
            mShooter.setAlignmentPower(m_rightShooterPower);
            mShooter.setElevatorPower(m_elevatorShooter);
        }
    } 
    @Override
    public boolean isFinished() {
        return false;
    }
}
