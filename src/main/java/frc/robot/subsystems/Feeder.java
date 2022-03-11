package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    public Feeder() {
        mBeltMotor.configFactoryDefault();

        mBeltMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        mBeltMotor.configNeutralDeadband(0.001, 30);

        /* Set Motion Magic gains in slot0 - see documentation */
        mBeltMotor.selectProfileSlot(0, 0);

        // SmartDashboard.putNumber("kLeftP", 0.1);
        // SmartDashboard.putNumber("kLeftI", 0);
        // SmartDashboard.putNumber("kLeftD", 0);

        // SmartDashboard.putNumber("kRightP", 0.1);
        // SmartDashboard.putNumber("kRightI", 0);
        // SmartDashboard.putNumber("kRightD", 0);

        mBeltMotor.config_kP(0, Constants.Feeder.kBeltKp);
        mBeltMotor.config_kI(0, Constants.Feeder.kBeltKi);
        mBeltMotor.config_kD(0, Constants.Feeder.kBeltKd);
        mBeltMotor.config_kF(0, Constants.Feeder.kBeltKf);

        /* Set acceleration and vcruise velocity - see documentation */
        mBeltMotor.configMotionCruiseVelocity(20000, 30);
        mBeltMotor.configMotionAcceleration(1500, 30);

        SmartDashboard.putNumber("Feeder Speed", 0);
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
        mBeltMotor.set(ControlMode.PercentOutput, beltPower);
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

    public void update() {
        SmartDashboard.putBoolean("Intake Dark Value", getIntakeSensorDark());
        SmartDashboard.putBoolean("Intake Light Value", getIntakeSensorLight());
        SmartDashboard.putBoolean("Shooter Dark Value", getShooterSensorDark());
        SmartDashboard.putBoolean("Shooter Light Value", getShooterSensorLight());
    }

    // @Override
    // public void periodic() {
    // moveBelt(SmartDashboard.getNumber("Feeder Speed", 0));
    // }
}
