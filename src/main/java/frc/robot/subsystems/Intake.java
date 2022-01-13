package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    
    private static Intake instance;
    // private motortype mMotor; //TODO: set motor type
    // private sensortype mSensor; //TODO: set motor type

    public static Intake getInstance() {
        if(instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    private Intake() {
        // mMotor = new motortype(); //TODO: instantiate motor
        // mSensor = new sensortype(); //TODO: instantiate sensor
    }

    public void setSpeed(double speed) {
        // mMotor.setSpeed(speed); //TODO: set motor speed
    }

    public returntype getSensor___() { //TODO: set sensor return type
        // return mSensor.getvalue(); //TODO: return sensor value
    }
}