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

public class Shooter extends SubsystemBase {

    public static class State {
        public double topSpeed;
        public double bottomSpeed;

        public State(double topSpeed, double bottomSpeed) {
            this.topSpeed = topSpeed;
            this.bottomSpeed = bottomSpeed;
        }

        public State(double topSpeed) {
            this(topSpeed, topSpeed * Constants.Shooter.kDefaultBottomSpeedMultiplier);
        }

        public String toString() {
            return "ShooterState(topSpeed=" + topSpeed + ", bottomSpeed=" + bottomSpeed + ")";
        }

        public boolean equals(Object o) {
            if (o instanceof State) {
                State other = (State) o;
                return topSpeed == other.topSpeed && bottomSpeed == other.bottomSpeed;

            }
            return false;
        }

        public boolean roughlyEquals(Object o) {
            if (o instanceof State) {
                State other = (State) o;
                return
                    Utils.roughlyEqual(topSpeed, other.topSpeed, Constants.Shooter.kTopSpeedTolerance)
                 && Utils.roughlyEqual(bottomSpeed, other.bottomSpeed, Constants.Shooter.kBottomSpeedTolerance);
            }
            return false;
        }
    }

    private static Shooter instance;
    private final WPI_TalonFX mBottomShooter  = new WPI_TalonFX(Constants.Shooter.kLeftShooterID);
    private final WPI_TalonFX mTopShooter = new WPI_TalonFX(Constants.Shooter.kRightShooterID);

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    private Shooter() {
        mBottomShooter.configFactoryDefault();
        mTopShooter.configFactoryDefault();
        mBottomShooter.configAllSettings(Constants.Shooter.kBottomShooterConfig);
        mTopShooter.configAllSettings(Constants.Shooter.kTopShooterConfig);


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

        SmartDashboard.putNumber("TopVelo", 0); 
        SmartDashboard.putNumber("BottomVelo", 0);
        
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addStringProperty("Shooter State", () -> getState().toString(), null);
        builder.addDoubleProperty("Bottom Error", () -> mBottomShooter.getClosedLoopError(), null);
        builder.addDoubleProperty("Top Error", () -> mTopShooter.getClosedLoopError(), null);
        
        builder.addDoubleProperty("Top Speed", () -> mTopShooter.getSelectedSensorVelocity() * (600/2048f), null);
        builder.addDoubleProperty("Bottom Speed", () -> mBottomShooter.getSelectedSensorVelocity() * (600/2048f), null);
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

        SmartDashboard.putNumber(
            "Distance Limelight",
            Limelight.getDistance());
        setState(
            new State(
                (SmartDashboard.getNumber("TopVelo", 0)), 
                (SmartDashboard.getNumber("BottomVelo", 0))
            )
        );
        SmartDashboard.putString("Table Value", "addEntry(" + Utils.round(Limelight.getDistance(), 2) + ", " + SmartDashboard.getNumber("TopVelo", 0) + ", " + SmartDashboard.getNumber("BottomVelo", 0) + ");");

    }

    public void setBottomShooterPower(double power) {
        mBottomShooter.set(ControlMode.PercentOutput, -power);
    }

    public void setBottomShooterVelocity(double velocity) {
        if(velocity < 500) {
            mBottomShooter.set(ControlMode.PercentOutput, 0);
        } else {
            mBottomShooter.set(ControlMode.Velocity, -velocity / (600.0 / 2048.0));
        }
    }

    public void setTopShooterPower(double power) {
        mTopShooter.set(ControlMode.PercentOutput, power);
    }

    public void setTopShooterVelocity(double velocity) {
        if(velocity < 500) {
            mTopShooter.set(ControlMode.PercentOutput, 0);
        } else {
            mTopShooter.set(ControlMode.Velocity, velocity / (600.0 / 2048.0));
        }
    }

    public State getState() {
        return new State(
            mTopShooter.getSelectedSensorVelocity() * (600/2048f),
            mBottomShooter.getSelectedSensorVelocity() * (600/2048f));
    }

    public void setState(State state) {
        SmartDashboard.putString("Requested state", state.toString());
        setTopShooterVelocity(state.topSpeed);
        setBottomShooterVelocity(state.bottomSpeed);

    }
}
