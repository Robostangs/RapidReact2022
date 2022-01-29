package frc.robot.subsystems;
import java.util.SplittableRandom;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class Intake extends SubsystemBase {
    
    private static Intake instance;
    private TalonFX m_intakeMotor;
    private DigitalInput m_intakeSensor;
    public SlotConfiguration intakeMotorPID;

    public static Intake getInstance() {
        if(instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    private Intake() {
        m_intakeMotor = new TalonFX(Constants.IntakeConstants.intakeMotorID); //TODO: instantiate motor
        m_intakeMotor.configFactoryDefault();

        intakeMotorPID = new SlotConfiguration();
        intakeMotorPID.kP = Constants.IntakeConstants.kP;
        intakeMotorPID.kI = Constants.IntakeConstants.kI;
        intakeMotorPID.kD = Constants.IntakeConstants.kD;

        m_intakeMotor.configureSlot(intakeMotorPID);
        m_intakeMotor.selectProfileSlot(1, 0);

        m_intakeSensor = new DigitalInput(Constants.IntakeConstants.sensorID);
    }

    public void setSpeed(double speed) {
        m_intakeMotor.set(ControlMode.PercentOutput, speed); //TODO: set motor speed
    }

    public double getEncoder() {
        return m_intakeMotor.getActiveTrajectoryPosition();
    }

    public boolean getSensor() {
        return m_intakeSensor.get();
    }
}