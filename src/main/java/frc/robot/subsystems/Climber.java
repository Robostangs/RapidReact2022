package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import frc.LoggyThings.*;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.LoggyThings.*;

public class Climber extends SubsystemBase {
    public class Hand extends SubsystemBase {

        private final LoggyCANSparkMax mClaw;
        private final RelativeEncoder mClawEncoder;
        private final SparkMaxPIDController mClawPIDController;
        private final SparkMaxLimitSwitch mEngagementSwitch;
        private final SparkMaxLimitSwitch mOpenLimit;
        private final Debouncer mLimitDebouncer = new Debouncer(Constants.Climber.Hand.kLimitDebounceTime);
        private final Servo mLock;
        private double mSetpoint;

        private Hand(int clawID, int lockID) {
            // System.out.println("Spark Max " + clawID + " init");
            mClaw = new LoggyCANSparkMax(clawID, MotorType.kBrushless, "/Climber/Hand/"+clawID+"/");
            mClawEncoder = mClaw.getEncoder();
            mClawPIDController = mClaw.getPIDController();
            mEngagementSwitch = mClaw.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
            mOpenLimit = mClaw.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
            mLock = new Servo(lockID);
            Constants.Climber.Hand.configClawMotor(mClaw);
            Constants.Climber.Hand.configClawLock(mLock);
            setLockReference(Constants.Climber.Hand.kClawLockUnlockedPositon);
        }

        @Override
        public void initSendable(SendableBuilder builder) {
            super.initSendable(builder);
            builder.addBooleanProperty("Engaged", this::getEngaged, null);
            builder.addBooleanProperty("Is Fully Open?", this::isFullyOpen, null);
            builder.addDoubleProperty("Claw/Position", this::getClawPosition, null);
            builder.addDoubleProperty("Claw/Velocity", this::getClawSpeed, null);
            builder.addDoubleProperty("Lock Position", this::getLockPosition, null);
            builder.addDoubleProperty("Lock Setpoint", () -> mSetpoint, null);
            builder.addDoubleProperty("Output Current", mClaw::getOutputCurrent, null);
        }

        public boolean getEngaged() {
            return mEngagementSwitch.isPressed();
        }

        public double getClawPosition() {
            return mClawEncoder.getPosition();
        }

        public double getClawSpeed() {
            return mClawEncoder.getVelocity();
        }

        public void setClawSpeed(double speed) {
            mClaw.setVoltage(speed * Constants.kMaxVoltage);
        }

        public void setClawReference(double position) {
            mSetpoint = position;
            mClawPIDController.setReference(position, ControlType.kPosition);
        }

        public void resetClawEncoder(double newPosition) {
            mClawEncoder.setPosition(newPosition);
        }

        public void zeroClawEncoder() {
            resetClawEncoder(0);
        }

        public boolean atReference() {
            return Utils.roughlyEqual(getClawPosition(), mSetpoint);
        }

        public void setLockReference(double setpoint) {
            mLock.set(setpoint);
        }

        public double getLockPosition() {
            return mLock.get();
        }

        public boolean isFullyOpen() {
            return mLimitDebouncer.calculate(mOpenLimit.isPressed());
        }
    }

    public class Rotator extends SubsystemBase {
        private final LoggyWPI_TalonFX mMotor = new LoggyWPI_TalonFX(Constants.Climber.kRotationMotorID, "/Climber/Rotation/");
        private double currentPosition = getPosition();
        // TODO: Rotator limit switch

        public Rotator() {
            mMotor.configFactoryDefault();
            mMotor.configAllSettings(Constants.Climber.kRotationConfig);
            mMotor.setNeutralMode(NeutralMode.Brake);
        }

        @Override
        public void periodic() {
            if(mMotor.hasResetOccurred()) {
                setEncoderPosition(currentPosition);
            }
            currentPosition = getPosition();
        }

        @Override
        public void initSendable(SendableBuilder builder) {
            super.initSendable(builder);
            builder.addDoubleProperty("Position", this::getPosition, null);
            builder.addDoubleProperty("Velocity", this::getVelocity, null);
            builder.addDoubleProperty("Current", this::getCurrent, null);
        }

        public void setPower(double power) {
            mMotor.set(power);
        }

        public double getCurrent() {
            return mMotor.getStatorCurrent();
        }

        public void setPosition(double position, double feedforward) {
            mMotor.set(
                ControlMode.Position,
                position * Constants.Climber.Rotator.kEncoderCountsPerDegree);
                // DemandType.ArbitraryFeedForward,
                // feedforward);
        }

        public void setPosition(double position) {
            setPosition(position, 0);
        }

        public double getPosition() {
            return mMotor.getSelectedSensorPosition() / Constants.Climber.Rotator.kEncoderCountsPerDegree;
        }

        public void setEncoderPosition(double position) {
            mMotor.setSelectedSensorPosition(position);
        }

        public double getVelocity() {
            return mMotor.getSelectedSensorVelocity();
        }

        public boolean atState(double position, double velocity) {
            return
                Utils.roughlyEqual(getPosition(), position, Constants.Climber.Rotator.kPositionTolerance)
                && Utils.roughlyEqual(getVelocity(), velocity, Constants.Climber.Rotator.kSpeedTolerance);
        }

        public void setNeutralModeBrake() {
            mMotor.setNeutralMode(NeutralMode.Brake);
        }

        public void setNeutralModeCoast() {
            mMotor.setNeutralMode(NeutralMode.Coast);
        }
    }
    private static Climber instance;

    private final Rotator mRotator = new Rotator();
    private final Hand mHandA = new Hand(Constants.Climber.Hand.kClawAID, Constants.Climber.Hand.kLockAID);
    private final Hand mHandB = new Hand(Constants.Climber.Hand.kClawBID, Constants.Climber.Hand.kLockBID);
    private final Servo mLeftElevatorRelease = new Servo(Constants.Climber.kLeftElevatorID);
    private final Servo mRightElevatorRelease = new Servo(Constants.Climber.kRightElevatorID);

    private Climber() {
        mLeftElevatorRelease.set(Constants.Climber.kLeftElevatorReleaseDefaultPosition);
        mRightElevatorRelease.set(Constants.Climber.kRightElevatorReleaseDefaultPosition);
        addChild("Hand A", mHandA);
        addChild("Hand B", mHandB);
        addChild("Rotation Motor", mRotator);
        addChild("Left Elevator Release", mLeftElevatorRelease);
        addChild("Right Elevator Release", mRightElevatorRelease);
        System.out.println( ",,,, Time" + 
                            ", Bus Voltage" + 
                            ", PDP Voltage"+
                            ", PDP Current" +
                            ", Faults" +
                            ", Closed Loop Error"  + 
                            ", Motor Output Percentage" + 
                            ", Motor Output Voltage"+ 
                            ", Motor Position" +  
                            ", Motor Velocity" +  
                            ", Motor Temperature" + 
                            ", Stator Current" + 
                            ", Supply Current");
    }

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    public Rotator getRotator() {
        return mRotator;
    }

    public Hand[] getHands() {
        return new Hand[] { mHandA, mHandB };
    }

    public void setElevatorReleasePositions(double leftPosition, double rightPosition) {
        mLeftElevatorRelease.set(leftPosition);
        mRightElevatorRelease.set(rightPosition);
    }
}
