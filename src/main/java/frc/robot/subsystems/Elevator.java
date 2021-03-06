package frc.robot.subsystems;

import frc.LoggyThings.LoggyWPI_TalonFX;
// import com.revrobotics.ColorSensorV3;

// import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {
    private static Elevator instance;
    private final LoggyWPI_TalonFX mElevator = new LoggyWPI_TalonFX(Constants.Feeder.kElevatorMotorID, "/Feeder/Elevator/");
    // private final ColorSensorV3 mColorSensor = new ColorSensorV3(I2C.Port.kOnboard);

    // enum cargoColor {
    //     Blue,
    //     Red,
    //     None
    // }

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    private Elevator() {
        mElevator.configFactoryDefault();
        mElevator.configAllSettings(Constants.Elevator.kElevatorConfig);
    }

    public void setPower(double power) {
        mElevator.set(power);
    }

    // public cargoColor getBallColor() {
    //     if (mColorSensor.getRed() > mColorSensor.getBlue()) {
    //         return cargoColor.Red;
    //     } else if (mColorSensor.getRed() < mColorSensor.getBlue()) {
    //         return cargoColor.Blue;
    //     } else {
    //         return cargoColor.None;
    //     }
    // }
}
