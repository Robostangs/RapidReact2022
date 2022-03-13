package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;

public class Climber extends SubsystemBase {
    public class Hand extends SubsystemBase {
        private final CANSparkMax claw;
        private final RelativeEncoder clawEncoder;
        private final SparkMaxPIDController clawPIDController;
        private final SparkMaxLimitSwitch engagementSwitch;
        private final SparkMaxLimitSwitch openLimit;
        private final Servo lock;
        private double mSetpoint;
        private double mLockSetpoint;

        private Hand(int clawID, int lockID) {
            claw = new CANSparkMax(clawID, MotorType.kBrushless);
            clawEncoder = claw.getEncoder();
            clawPIDController = claw.getPIDController();
            engagementSwitch = claw.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
            openLimit = claw.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
            lock = new Servo(lockID);
            Constants.Climber.Hand.configClawMotor(claw);
            Constants.Climber.Hand.configClawLock(lock);
        }

        @Override
        public void initSendable(SendableBuilder builder) {
            super.initSendable(builder);
            builder.addBooleanProperty("Engaged", this::getEngaged, null);
            builder.addDoubleProperty("Claw/Position", this::getClawPosition, null);
            builder.addDoubleProperty("Claw/Velocity", this::getClawSpeed, null);
            builder.addDoubleProperty("Lock Position", this::getLockPosition, null);
        }

        public boolean getEngaged() {
            return engagementSwitch.isPressed();
        }

        public double getClawPosition() {
            return clawEncoder.getPosition();
        }

        public double getClawSpeed() {
            return clawEncoder.getVelocity();
        }

        public void setClawSpeed() {
            setClawSpeed(Constants.Climber.Hand.kClawDefaultMoveSpeed);
        }

        public void setClawSpeed(double speed) {
            claw.set(speed);
        }

        public void setClawReference(double position) {
            mSetpoint = position;
            clawPIDController.setReference(position, ControlType.kPosition);
        }

        public boolean atReference() {
            return Utils.roughlyEqual(getClawPosition(), mSetpoint);
        }

        public void setLockReference(double setpoint) {
            mLockSetpoint = setpoint;
            lock.set(setpoint);
        }

        public double getLockPosition() {
            return lock.get();
        }

        public boolean atLockReference() {
            return Utils.roughlyEqual(getLockPosition(), mLockSetpoint);
        }

        public boolean isFullyOpen() {
            return openLimit.isPressed();
        }
    }

    public class Rotator extends SubsystemBase {
        private final WPI_TalonFX mMotor = new WPI_TalonFX(Constants.Climber.kRotationMotorID);

        public Rotator() {
            mMotor.configFactoryDefault();
            mMotor.configAllSettings(Constants.Climber.kRotationConfig);
            mMotor.setNeutralMode(NeutralMode.Brake);
        }

        @Override
        public void initSendable(SendableBuilder builder) {
            super.initSendable(builder);
            builder.addDoubleProperty("Position", this::getPosition, null);
            builder.addDoubleProperty("Velocity", this::getVelocity, null);
        }

        public void setPower(double power) {
            mMotor.setVoltage(power * Constants.kMaxVoltage);
        }

        public void setPosition(double position, double feedforward) {
            mMotor.set(ControlMode.Position, position, DemandType.ArbitraryFeedForward, feedforward);
        }

        public void setPosition(double position) {
            setPosition(position, 0);
        }

        public double getPosition() {
            return mMotor.getSelectedSensorPosition();
        }

        public double getVelocity() {
            return mMotor.getSelectedSensorVelocity();
        }

        public boolean atState(double position, double velocity) {
            return
                Utils.roughlyEqual(getPosition(), position, Constants.Climber.kRotationPositionTolerance)
                && Utils.roughlyEqual(getVelocity(), velocity, Constants.Climber.kRotationSpeedTolerance);
        }
    }
    private static Climber instance;

    private final Hand mHandA = new Hand(Constants.Climber.Hand.kClawAID, Constants.Climber.Hand.kLockAID);
    private final Hand mHandB = new Hand(Constants.Climber.Hand.kClawBID, Constants.Climber.Hand.kLockBID);
    private final Rotator mRotator = new Rotator();
    private final Servo mElevatorRelease = new Servo(Constants.Climber.kElevatorID);

    private Climber() {
        mElevatorRelease.set(Constants.Climber.kElevatorReleaseDefaultPosition);
    }

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        addChild("Hand A", mHandA);
        addChild("Hand B", mHandB);
        addChild("Rotation Motor", mRotator);
        builder.addDoubleProperty("ElevatorRelease Position", this::getElevatorReleasePosition, null);
        builder.addDoubleProperty("ElevatorRelease Speed", () -> mElevatorRelease.getSpeed(), null);
    }

    public Rotator getRotator() {
        return mRotator;
    }

    public void setElevatorReleasePosition(double position) {
        mElevatorRelease.set(position);
    }

    public double getElevatorReleasePosition() {
        return mElevatorRelease.getPosition();
    }

    public boolean getInclusiveEngaged() {
        return mHandA.getEngaged() || mHandB.getEngaged();
    }

    public Hand getEngagedHand() {
        if (mHandA.getEngaged()) {
            return mHandA;
        } else if (mHandB.getEngaged()) {
            return mHandB;
        } else {
            return null;
        }
    }

    public Hand getDisengagedHand() {
        if (mHandA.getEngaged()) {
            return mHandB;
        } else if (mHandB.getEngaged()) {
            return mHandA;
        } else {
            return null;
        }
    }
}
