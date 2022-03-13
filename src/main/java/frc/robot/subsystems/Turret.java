package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.commands.turret.GoHome;

public class Turret extends SubsystemBase {

    private static Turret instance;

    private final WPI_TalonFX mRotationMotor = new WPI_TalonFX(Constants.Turret.kRotationMotorID);
    // private final DigitalInput m_homeSensorOn, m_homeSensorOff;
    private boolean mIsHomed;
    private double mFilter;
    private frc.robot.subsystems.Drivetrain mDrivetrain = frc.robot.subsystems.Drivetrain.getInstance();

    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    private Turret() {
        mRotationMotor.configFactoryDefault();
        mRotationMotor.configAllSettings(Constants.Turret.kRotationConfig);

        mRotationMotor.setNeutralMode(NeutralMode.Brake);

        mFilter = 0;
        mIsHomed = false;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Turret", this::getTurrentAngle, null);
    }

    public void setClearPosition(boolean value) {
        mRotationMotor.configClearPositionOnLimitR(value, 100);
    }

    public void setSpeed(double speed) {
        mRotationMotor.set(
            ControlMode.PercentOutput,
            speed,
            DemandType.ArbitraryFeedForward,
            mDrivetrain.getGyroRate() * Constants.Turret.kTurningFeedForward);
    }

    public void setPosition(double position) {
        mRotationMotor.set(
            ControlMode.Position,
            position,
            DemandType.ArbitraryFeedForward,
            mDrivetrain.getGyroRate() * Constants.Turret.kTurningFeedForward);
    }

    public double getTurrentPosition() {
        return mRotationMotor.getSelectedSensorPosition();
    }

    public double getTurrentAngle() {
        return (mRotationMotor.getSelectedSensorPosition() - Constants.Turret.kBackPosition)
            / ((Constants.Turret.kLeftNinety - Constants.Turret.kBackPosition) / 90);
    }

    public double getTurretVelocity() {
        return mRotationMotor.getSelectedSensorVelocity();
    }

    public void setMaxSpeed(double speed) {
        mRotationMotor.configPeakOutputForward(speed);
        mRotationMotor.configPeakOutputReverse(-speed);
    }

    public void setAngle(double angle) {
        setPosition(
            Utils.saturate(
                ((angle * (Constants.Turret.kLeftNinety - Constants.Turret.kBackPosition) / 90) + Constants.Turret.kBackPosition),
                Constants.Turret.kRotationMotorSoftLimitOffset,
                (Constants.Turret.kRotationMotorMax - Constants.Turret.kRotationMotorSoftLimitOffset)));
    }

    public void limelightSetAngle(double angle) {
        double currentAngle = (mRotationMotor.getSelectedSensorPosition() - Constants.Turret.kBackPosition)
            / ((Constants.Turret.kLeftNinety - Constants.Turret.kBackPosition) / 90);
        SmartDashboard.putNumber("angle", currentAngle);
        double target = angle + currentAngle;
        SmartDashboard.putNumber("tarhet", target);
        setFilteredAngle(target);
    }

    public void setFilteredAngle(double target) {
        mFilter = mFilter + Constants.Turret.kFilterConstant * (target - mFilter);
        setAngle(mFilter);
    }

    public void rotateMotorVelocity(double speed) {
        mRotationMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getVelocity() {
        return mRotationMotor.getSelectedSensorVelocity();
    }

    public boolean getReverseLimit() {
        return mRotationMotor.isRevLimitSwitchClosed() == 1;
    }

    public void setSoftLimitEnable(boolean enable) {
        mRotationMotor.configForwardSoftLimitEnable(enable);
        mRotationMotor.configReverseSoftLimitEnable(enable);
    }

    public void setHomed(boolean value) {
        mIsHomed = value;
    }

    @Override
    public void periodic() {
        if (mRotationMotor.hasResetOccurred()) {
            mIsHomed = false;
        }
        if (!mIsHomed) {
            new GoHome().schedule();
        }
    }
}
