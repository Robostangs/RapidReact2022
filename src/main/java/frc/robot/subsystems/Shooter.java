package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.commands.Shooter.home;

public class Shooter extends SubsystemBase {
    public static Shooter instance;
    public final TalonFX m_leftShooter, m_rightShooter, m_Elevator, m_angleChanger;
    public ColorSensorV3 m_colorSensor;
    private boolean isHomed;
    enum cargoColor {
        blue,
        red, 
        none 
    }
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
        m_angleChanger = new TalonFX(Constants.Shooter.angleShooterID);
        m_Elevator = new TalonFX(Constants.Feeder.elevatorMotorID);
        m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

        m_leftShooter.configFactoryDefault();
        m_rightShooter.configFactoryDefault();
        m_angleChanger.configFactoryDefault();

        m_leftShooter.config_kP(0, Constants.Shooter.leftMotorKP);
        m_leftShooter.config_kI(0, Constants.Shooter.leftMotorKI);
        m_leftShooter.config_kD(0, Constants.Shooter.leftMotorKD);
        m_leftShooter.configMaxIntegralAccumulator(0, Constants.Shooter.leftMotorIntegralAccumulation);
        m_leftShooter.config_kF(0, Constants.Shooter.leftMotorKF);        
        
        m_rightShooter.config_kP(0, Constants.Shooter.leftMotorKP);
        m_rightShooter.config_kI(0, Constants.Shooter.leftMotorKI);
        m_rightShooter.config_kD(0, Constants.Shooter.leftMotorKD);
        m_rightShooter.configMaxIntegralAccumulator(0, Constants.Shooter.leftMotorIntegralAccumulation);
        m_rightShooter.config_kF(0, Constants.Shooter.leftMotorKF);        
        
        m_angleChanger.configNeutralDeadband(0);
        m_angleChanger.setNeutralMode(NeutralMode.Brake);
        m_angleChanger.config_kP(0, Constants.Shooter.angleMotorKP);
        m_angleChanger.config_kI(0, Constants.Shooter.angleMotorKI);
        m_angleChanger.config_kD(0, Constants.Shooter.angleMotorKD);
        m_angleChanger.config_IntegralZone(0, Constants.Shooter.angleMotorIZone);
        m_angleChanger.configReverseSoftLimitThreshold(Constants.Shooter.angleMotorReverseLimit);
        m_angleChanger.configForwardSoftLimitThreshold(0);

        SmartDashboard.putNumber("LeftVelo", 0);
        SmartDashboard.putNumber("RightVelo", 0);
        SmartDashboard.putNumber("Hood Position", 0);
        SmartDashboard.putNumber("Elevator Speed", 0);

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

    public cargoColor getBallColor() {
        if(m_colorSensor.getRed() > m_colorSensor.getBlue()) {
            return cargoColor.red;
        } else if(m_colorSensor.getRed() < m_colorSensor.getBlue()){
            return cargoColor.blue;
        } else {
            return cargoColor.none;
        }
    }

    public boolean getHoodLimitSwitch() {
        return m_angleChanger.isFwdLimitSwitchClosed() == 1;
    }

    public void setLeftShooterVelo(double velocity) {
        m_leftShooter.set(ControlMode.Velocity, velocity/ (600.0/2048.0));
    }
    
    public void setRightShooterVelo(double velocity) {
        m_rightShooter.set(ControlMode.Velocity, velocity/ (600.0/2048.0));
    }

    public void setRightShooterPower(double power) {
        m_rightShooter.set(ControlMode.PercentOutput, power);
    }

    public void setAnglePower(double power) {
        m_angleChanger.set(ControlMode.PercentOutput, power);
    }

    public void setAnglePositionPID(double position) {
        m_angleChanger.set(ControlMode.Position, position * (-8192/90), DemandType.ArbitraryFeedForward, 0.04);
    }

    public void setElevatorPower(double power) {
        m_Elevator.set(ControlMode.PercentOutput, power);
    }

    public double getLeftVelocity() {
        return m_leftShooter.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return m_rightShooter.getSelectedSensorVelocity();
    }

    public void setSoftLimitEnable(boolean value) {
        m_angleChanger.configForwardSoftLimitEnable(value);
        m_angleChanger.configReverseSoftLimitEnable(value);
    }

    public void setClearPosition(boolean value) {
        m_angleChanger.configClearPositionOnLimitF(value, 100);
    }

    public void setMaxSpeed(double speed) {
        m_angleChanger.configPeakOutputForward(speed);
        m_angleChanger.configPeakOutputReverse(-2*speed);
    }
    
    public boolean getForwardLimit() {
        return m_angleChanger.isFwdLimitSwitchClosed() == 1;
    }

    public void setHomed(boolean value) {
        isHomed = value;
    }

    public void resetHoodEncoder() {
        m_angleChanger.setSelectedSensorPosition(0);
    }

    @Override
    public void periodic() {
        if(m_angleChanger.hasResetOccurred()) {
            isHomed = false;
        }
        if(!isHomed){
            new home().schedule(); 
        }
    }

    // @Override
    // public void periodic() {
    //     SmartDashboard.putNumber("Distance Limelight", Utils.dist(Limelight.getTy()));
    //     m_leftShooter.set(ControlMode.Velocity, (SmartDashboard.getNumber("LeftVelo", 0.0) / (600.0/2048.0)));
    //     m_rightShooter.set(ControlMode.Velocity, (SmartDashboard.getNumber("RightVelo", 0.0) / (600.0/2048.0)));
    //     // setAnglePositionPID(SmartDashboard.getNumber("Hood Position", 0));
    //     setElevatorPower(SmartDashboard.getNumber("Elevator Speed", 0));    
    // }
}
