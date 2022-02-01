package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

public class Turret {
    private final TalonFX rotationMotor;
    public Turret instance;
    

    public Turret getInstance() {
        if(instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    public Turret() {
        rotationMotor = new TalonFX(Constants.Turret.rotationMotorID);
    }
}
