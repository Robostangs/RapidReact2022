package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
    
    public static Feeder Instance;
    private TalonFX m_beltMotor, m_elevatorMotor; 
    private SlotConfiguration m_beltMotorConfig, m_elevatorMotorConfig; 
    private DigitalInput m_intakeSensor, m_shooterSensor;

    public static Feeder getInstance() {
        if(Instance == null) {
            Instance = new Feeder();
        }
        return Instance;
    }

    public Feeder() {
       m_beltMotor = new TalonFX(Constants.Feeder.beltMotorID);
       m_elevatorMotor= new TalonFX(Constants.Feeder.elevatorMotorID);

       m_beltMotor.configFactoryDefault();
       m_elevatorMotor.configFactoryDefault();

       m_beltMotorConfig = new SlotConfiguration();
       m_beltMotorConfig.kP = Constants.Feeder.belt_kP;
       m_beltMotorConfig.kI = Constants.Feeder.belt_kI;
       m_beltMotorConfig.kD = Constants.Feeder.belt_kD;

       m_elevatorMotorConfig = new SlotConfiguration();
       m_elevatorMotorConfig.kP = Constants.Feeder.elevator_kP;
       m_elevatorMotorConfig.kP = Constants.Feeder.elevator_kI;
       m_elevatorMotorConfig.kP = Constants.Feeder.elevator_kD;

       m_intakeSensor = new DigitalInput(Constants.Feeder.colorIntakeID);
       m_shooterSensor = new DigitalInput(Constants.Feeder.colorShooterID);

       m_beltMotor.configureSlot(m_beltMotorConfig, 1, 500);
       m_elevatorMotor.configureSlot(m_elevatorMotorConfig, 1, 500);  
    }

    public boolean getIntakeSensor() {
        return m_intakeSensor.get();
    }

    public boolean getShooterSensor() {
        return m_shooterSensor.get();
    }

    public void moveBelt(double beltPower) {
        m_beltMotor.set(ControlMode.PercentOutput, beltPower);
    }
    
    public void moveBeltPosition(double position) {
        m_beltMotor.set(ControlMode.Position, position);
    }

    public void moveElevator(double elevatorPower) {
        m_elevatorMotor.set(ControlMode.PercentOutput, elevatorPower);
    }

    public void moveElevatorPosition(double position) {
        m_elevatorMotor.set(ControlMode.Position, position);
    }

    public double beltEncoder() {
        return m_beltMotor.getActiveTrajectoryPosition();
    } 

    public double elevatorEncoder() {
        return m_elevatorMotor.getActiveTrajectoryPosition();
    }

    public double beltVelocity() {
        return m_beltMotor.getSelectedSensorVelocity();
    }

    public double elevatorVelocity() {
        return m_elevatorMotor.getSelectedSensorVelocity();
    }

    public void resetElevatorEncoder() {
        m_elevatorMotor.setSelectedSensorPosition(0);
    }

    public void resetBeltEncoder() {
        m_beltMotor.setSelectedSensorPosition(0);
    }

    public void brakeElevator() {
        moveElevator(0);
    }

    public void brakeBelt() {
        moveBelt(0);
    }
}
