package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.shooter.Home;

public class Shooter extends SubsystemBase {

    public static class State {
        public double topSpeed;
        public double bottomSpeed;
        public double angle;

        public State(double topSpeed, double bottomSpeed, double angle) {
            this.topSpeed = topSpeed;
            this.bottomSpeed = bottomSpeed;
            this.angle = angle;
        }

        public State(double topSpeed, double bottomSpeed) {
            this(topSpeed, bottomSpeed, 0);
        }

        public State(double topSpeed) {
            this(topSpeed, topSpeed * Constants.Shooter.kDefaultBottomSpeedMultiplier, 0);
        }

        public String toString() {
            return "ShooterState(topSpeed=" + topSpeed + ", bottomSpeed=" + bottomSpeed + ", angle=" + angle + ")";
        }

        public boolean equals(Object o) {
            if (o instanceof State) {
                State other = (State) o;
                return topSpeed == other.topSpeed && bottomSpeed == other.bottomSpeed && angle == other.angle;
            }
            return false;
        }
    }

    private static Shooter instance;
    private final WPI_TalonFX mBottomShooter  = new WPI_TalonFX(Constants.Shooter.kLeftShooterID);
    private final WPI_TalonFX mTopShooter = new WPI_TalonFX(Constants.Shooter.kRightShooterID);
    private final WPI_TalonFX mHood = new WPI_TalonFX(Constants.Shooter.kAngleShooterID);
    private boolean isHomed;

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    private Shooter() {
        mBottomShooter.configFactoryDefault();
        mTopShooter.configFactoryDefault();
        mHood.configFactoryDefault();

        mBottomShooter.configAllSettings(Constants.Shooter.kBottomShooterConfig);
        mTopShooter.configAllSettings(Constants.Shooter.kTopShooterConfig);
        mHood.configAllSettings(Constants.Shooter.kHoodConfig);

        mHood.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addStringProperty("Shooter State", () -> getState().toString(), null);
    }

    @Override
    public void periodic() {
        super.periodic();
        if (mHood.hasResetOccurred()) {
            isHomed = false;
        }
        if (!isHomed) {
            new Home().schedule();
        }
    }

    public void setBottomShooterPower(double power) {
        mBottomShooter.set(ControlMode.PercentOutput, power);
    }

    public void setBottomShooterVelocity(double velocity) {
        mBottomShooter.set(ControlMode.Velocity, velocity / (600.0 / 2048.0));
    }

    public void setTopShooterPower(double power) {
        mTopShooter.set(ControlMode.PercentOutput, power);
    }

    public void setTopShooterVelocity(double velocity) {
        mTopShooter.set(ControlMode.Velocity, velocity / (600.0 / 2048.0));
    }

    public void setHoodPositionPID(double position) {
        mHood.set(ControlMode.Position, position * (-8192 / 90), DemandType.ArbitraryFeedForward, 0.04);
    }

    public void setHoodPower(double power) {
        mHood.set(ControlMode.PercentOutput, power);
    }

    public State getState() {
        return new State(
            mTopShooter.getSelectedSensorVelocity(),
            mBottomShooter.getSelectedSensorVelocity(),
            mHood.getSelectedSensorPosition());
    }

    public void setState(State state) {
        setTopShooterVelocity(state.topSpeed);
        setBottomShooterPower(state.bottomSpeed);
        setHoodPositionPID(state.angle);
    }

    public boolean getHoodLimitSwitch() {
        return mHood.isFwdLimitSwitchClosed() == 1;
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

    // @Override
    // public void periodic() {
    //     SmartDashboard.putNumber(
    //         "Distance Limelight",
    //         Utils.dist(Limelight.getTy()));
    //     mBottomShooter.set(
    //         ControlMode.Velocity,
    //         (SmartDashboard.getNumber("LeftVelo", 0.0) / (600.0/2048.0)));
    //     mTopShooter.set(
    //         ControlMode.Velocity,
    //         (SmartDashboard.getNumber("RightVelo", 0.0) / (600.0/2048.0)));
    //     // setAnglePositionPID(SmartDashboard.getNumber("Hood Position", 0));
    // }
}
