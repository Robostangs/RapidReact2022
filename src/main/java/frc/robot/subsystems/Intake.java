package frc.robot.subsystems;

import frc.LoggyThings.LoggyWPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private static Intake instance;

    private final LoggyWPI_TalonFX mIntakeMotor = new LoggyWPI_TalonFX(Constants.IntakeConstants.kIntakeMotorID, "/Intake/Intake/");

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    private Intake() {}

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Intake Speed", mIntakeMotor::get, null);
    }

    public void setSpeed(double speed) {
        mIntakeMotor.set(speed);
    }

    // @Override
    // public void periodic() {
    // setSpeed(SmartDashboard.getNumber("Intake Speed", 0));
    // }
}
