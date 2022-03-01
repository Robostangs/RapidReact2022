package frc.robot.subsystems;

import java.security.cert.TrustAnchor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.Utils;

public class Turret extends PIDSubsystem {
    private final TalonFX rotationMotor;
    public static Turret instance;
    public static DigitalInput m_homeSensorOn, m_homeSensorOff;
    private int filtSoftLimitRange;
    private final XboxController manip = new XboxController(1);
    private final StatorCurrentLimitConfiguration true_stator_config = new StatorCurrentLimitConfiguration(true, 10, 20, 100);
    private final StatorCurrentLimitConfiguration false_stator_config = new StatorCurrentLimitConfiguration(false, 10, 20, 100);
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
        rotationMotor.setNeutralMode(NeutralMode.Brake);
        rotationMotor.configFactoryDefault();
        // rotationMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 20, 20, 100));
        rotationMotor.configOpenloopRamp(0);
        //m_homeSensorOn = new DigitalInput(Constants.Turret.goHomeIDOn);
        //m_homeSensorOff = new DigitalInput(Constants.Turret.goHomeIDOff);
        SmartDashboard.putNumber("Turret", 0);
        SmartDashboard.putNumber("kP", 0.1);
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

    public void setSpeed(double speed) {
        rotationMotor.set(ControlMode.PercentOutput, speed);
    }

    public void rotateMotorVelocity(double speed) {
        rotationMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getVelocity() {
        return rotationMotor.getSelectedSensorVelocity();
    }

    // public boolean getActivatedHomeOn() {
    //     return m_homeSensorOn.get();
    // }

    // public boolean getActivatedHomeOff() {
    //     return m_homeSensorOff.get();
    // }

    public void resetEncoder() {
        rotationMotor.setSelectedSensorPosition(500000);
    }


    // @Override
    // public void periodic() {
    //     if(Limelight.getTv() != 0) {
    //         manip.setRumble(RumbleType.kLeftRumble, 0);
    //         manip.setRumble(RumbleType.kRightRumble, 0);
    //     } else {
    //         manip.setRumble(RumbleType.kLeftRumble, 1);
    //         manip.setRumble(RumbleType.kRightRumble, 1);
    //     }

    //     // if(Limelight.getTv() != 0 && (Math.abs(manip.getLeftX()) < 0.1)) {
    //     //     setSpeed(SmartDashboard.getNumber("kP", 0.1) * Limelight.getTx());
    //     //     SmartDashboard.putString("Control", "auto");
    //     // } else {
    //     //     setSpeed(manip.getLeftX());
    //     //     SmartDashboard.putString("Control", "manual");
    //     // }

    //     // rotationMotor.configOpenloopRamp(0);
    //     double outreq;
    //     if (Limelight.getTv() != 0 && (Math.abs(manip.getLeftX()) < 0.1)) {
    //         // outreq = SmartDashboard.getNumber("kP", 0.3) * Limelight.getTx();
    //         outreq = 0.2 * Limelight.getTx();
    //         SmartDashboard.putString("Control", "auto");
    //     } else {
    //         outreq = (manip.getLeftX());
    //         SmartDashboard.putString("Control", "manual");
    //     }

    //     if (outreq > 0.5){
    //         outreq = 0.5;
    //     }
    //     else if (outreq < -0.5){
    //         outreq = -0.5;
    //     }

    //         setSpeed(outreq);

    //     /*
    //     SmartDashboard.putNumber("turret Current", rotationMotor.getStatorCurrent());
    //     SmartDashboard.putNumber("Turret Percent Output", rotationMotor.getMotorOutputPercent());
    //     SmartDashboard.putNumber("Turret Encoder Position", rotationMotor.getSelectedSensorPosition());
    //     int resetPos=500000;
    //     int travelDist=380000;
    //     int softLimitRange=20000;
    //     softLimitRange+=(int)Math.abs(rotationMotor.getSelectedSensorVelocity())*5;

    //     filtSoftLimitRange=filtSoftLimitRange+(softLimitRange-filtSoftLimitRange)/15;

    //     SmartDashboard.putNumber("filtsoftrange", filtSoftLimitRange);
    //     if(rotationMotor.getSelectedSensorPosition() <= resetPos) {
    //         rotationMotor.configPeakOutputReverse(-0.5);
    //         rotationMotor.configPeakOutputForward(0.5);
    //         rotationMotor.configStatorCurrentLimit(true_stator_config);
    //         SmartDashboard.putString("Limiting", "motor reset init");

    //     }
    //     if(rotationMotor.getSelectedSensorPosition() <= resetPos+filtSoftLimitRange) {
    //         rotationMotor.configPeakOutputReverse(-0.2);
    //         rotationMotor.configStatorCurrentLimit(true_stator_config);
    //         SmartDashboard.putString("Limiting", "backwards");
    //     } else if(rotationMotor.getSelectedSensorPosition() >= resetPos+travelDist-filtSoftLimitRange) {
    //         rotationMotor.configPeakOutputForward(0.2);
    //         rotationMotor.configStatorCurrentLimit(true_stator_config);
    //         SmartDashboard.putString("Limiting", "forwards");
    //     } else {
    //         rotationMotor.configPeakOutputReverse(-1);
    //         rotationMotor.configPeakOutputForward(1);
    //         rotationMotor.configStatorCurrentLimit(false_stator_config);
    //         SmartDashboard.putString("Limiting", "none");
    //     }
    //     */
    // }
}
