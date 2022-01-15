package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Drivetrain extends SubsystemBase {

    public static Drivetrain Instance;
    private TalonFX m_leftFront, m_leftBack, m_leftMiddle, m_rightFront, m_rightBack, m_rightMiddle; 
    private AHRS m_gyro;
    private PIDController m_leftPID, m_rightPID;

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

    //Test out code, docs seems to suggest that its a PID built in control mode
    public void motionMagicDrive(double endPosition) {
        m_leftFront.set(ControlMode.MotionMagic, endPosition);
        m_rightFront.set(ControlMode.MotionMagic, endPosition);
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
