package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class Intake extends SubsystemBase {
    
    private static Intake instance;

    private TalonFX m_intakeMotor;

    public static Intake getInstance() {
        if(instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    public Intake() {
        m_intakeMotor = new TalonFX(Constants.IntakeConstants.intakeMotorID);
        SmartDashboard.putNumber("Intake Speed", 0);
    }

    public void setSpeed(double speed) {
        m_intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    // @Override
    // public void periodic() {
    //     setSpeed(SmartDashboard.getNumber("Intake Speed", 0));
    // }
}