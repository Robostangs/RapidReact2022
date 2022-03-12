package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Climber;

public class Climber extends SubsystemBase {

    private class Hand extends SubsystemBase {
        private final CANSparkMax claw;
        private final RelativeEncoder clawEncoder;
        private final SparkMaxLimitSwitch engagementSwitch;
        private final Servo lock;

        private Hand(int clawID, int lockID) {
            claw = new CANSparkMax(clawID, MotorType.kBrushless);
            clawEncoder = claw.getEncoder();
            engagementSwitch = claw.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
            lock = new Servo(lockID);
            Constants.Climber.Hand.configClawMotor(claw);
        }

        public boolean getEngaged() {
            return engagementSwitch.isPressed();
        }

        public void setClawSpeed() {
            setClawSpeed(Constants.Climber.Hand.kClawDefaultMoveSpeed);
        }

        public void setClawSpeed(double speed) {
            claw.set(speed);
        }

        public void setLockPosition(double position) {
            lock.set(position);
        }

        private double getLockPosition() {
            return lock.get();
        }

        private double getClawPosition() {
            return clawEncoder.getPosition();
        }

        private double getClawSpeed() {
            return clawEncoder.getVelocity();
        }
    }

    private static Climber instance;

    private final WPI_TalonFX mRotationMotor = new WPI_TalonFX(Constants.Climber.kRotationMotorID);
    private final Hand HandA = new Hand(Constants.Climber.Hand.kClawAID, Constants.Climber.Hand.kLockAID);
    private final Hand HandB = new Hand(Constants.Climber.Hand.kClawBID, Constants.Climber.Hand.kLockBID);
    private final Servo mElevatorServo = new Servo(Constants.Climber.kElevatorID);

    private Climber() {
        mRotationMotor.setNeutralMode(NeutralMode.Brake);
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
        builder.addBooleanProperty("A/Engaged", HandA::getEngaged, null);
        builder.addDoubleProperty("A/Claw/Position", HandA::getClawPosition, null);
        builder.addDoubleProperty("A/Claw/Velocity", HandA::getClawSpeed, null);
        builder.addDoubleProperty("A/LockPosition", HandA::getLockPosition, null);
        builder.addBooleanProperty("B/Engaged", HandB::getEngaged, null);
        builder.addDoubleProperty("B/Claw/Position", HandB::getClawPosition, null);
        builder.addDoubleProperty("B/Claw/Velocity", HandB::getClawSpeed, null);
        builder.addDoubleProperty("B/LockPosition", HandB::getLockPosition, null);
    }

    public void setRotationMotorSpeed(double power) {
        mRotationMotor.set(ControlMode.PercentOutput, power);
    }

    public void setRotationMotorPosition(double position) {
        mRotationMotor.set(ControlMode.Position, position);
    }

    public double getRotationMotorPosition() {
        return mRotationMotor.getActiveTrajectoryPosition();
    }

    public void setElevatorPosition(double position) {
        mElevatorServo.set(position);
    }

    public double getElevatorPosition() {
        return mElevatorServo.getPosition();
    }
}
