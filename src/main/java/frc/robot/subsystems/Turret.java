package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class Turret extends PIDSubsystem {
    private final TalonFX rotationMotor;
    public static Turret instance;
    //private TalonFXSensorCollection m_sensorCollection;

    public static Turret getInstance() {
        if(instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    public Turret() {
        super(new PIDController(Constants.Turret.rotationMotorKp, Constants.Turret.rotationMotorKi, Constants.Turret.rotationMotorKd));
        super.m_controller.enableContinuousInput(Constants.Turret.rotationMotorMin, Constants.Turret.rotationMotorMax);
        rotationMotor = new TalonFX(Constants.Turret.rotationMotorID);
    }
    
    @Override
    public double getMeasurement() {
        return rotationMotor.getActiveTrajectoryPosition();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        rotateMotorVelocity(output); //ADD OUTPUT        
    }

    @Override
    public void setSetpoint(double setpoint) {
        super.setSetpoint(setpoint);
    }

    public void rotateMotorVelocity(double speed) {
        rotationMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getVelocity() {
        return rotationMotor.getSelectedSensorVelocity();
    }
}
