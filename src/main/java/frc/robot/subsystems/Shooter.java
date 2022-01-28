package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    public static Shooter instance;
    public final TalonFX m_Alignment, m_leftShooter, m_rightShooter;
    private SlotConfiguration m_allMotorPID;
    //private DigitalInput m_shooterInput; -- Get the Sensor from Feeder, that has the shooter sensor

    public static Shooter getInstance() {
        if(instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public Shooter() {
        m_Alignment = new TalonFX(Constants.Shooter.alignmentID);
        m_leftShooter = new TalonFX(Constants.Shooter.leftShooterID);
        m_rightShooter = new TalonFX(Constants.Shooter.rightShooterID);
        
        m_allMotorPID = new SlotConfiguration();
        m_allMotorPID.kP = Constants.Shooter.motorKP; 
        m_allMotorPID.kI = Constants.Shooter.motorKI;
        m_allMotorPID.kD = Constants.Shooter.motorKD;

        m_Alignment.configureSlot(m_allMotorPID);
        m_leftShooter.configureSlot(m_allMotorPID);
        m_rightShooter.configureSlot(m_allMotorPID);

        m_Alignment.selectProfileSlot(1, 0);
        m_leftShooter.selectProfileSlot(1, 0);
        m_rightShooter.selectProfileSlot(1, 0);
    
    
    }

    public void setAlignmentPower(double power) {
        m_Alignment.set(ControlMode.Velocity, power);
    }

    
    public void setLeftShooterPower(double power) {
        m_leftShooter.set(ControlMode.Velocity, power);
    }
    
    public void setRightShooterPower(double power) {
        m_rightShooter.set(ControlMode.Velocity, power);
    }

    public double getAlignmentVelocity() {
        return m_Alignment.getActiveTrajectoryVelocity();
    }

    public double getLeftShooterVelocity() {
        return m_leftShooter.getActiveTrajectoryVelocity();
    }

    public double getRightShooterVelocity() {
        return m_rightShooter.getActiveTrajectoryVelocity();
    }

    // public double getAlignmentEncoder() {
    //     return m_Alignment.getActiveTrajectoryPosition();
    // }

    // public double getLeftShooterEncoder() {
    //     return m_leftShooter.getActiveTrajectoryPosition();
    // }

    // public double getRightShooterEncoder() {
    //     return m_rightShooter.getActiveTrajectoryPosition();
    // }
}
