package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class Turret extends PIDSubsystem{
    private final TalonFX rotationMotor;
    public Turret instance;
    //private TalonFXSensorCollection m_sensorCollection;

    public Turret getInstance() {
        if(instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    public Turret() {
        super(new PIDController(Constants.Turret.rotationMotorKp, Constants.Turret.rotationMotorKi, Constants.Turret.rotationMotorKd));
        rotationMotor = new TalonFX(Constants.Turret.rotationMotorID);
    }
    
    @Override
    protected double getMeasurement() {
        return rotationMotor.getActiveTrajectoryPosition();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        rotateMotorVelocity(output); //ADD OUTPUT        
    }

    public void rotateMotorVelocity(double speed) {
        rotationMotor.set(ControlMode.PercentOutput, speed);
    }
}
