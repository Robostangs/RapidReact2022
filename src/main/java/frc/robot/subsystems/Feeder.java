package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
    
    public static Feeder Instance;
    private TalonFX m_beltMotor, m_elevatorMotor; 
    private DigitalInput m_intakeSensorDark, m_shooterSensorDark,  m_intakeSensorLight, m_shooterSensorLight;

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

       m_beltMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
       m_beltMotor.configNeutralDeadband(0.001, 30);


       m_elevatorMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0,30);
       m_elevatorMotor.configNeutralDeadband(0.001, 30);
/* Set Motion Magic gains in slot0 - see documentation */
m_beltMotor.selectProfileSlot(0, 0);
m_elevatorMotor.selectProfileSlot(0, 0);

// SmartDashboard.putNumber("kLeftP", 0.1);
// SmartDashboard.putNumber("kLeftI", 0);
// SmartDashboard.putNumber("kLeftD", 0);

// SmartDashboard.putNumber("kRightP", 0.1);
// SmartDashboard.putNumber("kRightI", 0);
// SmartDashboard.putNumber("kRightD", 0);

m_beltMotor.config_kP(0, Constants.Feeder.belt_kP);
m_beltMotor.config_kI(0, Constants.Feeder.belt_kI);
m_beltMotor.config_kD(0, Constants.Feeder.belt_kD);
m_beltMotor.config_kF(0, Constants.Feeder.belt_kF);

/* Set acceleration and vcruise velocity - see documentation */
m_beltMotor.configMotionCruiseVelocity(20000, 30);
m_beltMotor.configMotionAcceleration(1500, 30);        

/* Set Motion Magic gains in slot0 - see documentation */
m_elevatorMotor.config_kP(0, Constants.Feeder.elevator_kP);
m_elevatorMotor.config_kI(0, Constants.Feeder.elevator_kI);
m_elevatorMotor.config_kD(0, Constants.Feeder.elevator_kD);
m_elevatorMotor.config_kF(0, Constants.Feeder.elevator_kF);

/* Set acceleration and vcruise velocity - see documentation */
m_elevatorMotor.configMotionCruiseVelocity(20000, 30);
m_elevatorMotor.configMotionAcceleration(1500, 30);    

       m_intakeSensorDark = new DigitalInput(Constants.Feeder.colorIntakeID);
       m_shooterSensorDark = new DigitalInput(Constants.Feeder.colorShooterID);

       m_intakeSensorLight = new DigitalInput(Constants.Feeder.colorIntakeID);
       m_shooterSensorLight = new DigitalInput(Constants.Feeder.colorShooterID);

    }

    public boolean getIntakeSensorDark() {
        return m_intakeSensorDark.get();
    }

    public boolean getShooterSensorDark() {
        return m_shooterSensorDark.get();
    }

    public boolean getIntakeSensorLight() {
        return m_intakeSensorLight.get();
    }

    public boolean getShooterSensorLight() {
        return m_shooterSensorLight.get();
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
