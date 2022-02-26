package frc.robot.commands.Shooter;

import java.nio.file.WatchEvent;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SynchronousInterrupt.WaitResult;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.*;

public class shoot extends CommandBase{
    private final Shooter mShooter = Shooter.getInstance();
    private final double m_allignmentPower, m_leftShooterPower, m_rightShooterPower;

    public shoot(double allignmentPower, double leftShooterPower, double rightShooterPower) {
        this.addRequirements(mShooter);
        this.setName("Shoot");
        m_allignmentPower = allignmentPower;
        m_leftShooterPower = leftShooterPower;
        m_rightShooterPower = rightShooterPower;  
        
        
    }
    
    @Override
    public void initialize() {
        mShooter.setRightShooterPower(m_rightShooterPower);
        mShooter.setLeftShooterPower(m_leftShooterPower);
        // mShooter.setAnglePower(m_allignmentPower);
        
        mShooter.setElevatorPower(-1);

    }

    @Override
    public void end(boolean interrupted) {
        mShooter.setRightShooterPower(0);
        mShooter.setLeftShooterPower(0);
        // mShooter.setAnglePower(0);
        mShooter.setElevatorPower(0);
    } 
    @Override
    public boolean isFinished() {
        return false;
    }
}
