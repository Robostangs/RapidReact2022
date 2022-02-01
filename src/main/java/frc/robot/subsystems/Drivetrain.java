package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Drivetrain extends SubsystemBase {

    public static Drivetrain Instance;
    private TalonFX m_leftTop, m_leftBottom, m_rightTop, m_rightBottom; 
    private AHRS m_gyro;
    private SlotConfiguration m_allMotorPID;
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
        m_leftTop = new TalonFX(Constants.Drivetrain.LT);
        m_leftBottom = new TalonFX(Constants.Drivetrain.LB);

        m_rightTop = new TalonFX(Constants.Drivetrain.RT);
        m_rightBottom = new TalonFX(Constants.Drivetrain.RB);


        m_leftPIDController = new PIDController(Constants.Drivetrain.kLeftP, Constants.Drivetrain.kLeftI, Constants.Drivetrain.kLeftD);
        m_rightPIDController = new PIDController(Constants.Drivetrain.kRightP, Constants.Drivetrain.kRightI, Constants.Drivetrain.kRightD);

        m_leftFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kLeftS, Constants.Drivetrain.kLeftV);
        m_rightFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kRightS, Constants.Drivetrain.kRightV);

        m_leftBottom.follow(m_leftTop);
        m_rightBottom.follow(m_rightTop);

        m_gyro = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void periodic() {
        double leftOutput = m_leftPIDController.calculate(getLeftPosition());
        double rightOutput = m_rightPIDController.calculate(getRightPosition());
        drivePower(
            leftOutput + m_leftFeedForward.calculate(getLeftVelocity()),
            rightOutput + m_rightFeedForward.calculate(getRightVelocity())
        );
    }

    public void drivePower(double leftPwr, double rightPwr) {
        m_leftTop.set(ControlMode.PercentOutput, leftPwr);
        m_rightTop.set(ControlMode.PercentOutput, rightPwr);
    }

    public double getLeftPosition() {
        return m_leftTop.getActiveTrajectoryPosition();
    }

    public double getRightPosition() {
        return m_rightTop.getActiveTrajectoryPosition();
    }
    
    public double getLeftVelocity() {
        return m_leftTop.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return m_rightTop.getSelectedSensorVelocity();
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
