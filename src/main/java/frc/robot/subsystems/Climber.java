package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

        private Hand(int clawID, int lockID) {
            claw = new CANSparkMax(clawID, MotorType.kBrushless);
            clawEncoder = claw.getEncoder();
            clawPIDController = claw.getPIDController();
            engagementSwitch = claw.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
            openLimit = claw.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
            lock = new Servo(lockID);
            Constants.Climber.Hand.configClawMotor(claw);
        }

        @Override
        public void initSendable(SendableBuilder builder) {
            super.initSendable(builder);
            builder.addBooleanProperty("Engaged", this::getEngaged, null);
            builder.addDoubleProperty("Claw/Position", this::getClawPosition, null);
            builder.addDoubleProperty("Claw/Velocity", this::getClawSpeed, null);
            builder.addDoubleProperty("LockPosition", () -> lock.get(), null);
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

        public void setLockPosition(double position) {
            lock.set(position);
        }

        public boolean isFullyOpen() {
            return openLimit.isPressed();
        }
    }

    private static Climber instance;

    private final WPI_TalonFX mRotationMotor = new WPI_TalonFX(Constants.Climber.kRotationMotorID);
    private final Hand HandA = new Hand(Constants.Climber.Hand.kClawAID, Constants.Climber.Hand.kLockAID);
    private final Hand HandB = new Hand(Constants.Climber.Hand.kClawBID, Constants.Climber.Hand.kLockBID);
    private final Servo mElevatorRelease = new Servo(Constants.Climber.kElevatorID);

    private Climber() {
        mRotationMotor.configFactoryDefault();
        mRotationMotor.configAllSettings(Constants.Climber.kRotationConfig);
        mRotationMotor.setNeutralMode(NeutralMode.Brake);

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
        addChild("Hand A", HandA);
        addChild("Hand B", HandB);
        builder.addDoubleProperty("Rotation Position", this::getRotationMotorPosition, null);
        builder.addDoubleProperty("Rotation Velocity", this::getRotationMotorVelocity, null);
        builder.addDoubleProperty("ElevatorRelease Position", this::getElevatorReleasePosition, null);
        builder.addDoubleProperty("ElevatorRelease Speed", () -> mElevatorRelease.getSpeed(), null);
    }

    public void setRotationMotorPower(double power) {
        mRotationMotor.set(ControlMode.PercentOutput, power);
    }

    public void setRotationMotorPosition(double position) {
        mRotationMotor.set(ControlMode.Position, position);
    }

    public double getRotationMotorPosition() {
        return mRotationMotor.getSelectedSensorPosition();
    }

    public double getRotationMotorVelocity() {
        return mRotationMotor.getSelectedSensorVelocity();
    }

    public boolean atState(double position, double velocity) {
        return
            Utils.roughlyEqual(getRotationMotorPosition(), position, Constants.Climber.kRotationPositionTolerance)
            && Utils.roughlyEqual(getRotationMotorVelocity(), velocity, Constants.Climber.kRotationSpeedTolerance);
    }

    public void setElevatorReleasePosition(double position) {
        mElevatorRelease.set(position);
    }

    public double getElevatorReleasePosition() {
        return mElevatorRelease.getPosition();
    }

    public boolean getInclusiveEngaged() {
        return HandA.getEngaged() || HandB.getEngaged();
    }
}
