package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

    private static Climber instance;

    private final WPI_TalonFX mRotationMotor = new WPI_TalonFX(Constants.Climber.kRotationMotorID);
    private final CANSparkMax mLeftClaw = new CANSparkMax(Constants.Climber.kLeftClawID, MotorType.kBrushless);
    private final CANSparkMax mRightClaw = new CANSparkMax(Constants.Climber.kRightClawID, MotorType.kBrushless);
    private final Servo mLeftClawLock = new Servo(Constants.Climber.kLeftClawLockID);
    private final Servo mRightClawLock = new Servo(Constants.Climber.kRightClawLockID);
    private final Servo mElevatorServo = new Servo(Constants.Climber.kElevatorID);
    // private final ElevatorFeedforward mElevatorFeedforward;
    private final DigitalInput mLeftClawDigitalInput = new DigitalInput(Constants.Climber.kLeftClawSensorID);
    private final DigitalInput mRightClawDigitalInput = new DigitalInput(Constants.Climber.kRightClawSensorID);

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    private Climber() {
        // mElevatorFeedforward = new ElevatorFeedforward(Constants.Climber.rotationStaticGain,
        //         Constants.Climber.gravityGain, Constants.Climber.velocityGain, Constants.Climber.accelerationGain);
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

    public boolean getLeftClawSensor() {
        return mLeftClawDigitalInput.get();
    }

    public boolean getRightClawSensor() {
        return mRightClawDigitalInput.get();
    }

    public void setLeftClawSpeed(double power) {
        mLeftClaw.set(power);
    }

    public void setRightClawSpeed(double power) {
        mRightClaw.set(power);
    }

    public double getLeftClawPosition() {
        return mLeftClaw.getEncoder().getPosition();
    }

    public double getRightClawPosition() {
        return mRightClaw.getEncoder().getPosition();
    }

    public void setRightClawLockPosition(double position) {
        mRightClawLock.set(position);
    }

    public void setLeftClawPosition(double position) {
        mLeftClaw.set(position);
    }

    public void setRightClawPosition(double position) {
        mRightClaw.set(position);
    }

    public double getRightClawLockPosition() {
        return mRightClawLock.getPosition();
    }

    public double getLeftClawLockPosition() {
        return mLeftClawLock.getPosition();
    }

    public void setElevatorPosition(double position) {
        mElevatorServo.set(position);
    }

    public double getElevatorPosition() {
        return mElevatorServo.getPosition();
    }

    // @Override
    // protected void useOutput(double output, double setpoint) {
    //     setRotationMotorSpeed(output + m_ElevatorFeedforward.calculate(setpoint));
    // }

    // @Override
    // protected double getMeasurement() {
    //     return m_rotationMotor.getActiveTrajectoryPosition();
    // }
}
