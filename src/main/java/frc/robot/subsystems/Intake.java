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

    public static Intake getInstance() {
        if(instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    public Intake() {
        m_intakeMotor = new TalonFX(Constants.IntakeConstants.intakeMotorID);

    }

    public void setSpeed(double speed) {
        m_intakeMotor.set(ControlMode.PercentOutput, speed);
    }

}