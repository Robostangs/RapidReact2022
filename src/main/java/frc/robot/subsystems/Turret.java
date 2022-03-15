package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.turret.GoHome;

public class Turret extends SubsystemBase {

    private static Turret instance;

    private final WPI_TalonFX mMotor = new WPI_TalonFX(Constants.Turret.kRotationMotorID);
    // private final DigitalInput m_homeSensorOn, m_homeSensorOff;
    private boolean mIsHomed;

    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    private Turret() {
        mMotor.configFactoryDefault();
        mMotor.configAllSettings(Constants.Turret.kMotorConfig);

        mMotor.setNeutralMode(NeutralMode.Brake);

        mIsHomed = false;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Position", this::getRawPosition, null);
        builder.addDoubleProperty("Angle", this::getAngle, null);
    }

    private double positionRawToAngular(double raw) {
        return raw / Constants.Turret.kTicksPerDegree;
    }

    private double positionAngularToRaw(double angle) {
        return angle * Constants.Turret.kTicksPerDegree;
    }

    private double speedRawToAnguar(double raw) {
        return raw * 10 / Constants.Turret.kTicksPerDegree;
    }

    private double speedAngularToRaw(double angular) {
        return angular * Constants.Turret.kTicksPerDegree / 10;
    }

    private double getRawPosition() {
        return mMotor.getSelectedSensorPosition(0);
    }

    public double getAngle() {
        return positionRawToAngular(getRawPosition());
    }

    private double getRawVelocity() {
        return mMotor.getSelectedSensorVelocity();
    }

    public double getAngularVelocity() {
        return speedRawToAnguar(getRawVelocity());
    }

    private void setRawSetpoint(double position, double feedforward) {
        mMotor.set(
            ControlMode.Position,
            position,
            DemandType.ArbitraryFeedForward,
            feedforward);
    }

    public void setAngleSetpoint(double angle, double feedforward) {
        setRawSetpoint(positionAngularToRaw(angle), feedforward);
    }

    public void setAngleSetpoint(double angle) {
        setAngleSetpoint(angle, 0);
    }

    private void setRawSpeedSetpoint(double speed, double feedforward) {
        mMotor.set(
            ControlMode.PercentOutput,
            speed,
            DemandType.ArbitraryFeedForward,
            feedforward);
    }

    public void setAngularVelocitySetpoint(double angularVelocity, double feedforward) {
        setRawSpeedSetpoint(speedAngularToRaw(angularVelocity), feedforward);
    }

    public void setAngularVelocitySetpoint(double angularVelocity) {
        setAngularVelocitySetpoint(angularVelocity, 0);
    }

    public boolean getReverseLimit() {
        return mMotor.isRevLimitSwitchClosed() == 1;
    }

    public void configMaxSpeed(double speed) {
        mMotor.configPeakOutputForward(speed);
        mMotor.configPeakOutputReverse(-speed);
    }

    public void configClearPosition(boolean value) {
        mMotor.configClearPositionOnLimitR(value, 100);
    }

    public void setSoftLimitEnable(boolean enable) {
        mMotor.configForwardSoftLimitEnable(enable);
        mMotor.configReverseSoftLimitEnable(enable);
    }

    public void setHomed(boolean value) {
        mIsHomed = value;
    }

    @Override
    public void periodic() {
        if (mMotor.hasResetOccurred()) {
            mIsHomed = false;
        }
        if (!mIsHomed) {
            new GoHome().withTimeout(3).schedule();
        }
    }
}
