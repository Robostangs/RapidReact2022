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
    private TalonFX m_leftTop, m_leftBottom, m_rightTop, m_rightBottom; 
    private AHRS m_gyro;
    private SlotConfiguration m_allMotorPID;

    public static Drivetrain getInstance() {
        if(Instance == null) {
            Instance = new Drivetrain();
        }
        return Instance;
    }

    public Drivetrain() {
        m_leftTop = new TalonFX(Constants.Drivetrain.LT);
        m_leftBottom = new TalonFX(Constants.Drivetrain.LB);

        m_rightTop = new TalonFX(Constants.Drivetrain.RT);
        m_rightBottom = new TalonFX(Constants.Drivetrain.RB);

        m_leftTop.configFactoryDefault();
        m_leftBottom.configFactoryDefault();
        m_rightTop.configFactoryDefault();
        m_rightBottom.configFactoryDefault();

        m_allMotorPID = new SlotConfiguration();
        m_allMotorPID.kP = Constants.Drivetrain.kP;
        m_allMotorPID.kI = Constants.Drivetrain.kI;
        m_allMotorPID.kD = Constants.Drivetrain.kD;

        m_leftTop.configureSlot(m_allMotorPID, 1, 500);
        m_rightTop.configureSlot(m_allMotorPID, 1, 500);

        m_leftTop.selectProfileSlot(1, 0);
        m_rightTop.selectProfileSlot(1, 0);

        m_leftBottom.follow(m_leftTop);
        m_rightBottom.follow(m_rightTop);

        m_gyro = new AHRS(SPI.Port.kMXP);
    }

    public void drivePower(double leftPwr, double rightPwr) {
        m_leftTop.set(ControlMode.PercentOutput, leftPwr);
        m_rightTop.set(ControlMode.PercentOutput, rightPwr);
    }

    //Test out code, docs seems to suggest that its a PID built in control mode, PID Configured above?
    public void motionMagicDrive(double endPosition) {
        m_leftTop.set(ControlMode.Position, endPosition);
        m_rightTop.set(ControlMode.Position, endPosition);
    }

    public double getLeftEncoder() {
        return m_leftTop.getActiveTrajectoryPosition();
    }

    public double getRightEncoder() {
        return m_rightTop.getActiveTrajectoryPosition();
    }

    public double getRightVelocity() {
        return m_rightTop.getSelectedSensorVelocity();
    }

    public double getLeftVelocity() {
        return m_leftTop.getSelectedSensorVelocity();
    }

    public void resetEncoder() {
        m_leftTop.setSelectedSensorPosition(0);
        m_rightTop.setSelectedSensorPosition(0);
    }

    public double getAngle() {
        return m_gyro.getAngle();
    }

    public void brake() {
        drivePower(0, 0);
    }       
}
