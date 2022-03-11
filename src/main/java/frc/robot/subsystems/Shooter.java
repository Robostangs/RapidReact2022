package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.shooter.Home;

public class Shooter extends SubsystemBase {

    private static Shooter instance;
    private final WPI_TalonFX mBottomShooter  = new WPI_TalonFX(Constants.Shooter.kLeftShooterID);
    private final WPI_TalonFX mTopShooter = new WPI_TalonFX(Constants.Shooter.kRightShooterID);
    private final WPI_TalonFX mElevator = new WPI_TalonFX(Constants.Feeder.elevatorMotorID);
    private final WPI_TalonFX mHood = new WPI_TalonFX(Constants.Shooter.kAngleShooterID);
    private final ColorSensorV3 mColorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    private boolean isHomed;

    enum cargoColor {
        blue,
        red,
        none
    }
    // private DigitalInput m_shooterInput; -- Get the Sensor from Feeder, that has
    // the shooter sensor

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public Shooter() {
        mBottomShooter.configFactoryDefault();
        mTopShooter.configFactoryDefault();
        mHood.configFactoryDefault();

        mBottomShooter.configAllSettings(Constants.Shooter.kBottomShooterConfig);
        mTopShooter.configAllSettings(Constants.Shooter.kBottomShooterConfig); // XXX: Is this supposed to be Bottom config?
        mHood.configAllSettings(Constants.Shooter.kHoodConfig);

        mHood.setNeutralMode(NeutralMode.Brake);

        SmartDashboard.putNumber("LeftVelo", 0);
        SmartDashboard.putNumber("RightVelo", 0);
        SmartDashboard.putNumber("Hood Position", 0);
        SmartDashboard.putNumber("Elevator Speed", 0);

    }

    // @Override
    // public void periodic() {
    // super.periodic();
    // setAlignmentPower(m_alignmentPID.calculate(getAlignmentPosition()));
    // setAnglePower(m_angleChangerPID.calculate(getAnglePosition()));
    // setLeftShooterPower(m_leftShooterPID.calculate(getLeftShooterPosition()));
    // setRightShooterPower(m_rightShooterPID.calculate(getRightShooterPosition()));
    // }

    public void setLeftShooterPower(double power) {
        mBottomShooter.set(ControlMode.PercentOutput, power);
    }

    public cargoColor getBallColor() {
        if (mColorSensor.getRed() > mColorSensor.getBlue()) {
            return cargoColor.red;
        } else if (mColorSensor.getRed() < mColorSensor.getBlue()) {
            return cargoColor.blue;
        } else {
            return cargoColor.none;
        }
    }

    public boolean getHoodLimitSwitch() {
        return mHood.isFwdLimitSwitchClosed() == 1;
    }

    public void setLeftShooterVelocity(double velocity) {
        mBottomShooter.set(ControlMode.Velocity, velocity / (600.0 / 2048.0));
    }

    public void setRightShooterVelocity(double velocity) {
        mTopShooter.set(ControlMode.Velocity, velocity / (600.0 / 2048.0));
    }

    public void setRightShooterPower(double power) {
        mTopShooter.set(ControlMode.PercentOutput, power);
    }

    public void setAnglePower(double power) {
        mHood.set(ControlMode.PercentOutput, power);
    }

    public void setAnglePositionPID(double position) {
        mHood.set(ControlMode.Position, position * (-8192 / 90), DemandType.ArbitraryFeedForward, 0.04);
    }

    public void setElevatorPower(double power) {
        mElevator.set(ControlMode.PercentOutput, power);
    }

    public double getLeftVelocity() {
        return mBottomShooter.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return mTopShooter.getSelectedSensorVelocity();
    }

    public void setSoftLimitEnable(boolean value) {
        mHood.configForwardSoftLimitEnable(value);
        mHood.configReverseSoftLimitEnable(value);
    }

    public void setClearPosition(boolean value) {
        mHood.configClearPositionOnLimitF(value, 100);
    }

    public void setMaxSpeed(double speed) {
        mHood.configPeakOutputForward(speed);
        mHood.configPeakOutputReverse(-2 * speed);
    }

    public boolean getForwardLimit() {
        return mHood.isFwdLimitSwitchClosed() == 1;
    }

    public void setHomed(boolean value) {
        isHomed = value;
    }

    public void resetHoodEncoder() {
        mHood.setSelectedSensorPosition(0);
    }

    @Override
    public void periodic() {
        if (mHood.hasResetOccurred()) {
            isHomed = false;
        }
        if (!isHomed) {
            new Home().schedule();
        }
    }

    // @Override
    // public void periodic() {
    // SmartDashboard.putNumber("Distance Limelight",
    // Utils.dist(Limelight.getTy()));
    // m_leftShooter.set(ControlMode.Velocity, (SmartDashboard.getNumber("LeftVelo",
    // 0.0) / (600.0/2048.0)));
    // m_rightShooter.set(ControlMode.Velocity,
    // (SmartDashboard.getNumber("RightVelo", 0.0) / (600.0/2048.0)));
    // // setAnglePositionPID(SmartDashboard.getNumber("Hood Position", 0));
    // setElevatorPower(SmartDashboard.getNumber("Elevator Speed", 0));
    // }
}
