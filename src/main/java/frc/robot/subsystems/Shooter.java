package frc.robot.subsystems;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    public static Shooter instance;
    public final TalonFX m_Alignment, m_leftShooter, m_rightShooter, m_angleChanger, m_Elevator;
    private PIDController m_alignmentPID, m_leftShooterPID, m_rightShooterPID, m_angleChangerPID;
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
        m_angleChanger = new TalonFX(Constants.Shooter.angleShooterID);
        m_Elevator = new TalonFX(Constants.Feeder.elevatorMotorID);

        m_Alignment.configFactoryDefault();
        m_leftShooter.configFactoryDefault();
        m_rightShooter.configFactoryDefault();
        m_angleChanger.configFactoryDefault();
    
        m_alignmentPID = new PIDController(Constants.Shooter.alignmentMotorKP, Constants.Shooter.alignmentMotorKI, Constants.Shooter.alignmentMotorKD);
        m_leftShooterPID = new PIDController(Constants.Shooter.leftMotorKP, Constants.Shooter.leftMotorKI, Constants.Shooter.leftMotorKD);
        m_rightShooterPID = new PIDController(Constants.Shooter.rightMotorKP, Constants.Shooter.rightMotorKI, Constants.Shooter.rightMotorKD);
        m_angleChangerPID = new PIDController(Constants.Shooter.angleMotorKP, Constants.Shooter.angleMotorKI, Constants.Shooter.angleMotorKD);   
        
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

    public void setAlignmentPower(double power) {
        m_Alignment.set(ControlMode.PercentOutput, power);
    }
    
    public void setLeftShooterPower(double power) {
        m_leftShooter.set(ControlMode.PercentOutput, power);
    }
    
    public void setRightShooterPower(double power) {
        m_rightShooter.set(ControlMode.PercentOutput, power);
    }

    public void setAnglePower(double power) {
        m_angleChanger.set(ControlMode.PercentOutput, power);
    }

    public void setAnglePositionPID(double position) {
        m_alignmentPID.setSetpoint(position);
    }

    public void setElevatorPower(double power) {
        m_Elevator.set(ControlMode.PercentOutput, power);
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
        m_rightShooter.set(ControlMode.PercentOutput, SmartDashboard.getNumber("RightVelocity", 0));
        m_leftShooter.set(ControlMode.PercentOutput, SmartDashboard.getNumber("LeftVelocity", 0));
       // m_Alignment.set(ControlMode.PercentOutput, SmartDashboard.getNumber("AllignmentVelocity", 0));
        m_Elevator.set(ControlMode.PercentOutput, SmartDashboard.getNumber("Elevator", 0));

        SmartDashboard.putNumber("Right Shooter Velo", m_rightShooter.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Left Shooter Velo", m_leftShooter.getSelectedSensorVelocity());
    }
}
