package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {

    private static Feeder Instance;

    private final WPI_TalonFX mBeltMotor = new WPI_TalonFX(Constants.Feeder.kBeltMotorID);
    private final DigitalInput mIntakeSensorDark = new DigitalInput(Constants.Feeder.kDarkColorIntakeID);
    private final DigitalInput mShooterSensorDark = new DigitalInput(Constants.Feeder.kDarkColorShooterID);
    private final DigitalInput mIntakeSensorLight = new DigitalInput(Constants.Feeder.kLightColorIntakeID);
    private final DigitalInput mShooterSensorLight = new DigitalInput(Constants.Feeder.kLightColorShooterID);

    public static Feeder getInstance() {
        if (Instance == null) {
            Instance = new Feeder();
        }
        return Instance;
    }

    private Feeder() {
        mBeltMotor.configFactoryDefault();

        mBeltMotor.configAllSettings(Constants.Feeder.kBeltConfig);
        mBeltMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);

        /* Set Motion Magic gains in slot0 - see documentation */
        mBeltMotor.selectProfileSlot(0, 0);

        /* Set acceleration and vcruise velocity - see documentation */
        mBeltMotor.configMotionCruiseVelocity(20000, 30);
        mBeltMotor.configMotionAcceleration(1500, 30);

        addChild("Intake Dark", mIntakeSensorDark);
        addChild("Intake Light", mIntakeSensorLight);
        addChild("Shooter Dark", mShooterSensorDark);
        addChild("Shooter Light", mShooterSensorLight);
    }

    public boolean getIntakeSensorDark() {
        return mIntakeSensorDark.get();
    }

    public boolean getShooterSensorDark() {
        return mShooterSensorDark.get();
    }

    public boolean getIntakeSensorLight() {
        return mIntakeSensorLight.get();
    }

    public boolean getShooterSensorLight() {
        return mShooterSensorLight.get();
    }

    public void moveBelt(double beltPower) {
        mBeltMotor.set(beltPower);
    }

    public void moveBeltPosition(double position) {
        mBeltMotor.set(ControlMode.Position, position);
    }

    public double beltEncoder() {
        return mBeltMotor.getActiveTrajectoryPosition();
    }

    public double beltVelocity() {
        return mBeltMotor.getSelectedSensorVelocity();
    }

    public void resetBeltEncoder() {
        mBeltMotor.setSelectedSensorPosition(0);
    }

    public void brakeBelt() {
        moveBelt(0);
    }

    // @Override
    // public void periodic() {
    // moveBelt(SmartDashboard.getNumber("Feeder Speed", 0));
    // }
}
