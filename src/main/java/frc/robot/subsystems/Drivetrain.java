package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
    

    public static Drivetrain Instance;
    public DifferentialDriveOdometry m_dtOdometry;
    public DifferentialDriveKinematics m_dtKinematics;
    private WPI_TalonFX m_leftTop, m_leftBottom, m_rightTop, m_rightBottom; 
    private AHRS m_gyro;
    
    //Stabilization STUFFFF
    private double angleConstant, actualEncoderValueLeft, lastEncoderValueLeft,actualEncoderValueRight, lastEncoderValueRight;
    // private SlotConfiguration m_allMotorPID;
    private PIDController m_leftPIDController;
    private PIDController m_rightPIDController;
    private SimpleMotorFeedforward m_leftFeedForward;
    private SimpleMotorFeedforward m_rightFeedForward;   
    public static Drivetrain getInstance() {
        if(Instance == null) {
            Instance = new Drivetrain();
        }
        return Instance;
    }

    public Drivetrain() {
        m_leftTop = new WPI_TalonFX(Constants.Drivetrain.LT);
        m_leftBottom = new WPI_TalonFX(Constants.Drivetrain.LB);

        m_rightTop = new WPI_TalonFX(Constants.Drivetrain.RT);
        m_rightBottom = new WPI_TalonFX(Constants.Drivetrain.RB);

        m_leftTop.setNeutralMode(NeutralMode.Brake);
        m_rightTop.setNeutralMode(NeutralMode.Brake);
        m_leftBottom.setNeutralMode(NeutralMode.Brake);
        m_rightBottom.setNeutralMode(NeutralMode.Brake);

        m_dtKinematics = new DifferentialDriveKinematics(0.65);

        // m_leftPIDController = new PIDController(Constants.Drivetrain.kLeftP, Constants.Drivetrain.kLeftI, Constants.Drivetrain.kLeftD);
        // m_rightPIDController = new PIDController(Constants.Drivetrain.kRightP, Constants.Drivetrain.kRightI, Constants.Drivetrain.kRightD);

        // m_leftFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kLeftS, Constants.Drivetrain.kLeftV, Constants.Drivetrain.kLeftA);
        // m_rightFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kRightS, Constants.Drivetrain.kRightV, Constants.Drivetrain.kRightD);

        m_leftBottom.follow(m_leftTop);
        m_rightBottom.follow(m_rightTop);

        m_leftTop.setNeutralMode(NeutralMode.Brake);
        m_rightTop.setNeutralMode(NeutralMode.Brake);

        m_gyro = new AHRS(SPI.Port.kMXP);

        resetEncoder(); 
        m_dtOdometry = new DifferentialDriveOdometry(getGyroRotation2d(), new Pose2d(0, 0, new Rotation2d(0, 0)));

    }

    public Rotation2d getGyroRotation2d() {
        if(m_gyro.getRotation2d() != null) {
            return m_gyro.getRotation2d();
        } else {
            return null;
        }
    }

    public double getGyroRate() {
        return m_gyro.getRate();
    }

    public double getGyroVelocityX() {
        return m_gyro.getVelocityX();
    }

    public double getGyroVelocityY() {
        return m_gyro.getVelocityY();
    }

    public void drivePower(double leftPwr, double rightPwr) {
        m_leftTop.set(ControlMode.PercentOutput, leftPwr);
        m_rightTop.set(ControlMode.PercentOutput, rightPwr);
    }

    public double getLeftPosition() {
        return m_leftTop.getSelectedSensorPosition();
    }

    public double getRightPosition() {
        return m_rightTop.getSelectedSensorPosition();
    }
    
    public double getLeftVelocity() {
        return m_leftTop.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return m_rightTop.getSelectedSensorVelocity();
    }

    public double getLeftCurrent() {
        return m_leftTop.getSupplyCurrent();
    }

    public double getRightCurrent() {
        return m_rightTop.getSupplyCurrent();
    }

    public double getLeftVoltage() {
        return m_leftTop.getBusVoltage();
    }

    public double getRightVoltage() {
        return m_rightTop.getBusVoltage();
    }

    public double getLeftTemp() {
        return m_leftTop.getTemperature();
    }

    public double getRightTemp() {
        return m_rightTop.getTemperature();
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

    @Override
    public void periodic() {
        m_dtOdometry.update(getGyroRotation2d(), m_leftTop.getSelectedSensorPosition() * ((0.15 * Math.PI) * 0.11/ 2048), -m_rightTop.getSelectedSensorPosition() * ((0.15 * 0.11* Math.PI) / 2048));
        SmartDashboard.putString("Pose 2D", m_dtOdometry.getPoseMeters().toString());
        SmartDashboard.putNumber("leftDistance", m_leftTop.getSelectedSensorPosition() * ((0.15 * Math.PI) / 2048));
        SmartDashboard.putNumber("rightDistance", -m_rightTop.getSelectedSensorPosition() * ((0.15 * Math.PI) / 2048));
    }
}

