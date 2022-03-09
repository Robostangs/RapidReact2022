package frc.robot.subsystems;

import java.security.cert.TrustAnchor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.Constants.Drivetrain;
import frc.robot.commands.Turret.goHome;

public class Turret extends SubsystemBase {
    private final TalonFX rotationMotor;
    public static Turret instance;
    public static DigitalInput m_homeSensorOn, m_homeSensorOff;
    private boolean isHomed;
    private double filter;
    private frc.robot.subsystems.Drivetrain mDrivetrain;
    //private TalonFXSensorCollection m_sensorCollection;

    public static Turret getInstance() {
        if(instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    public Turret() {
        rotationMotor = new TalonFX(Constants.Turret.rotationMotorID);
        rotationMotor.setNeutralMode(NeutralMode.Brake);
        rotationMotor.configFactoryDefault();
        
        rotationMotor.setNeutralMode(NeutralMode.Brake);
        rotationMotor.config_kP(0, Constants.Turret.rotationMotorKp);
        rotationMotor.config_kI(0, Constants.Turret.rotationMotorKi);
        rotationMotor.config_IntegralZone(0, Constants.Turret.rotationMotorIZone);
        rotationMotor.configForwardSoftLimitThreshold(Constants.Turret.rotationMotorMax - Constants.Turret.rotationMotorSoftLimitOffset);
        rotationMotor.configReverseSoftLimitThreshold(Constants.Turret.rotationMotorSoftLimitOffset);
        rotationMotor.configNeutralDeadband(0);

        filter = 0;
        isHomed = false;
        SmartDashboard.putNumber("Turret", 0);
        SmartDashboard.putNumber("Yaw Feed", 0);

        mDrivetrain = frc.robot.subsystems.Drivetrain.getInstance();
        
    }

    public void setClearPosition(boolean value) {
        rotationMotor.configClearPositionOnLimitR(value, 100);
    }
    
    public void setSpeed(double speed) {
        rotationMotor.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, mDrivetrain.getGyroRate() * Constants.Turret.rotationMotorFF);
    }

    public void setPosition(double position) {
        rotationMotor.set(ControlMode.Position, position, DemandType.ArbitraryFeedForward, mDrivetrain.getGyroRate() * Constants.Turret.rotationMotorFF);
    }

    public double getTurrentPosition() {
        return rotationMotor.getSelectedSensorPosition();
    }

    public double getTurrentAngle() {
        return (rotationMotor.getSelectedSensorPosition() - Constants.Turret.backPosition) / ((Constants.Turret.leftNinety - Constants.Turret.backPosition)/90);
    }

    public double getTurretVelocity() {
        return rotationMotor.getSelectedSensorVelocity();
    }

    public void setMaxSpeed(double speed) {
        rotationMotor.configPeakOutputForward(speed);
        rotationMotor.configPeakOutputReverse(-speed);
    }

    public void setAngle(double angle) {
        setPosition(Utils.saturate(
            ((angle * (Constants.Turret.leftNinety - Constants.Turret.backPosition)/90) +  Constants.Turret.backPosition),
            Constants.Turret.rotationMotorSoftLimitOffset,
            (Constants.Turret.rotationMotorMax - Constants.Turret.rotationMotorSoftLimitOffset)
        ));
    }

    public void limelightSetAngle(double angle) {
        double currentAngle = (rotationMotor.getSelectedSensorPosition() - Constants.Turret.backPosition) / ((Constants.Turret.leftNinety - Constants.Turret.backPosition)/90);
        SmartDashboard.putNumber("angle", currentAngle);
        double target = angle + currentAngle;
        SmartDashboard.putNumber("tarhet", target);
        setFilteredAngle(target);
    }

    public void setFilteredAngle(double target) {
        filter = filter + Constants.Turret.filterConstant * (target - filter);
        setAngle(filter);
    }

    public void rotateMotorVelocity(double speed) {
        rotationMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getVelocity() {
        return rotationMotor.getSelectedSensorVelocity();
    }

    public boolean getReverseLimit() {
        return rotationMotor.isRevLimitSwitchClosed() == 1;
    }

    public void setSoftLimitEnable(boolean enable) {
        rotationMotor.configForwardSoftLimitEnable(enable);
        rotationMotor.configReverseSoftLimitEnable(enable);
    }

    public void setHomed(boolean value) {
        isHomed = value;
    }

    @Override
    public void periodic() {
        if(rotationMotor.hasResetOccurred()) {
            isHomed = false;
        }
        if(!isHomed){
            new goHome().schedule(); 
        }
    }
}
