package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;
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

        public boolean roughlyEquals(Object o) {
            if (o instanceof State) {
                State other = (State) o;
                return
                    Utils.roughlyEqual(topSpeed, other.topSpeed, Constants.Shooter.kTopSpeedTolerance)
                 && Utils.roughlyEqual(bottomSpeed, other.bottomSpeed, Constants.Shooter.kBottomSpeedTolerance)
                 && Utils.roughlyEqual(angle, other.angle, Constants.Shooter.kAngleTolerance);
            }
            return false;
        }
    }

    private static Shooter instance;
    private final WPI_TalonFX mBottomShooter  = new WPI_TalonFX(Constants.Shooter.kLeftShooterID);
    private final WPI_TalonFX mTopShooter = new WPI_TalonFX(Constants.Shooter.kRightShooterID);
    // private final WPI_TalonFX mHood = new WPI_TalonFX(Constants.Shooter.kAngleShooterID);
    private boolean isHomed;
    private static Command mHomeCommand = new Home();

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    private Shooter() {
        mBottomShooter.configFactoryDefault();
        mTopShooter.configFactoryDefault();
        // mHood.configFactoryDefault();

        mBottomShooter.configAllSettings(Constants.Shooter.kBottomShooterConfig);
        mTopShooter.configAllSettings(Constants.Shooter.kTopShooterConfig);
        // mHood.configAllSettings(Constants.Shooter.kHoodConfig);

        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_12_Feedback1, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 255);
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_14_Turn_PIDF1, 255);
        //21?
        mBottomShooter.setStatusFramePeriod(StatusFrameEnhanced.Status_Brushless_Current, 255);

        // mHood.setNeutralMode(NeutralMode.Brake);

        SmartDashboard.putNumber("LeftVelo", 0.0);
        SmartDashboard.putNumber("RightVelo", 0.0);
        SmartDashboard.putNumber("HoodAngle", 0.0);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addStringProperty("Shooter State", () -> getState().toString(), null);
        // builder.addBooleanProperty("Hood homed", () -> isHomed, null);
    }

    @Override
    public void periodic() {
        super.periodic();
        // if (mHood.hasResetOccurred()) {
        //     isHomed = false;
        // }
        // if (!isHomed && !mHomeCommand.isScheduled()) {
        //     mHomeCommand.schedule();
        // }

        // SmartDashboard.putNumber(
        //     "Distance Limelight",
        //     Limelight.getDistance());
        // setState(
        //     new State(
        //         (SmartDashboard.getNumber("TopVelo", 0)), 
        //         (SmartDashboard.getNumber("BottomVelo", 0)), 
        //         (SmartDashboard.getNumber("HoodAngle", 0)) 
        //     )
        // );
        // SmartDashboard.putString("Table Value", "addEntry(" + Utils.round(Limelight.getDistance(), 2) + ", " + SmartDashboard.getNumber("TopVelo", 0) + ", " + SmartDashboard.getNumber("BottomVelo", 0) + ", " + SmartDashboard.getNumber("HoodAngle", 0) + ");");
    }

    public void setBottomShooterPower(double power) {
        mBottomShooter.set(ControlMode.PercentOutput, -power);
    }

    public void setBottomShooterVelocity(double velocity) {
        if(velocity < 500) {
            mBottomShooter.set(ControlMode.PercentOutput, 0);
        } else {
            mBottomShooter.set(ControlMode.Velocity, (-velocity / (600.0 / 2048.0)));
        }
    }

    public void setTopShooterPower(double power) {
        mTopShooter.set(ControlMode.PercentOutput, power);
    }

    public void setTopShooterVelocity(double velocity) {
        if(velocity < 500) {
            setTopShooterPower(0);
        } else {
            mTopShooter.set(ControlMode.Velocity, (velocity / (600.0 / 2048.0)));
        }
    }

    public void setHoodPositionPID(double position) {
        // if(position == 0) {
        //     setHoodPower(0);
        // } else {
        //     mHood.set(ControlMode.Position, position * (-8192 / 90), DemandType.ArbitraryFeedForward, 0.04);
        // }
    }

    public void setHoodPower(double power) {
        // mHood.set(ControlMode.PercentOutput, power);
    }

    public State getState() {
        return new State(
            mTopShooter.getSelectedSensorVelocity() * (600/2048f),
            mBottomShooter.getSelectedSensorVelocity() * (600/2048f),
            // mHood.getSelectedSensorPosition());
            0);
    }

    public void setState(State state) {
        SmartDashboard.putString("Requested state", state.toString());
        setTopShooterVelocity(state.topSpeed);
        setBottomShooterVelocity(state.bottomSpeed);
        // setHoodPositionPID(state.angle);
    }

    public void setSoftLimitEnable(boolean value) {
        // mHood.configForwardSoftLimitEnable(value);
        // mHood.configReverseSoftLimitEnable(value);
    }

    public void setClearPosition(boolean value) {
        // mHood.configClearPositionOnLimitF(value, 100);
    }

    public void setMaxSpeed(double speed) {
        // mHood.configPeakOutputForward(speed);
        // mHood.configPeakOutputReverse(-2 * speed);
    }

    public boolean getForwardLimit() {
        // return mHood.isFwdLimitSwitchClosed() == 1;
        return true;
    }

    public void setHomed(boolean value) {
        isHomed = value;
    }

    public void resetHoodEncoder() {
        // mHood.setSelectedSensorPosition(0);
    }
}
