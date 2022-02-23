package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
    
    public static Feeder Instance;
    private TalonFX m_beltMotor; 
    private DigitalInput m_intakeSensorDark, m_shooterSensorDark,  m_intakeSensorLight, m_shooterSensorLight;

    public static Feeder getInstance() {
        if(Instance == null) {
            Instance = new Feeder();
        }
        return Instance;
    }

    public Feeder() {
       m_beltMotor = new TalonFX(Constants.Feeder.beltMotorID);

       m_beltMotor.configFactoryDefault();

       m_beltMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
       m_beltMotor.configNeutralDeadband(0.001, 30);

        /* Set Motion Magic gains in slot0 - see documentation */
        m_beltMotor.selectProfileSlot(0, 0);


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

       m_intakeSensorDark = new DigitalInput(Constants.Feeder.dcolorIntakeID);
       m_shooterSensorDark = new DigitalInput(Constants.Feeder.dcolorShooterID);

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

    public double beltEncoder() {
        return m_beltMotor.getActiveTrajectoryPosition();
    } 

    public double beltVelocity() {
        return m_beltMotor.getSelectedSensorVelocity();
    }

    public void resetBeltEncoder() {
        m_beltMotor.setSelectedSensorPosition(0);
    }

    public void brakeBelt() {
        moveBelt(0);
    }

    public void update() {
        SmartDashboard.putBoolean("Intake Dark Value", getIntakeSensorDark());
        SmartDashboard.putBoolean("Intake Light Value", getIntakeSensorLight());
        SmartDashboard.putBoolean("Shooter Dark Value", getShooterSensorDark());
        SmartDashboard.putBoolean("Shooter Light Value", getShooterSensorLight());

    }
}
