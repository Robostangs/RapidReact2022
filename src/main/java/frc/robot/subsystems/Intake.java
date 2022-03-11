package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private static Intake instance;

    private final WPI_TalonFX mIntakeMotor = new WPI_TalonFX(Constants.IntakeConstants.kIntakeMotorID);

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    public Intake() {
        SmartDashboard.putNumber("Intake Speed", 0);
    }

    public void setSpeed(double speed) {
        mIntakeMotor.set(ControlMode.PercentOutput, speed);
    }

    // @Override
    // public void periodic() {
    // setSpeed(SmartDashboard.getNumber("Intake Speed", 0));
    // }
}
