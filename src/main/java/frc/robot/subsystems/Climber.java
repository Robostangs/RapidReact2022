package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends PIDSubsystem {
    
    public static Climber instance;
    private TalonFX m_rotationMotor;  
    private CANSparkMax m_leftClaw, m_rightClaw;
    private Servo m_leftClawLock, m_rightClawLock, m_elevatorServo;
    private ElevatorFeedforward m_ElevatorFeedforward;


    public static Climber getInstance() {
        if(instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    public Climber() {
        super(new PIDController(Constants.Climber.rotationKP, Constants.Climber.rotationKI, Constants.Climber.rotationKD));
        m_rotationMotor = new TalonFX(Constants.Climber.rotationMotorID);
        m_elevatorServo = new Servo(Constants.Climber.elevatorID);

        m_leftClaw = new CANSparkMax(Constants.Climber.leftClawID, MotorType.kBrushless);
        m_rightClaw = new CANSparkMax(Constants.Climber.rightClawID, MotorType.kBrushless);
        
        m_leftClawLock = new Servo(Constants.Climber.leftClawLockID);
        m_rightClawLock = new Servo(Constants.Climber.rightClawLockID);
        
        m_ElevatorFeedforward = new ElevatorFeedforward(Constants.Climber.rotationStaticGain, Constants.Climber.gravityGain, Constants.Climber.velocityGain, Constants.Climber.accelerationGain);
    }

    public void setRotationMotorSpeed(double power) {
        m_rotationMotor.set(ControlMode.PercentOutput, power);
    }

    public void setRotationMotorPosition(double position) {
        m_rotationMotor.set(ControlMode.Position, position);
    }

    // public void setRotationMotorMagicPosition(double position) {
    //     m_rotationMotor.set(ControlMode.MotionMagic, position);
    // }

    public double getRotationMotorPosition() {
        return m_rotationMotor.getActiveTrajectoryPosition();
    }

    public void setLeftClawSpeed(double power) {
        m_leftClaw.set(power);
    }

    public void setRightClawSpeed(double power) {
        m_rightClaw.set(power);
    }

    public double getLeftClawPosition() {
        return m_leftClaw.getEncoder().getPosition();
    }

    public double getRightClawPosition() {
        return m_rightClaw.getEncoder().getPosition();
    }

    public void setRightClawLockPosition(double position) {
        m_rightClawLock.set(position);
    }

    public void setLeftClawPosition(double position) {
        m_leftClaw.set(position);
    }

    public void setRightClawPosition(double position) {
       m_rightClaw.set(position);
    }

    public double getRightClawLockPosition() {
        return m_rightClawLock.getPosition();
    }

    public double getLeftClawLockPosition() {
        return m_leftClawLock.getPosition();
    }
    
    public void setElevatorPosition(double position) {
        m_elevatorServo.set(position);
    }

    public double getElevatorPosition() {
        return m_elevatorServo.getPosition();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        setRotationMotorSpeed(output + m_ElevatorFeedforward.calculate(setpoint));
    }

    @Override
    protected double getMeasurement() {
        return m_rotationMotor.getActiveTrajectoryPosition();
    }
}
