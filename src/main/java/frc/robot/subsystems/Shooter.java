package frc.robot.subsystems;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    public static Shooter instance;
    public final TalonFX m_leftShooter, m_rightShooter, m_Elevator;
    private PIDController m_leftShooterPID, m_rightShooterPID;
    //private DigitalInput m_shooterInput; -- Get the Sensor from Feeder, that has the shooter sensor

    public static Shooter getInstance() {
        if(instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public Shooter() {
        m_leftShooter = new TalonFX(Constants.Shooter.leftShooterID);
        m_rightShooter = new TalonFX(Constants.Shooter.rightShooterID);
        // m_angleChanger = new TalonFX(Constants.Shooter.angleShooterID);
        m_Elevator = new TalonFX(Constants.Feeder.elevatorMotorID);

        m_leftShooter.configFactoryDefault();
        m_rightShooter.configFactoryDefault();
        // m_angleChanger.configFactoryDefault();
    
        // m_leftShooterPID = new PIDController(Constants.Shooter.leftMotorKP, Constants.Shooter.leftMotorKI, Constants.Shooter.leftMotorKD);
        // m_rightShooterPID = new PIDController(Constants.Shooter.rightMotorKP, Constants.Shooter.rightMotorKI, Constants.Shooter.rightMotorKD);

        // m_angleChangerPID = new PIDController(Constants.Shooter.angleMotorKP, Constants.Shooter.angleMotorKI, Constants.Shooter.angleMotorKD);   
        
        // m_angleChanger.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 5, 7, 100));

        m_leftShooter.config_kP(0, Constants.Shooter.leftMotorKP);
        m_leftShooter.config_kI(0, Constants.Shooter.leftMotorKI);

        m_rightShooter.config_kP(0, Constants.Shooter.rightMotorKP);
        m_rightShooter.config_kI(0, Constants.Shooter.rightMotorKI);

        SmartDashboard.putNumber("RightVelocity", 0);
        SmartDashboard.putNumber("LeftVelocity", 0);
        SmartDashboard.putNumber("AllignmentVelocity", 0);
        SmartDashboard.putNumber("Elevator", 0);
    }

    // @Override
    // public void periodic() {
    //     super.periodic();
    //     setAlignmentPower(m_alignmentPID.calculate(getAlignmentPosition()));
    //     setAnglePower(m_angleChangerPID.calculate(getAnglePosition()));
    //     setLeftShooterPower(m_leftShooterPID.calculate(getLeftShooterPosition()));
    //     setRightShooterPower(m_rightShooterPID.calculate(getRightShooterPosition()));
    // }
    public void setLeftShooterPower(double power) {
        m_leftShooter.set(ControlMode.PercentOutput, power);
    }

    public void setLeftShooterVelo(double velocity) {
        m_leftShooter.set(ControlMode.Velocity, velocity);
    }
    
    public void setRightShooterVelo(double velocity) {
        m_rightShooter.set(ControlMode.Velocity, velocity);
    }

    public void setRightShooterPower(double power) {
        m_rightShooter.set(ControlMode.PercentOutput, power);
    }

    // public void setAnglePower(double power) {
    //     m_angleChanger.set(ControlMode.PercentOutput, power);
    // }

    // public void setAnglePositionPID(double position) {
    //     m_angleChangerPID.setSetpoint(position);
    // }

    public void setElevatorPower(double power) {
        m_Elevator.set(ControlMode.PercentOutput, power);
    }

    public double getLeftVelocity() {
        return m_leftShooter.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return m_rightShooter.getSelectedSensorVelocity();
    }

    // public double getAlignmentPosition() {
    //     return m_Alignment.getActiveTrajectoryPosition();
    // }

    // public double getLeftShooterPosition() {
    //     return m_leftShooter.getActiveTrajectoryPosition();
    // }

    // public double getRightShooterPosition() {
    //     return m_rightShooter.getActiveTrajectoryPosition();
    // }

    // public double getAnglePosition() {
    //     return m_angleChanger.getActiveTrajectoryPosition();
    // }
    // public double getAlignmentEncoder() {

        
    //     return m_Alignment.getActiveTrajectoryPosition();
    // }

    // public double getLeftShooterEncoder() {
    //     return m_leftShooter.getActiveTrajectoryPosition();
    // }

    // public double getRightShooterEncoder() {
    //     return m_rightShooter.getActiveTrajectoryPosition();
    // }

    public void test() {
    //     m_rightShooter.set(ControlMode.PercentOutput, SmartDashboard.getNumber("RightVelocity", 0));
    //     m_leftShooter.set(ControlMode.PercentOutput, SmartDashboard.getNumber("LeftVelocity", 0) * 0.94);
    //    // m_Alignment.set(ControlMode.PercentOutput, SmartDashboard.getNumber("AllignmentVelocity", 0));
    //     m_Elevator.set(ControlMode.PercentOutput, SmartDashboard.getNumber("Elevator", 0));

        SmartDashboard.putNumber("Right Shooter Velo", m_rightShooter.getSelectedSensorVelocity() * 600/2048);
        SmartDashboard.putNumber("Left Shooter Velo", m_leftShooter.getSelectedSensorVelocity() * 600/2048);
    }
}
