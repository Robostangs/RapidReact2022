package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Drivetrain extends SubsystemBase {

    public static Drivetrain Instance;
    private TalonFX m_leftFront, m_leftBack, m_leftMiddle, m_rightFront, m_rightBack, m_rightMiddle; 
    private AHRS m_gyro;
    private SlotConfiguration m_allMotorPID;

    public static Drivetrain getInstance() {
        if(Instance == null) {
            Instance = new Drivetrain();
        }
        return Instance;
    }

    public Drivetrain() {
        m_leftFront = new TalonFX(Constants.Drivetrain.LF);
        m_leftMiddle = new TalonFX(Constants.Drivetrain.LM);
        m_leftBack = new TalonFX(Constants.Drivetrain.LB);

        m_rightFront = new TalonFX(Constants.Drivetrain.RF);
        m_rightMiddle = new TalonFX(Constants.Drivetrain.RM);
        m_rightBack = new TalonFX(Constants.Drivetrain.RB);

        m_allMotorPID = new SlotConfiguration();
        m_allMotorPID.kP = Constants.Drivetrain.kP;
        m_allMotorPID.kI = Constants.Drivetrain.kI;
        m_allMotorPID.kD = Constants.Drivetrain.kD;

        m_leftFront.configureSlot(m_allMotorPID, 1, 500);
        m_rightFront.configureSlot(m_allMotorPID, 1, 500);

        m_leftFront.selectProfileSlot(1, 0);
        m_rightFront.selectProfileSlot(1, 0);

        m_leftMiddle.follow(m_leftFront);
        m_leftBack.follow(m_leftFront);

        m_rightMiddle.follow(m_rightFront);
        m_rightBack.follow(m_rightFront);

        m_gyro = new AHRS(SPI.Port.kMXP);
    }

    public void drivePower(double leftPwr, double rightPwr) {
        m_leftFront.set(ControlMode.Velocity, leftPwr);
        m_rightFront.set(ControlMode.Velocity, rightPwr);
    }

    //Test out code, docs seems to suggest that its a PID built in control mode, PID Configured above?
    public void motionMagicDrive(double endPosition) {
        m_leftFront.set(ControlMode.Position, endPosition);
        m_rightFront.set(ControlMode.Position, endPosition);
    }

    public double getLeftEncoder() {
        return m_leftFront.getActiveTrajectoryPosition();
    }

    public double getRightEncoder() {
        return m_rightFront.getActiveTrajectoryPosition();
    }

    public double getRightVelocity() {
        return m_rightFront.getSelectedSensorVelocity();
    }

    public double getLeftVelocity() {
        return m_leftFront.getSelectedSensorVelocity();
    }

    public void resetEncoder() {
        m_leftFront.setSelectedSensorPosition(0);
        m_rightFront.setSelectedSensorPosition(0);
    }

    public double getAngle() {
        return m_gyro.getAngle();
    }

    public void brake() {
        drivePower(0, 0);
    }       
}
