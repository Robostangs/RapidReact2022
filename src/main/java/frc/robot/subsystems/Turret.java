package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.commands.turret.GoHome;

public class Turret extends SubsystemBase {

    private static Turret instance;

    private final WPI_TalonFX rotationMotor = new WPI_TalonFX(Constants.Turret.kRotationMotorID);
    // private final DigitalInput m_homeSensorOn, m_homeSensorOff;
    private boolean isHomed;
    private double filter;
    private frc.robot.subsystems.Drivetrain mDrivetrain = frc.robot.subsystems.Drivetrain.getInstance();

    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    public Turret() {
        rotationMotor.setNeutralMode(NeutralMode.Brake);
        rotationMotor.configFactoryDefault();

        rotationMotor.setNeutralMode(NeutralMode.Brake);
        rotationMotor.config_kP(0, Constants.Turret.kRotationMotorKp);
        rotationMotor.config_kI(0, Constants.Turret.kRotationMotorKi);
        rotationMotor.config_IntegralZone(0, Constants.Turret.kRotationMotorIZone);
        rotationMotor.configForwardSoftLimitThreshold(
            Constants.Turret.kRotationMotorMax - Constants.Turret.kRotationMotorSoftLimitOffset);
        rotationMotor.configReverseSoftLimitThreshold(Constants.Turret.kRotationMotorSoftLimitOffset);
        rotationMotor.configNeutralDeadband(0);

        filter = 0;
        isHomed = false;
        SmartDashboard.putNumber("Turret", 0);
        SmartDashboard.putNumber("Yaw Feed", 0);
    }

    public void setClearPosition(boolean value) {
        rotationMotor.configClearPositionOnLimitR(value, 100);
    }

    public void setSpeed(double speed) {
        rotationMotor.set(
            ControlMode.PercentOutput,
            speed,
            DemandType.ArbitraryFeedForward,
            mDrivetrain.getGyroRate() * Constants.Turret.kRotationMotorKf);
    }

    public void setPosition(double position) {
        rotationMotor.set(
            ControlMode.Position,
            position,
            DemandType.ArbitraryFeedForward,
            mDrivetrain.getGyroRate() * Constants.Turret.kRotationMotorKf);
    }

    public double getTurrentPosition() {
        return rotationMotor.getSelectedSensorPosition();
    }

    public double getTurrentAngle() {
        return (rotationMotor.getSelectedSensorPosition() - Constants.Turret.kBackPosition)
            / ((Constants.Turret.kLeftNinety - Constants.Turret.kBackPosition) / 90);
    }

    public double getTurretVelocity() {
        return rotationMotor.getSelectedSensorVelocity();
    }

    public void setMaxSpeed(double speed) {
        rotationMotor.configPeakOutputForward(speed);
        rotationMotor.configPeakOutputReverse(-speed);
    }

    public void setAngle(double angle) {
        setPosition(
            Utils.saturate(
                ((angle * (Constants.Turret.kLeftNinety - Constants.Turret.kBackPosition) / 90) + Constants.Turret.kBackPosition),
                Constants.Turret.kRotationMotorSoftLimitOffset,
                (Constants.Turret.kRotationMotorMax - Constants.Turret.kRotationMotorSoftLimitOffset)));
    }

    public void limelightSetAngle(double angle) {
        double currentAngle = (rotationMotor.getSelectedSensorPosition() - Constants.Turret.kBackPosition)
            / ((Constants.Turret.kLeftNinety - Constants.Turret.kBackPosition) / 90);
        SmartDashboard.putNumber("angle", currentAngle);
        double target = angle + currentAngle;
        SmartDashboard.putNumber("tarhet", target);
        setFilteredAngle(target);
    }

    public void setFilteredAngle(double target) {
        filter = filter + Constants.Turret.kFilterConstant * (target - filter);
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
        if (rotationMotor.hasResetOccurred()) {
            isHomed = false;
        }
        if (!isHomed) {
            new GoHome().schedule();
        }
    }
}
