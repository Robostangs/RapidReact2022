package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;


public class Drivetrain extends SubsystemBase {
    

    public static Drivetrain Instance;
    private TalonFX m_leftTop, m_leftBottom, m_rightTop, m_rightBottom; 
    private AHRS m_gyro;
    
    //Stabilization STUFFFF
    private double angleConstant;
    // private SlotConfiguration m_allMotorPID;
    // private PIDController m_leftPIDController;
    // private PIDController m_rightPIDController;
    // private SimpleMotorFeedforward m_leftFeedForward;
    // private SimpleMotorFeedforward m_rightFeedForward;   

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

        m_leftTop.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0,
				30);
                m_leftTop.configNeutralDeadband(0.001, 30);
		/* Set Motion Magic gains in slot0 - see documentation */
		m_leftTop.selectProfileSlot(0, 0);
        SmartDashboard.putNumber("kLeftP", 0.1);
        SmartDashboard.putNumber("kLeftI", 0);
        SmartDashboard.putNumber("kLeftD", 0);

        SmartDashboard.putNumber("kRightP", 0.1);
        SmartDashboard.putNumber("kRightI", 0);
        SmartDashboard.putNumber("kRightD", 0);

        //Stabilization STUFFFF
        // SmartDashboard.putNumber("CAngle", 0);
        // angleConstant = 0;

		m_leftTop.config_kF(0, Constants.Drivetrain.kLeftF, 30);
		m_leftTop.config_kP(0, SmartDashboard.getNumber("kLeftP", 0.1), 30);
		m_leftTop.config_kI(0, SmartDashboard.getNumber("kLeftI", 0), 30);
		m_leftTop.config_kD(0, SmartDashboard.getNumber("kLeftD", 0), 30);

		/* Set acceleration and vcruise velocity - see documentation */
		m_leftTop.configMotionCruiseVelocity(20000, 30);
		m_leftTop.configMotionAcceleration(1500, 30);        

        /* Set Motion Magic gains in slot0 - see documentation */
		m_rightTop.selectProfileSlot(0, 0);
		m_rightTop.config_kF(0, Constants.Drivetrain.kRightF, 30);
		m_rightTop.config_kP(0, SmartDashboard.getNumber("kRightP", 0.1), 30);
		m_rightTop.config_kI(0, SmartDashboard.getNumber("kRightI", 0), 30);
		m_rightTop.config_kD(0, SmartDashboard.getNumber("kRightD", 0), 30);

		/* Set acceleration and vcruise velocity - see documentation */
		m_rightTop.configMotionCruiseVelocity(20000, 30);
		m_rightTop.configMotionAcceleration(1500, 30);    

        // m_leftPIDController = new PIDController(Constants.Drivetrain.kLeftP, Constants.Drivetrain.kLeftI, Constants.Drivetrain.kLeftD);
        // m_rightPIDController = new PIDController(Constants.Drivetrain.kRightP, Constants.Drivetrain.kRightI, Constants.Drivetrain.kRightD);

        // m_leftFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kLeftS, Constants.Drivetrain.kLeftV);
        // m_rightFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kRightS, Constants.Drivetrain.kRightV);

        m_leftBottom.follow(m_leftTop);
        m_rightBottom.follow(m_rightTop);

        m_gyro = new AHRS(SPI.Port.kMXP);
    }

    // @Override
    // public void periodic() {
    //     double leftOutput = m_leftPIDController.calculate(getLeftPosition());
    //     double rightOutput = m_rightPIDController.calculate(getRightPosition());
    //     drivePower(
    //         leftOutput + m_leftFeedForward.calculate(getLeftVelocity()),
    //         rightOutput + m_rightFeedForward.calculate(getRightVelocity())
    //     );
    // }

    // public void setPoint(double leftSetpoint, double rightSetpoint) {
    //     m_leftPIDController.setSetpoint(leftSetpoint);
    //     m_rightPIDController.setSetpoint(rightSetpoint);
    // }

    public void driveDistance(double distance) {
        m_leftTop.set(ControlMode.Velocity, 3000.0 / (600.0/2048.0));
       // m_rightTop.set(ControlMode.Velocity, 100);
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

    //Stabilization Stuffff
    // public double getConstant() {
    //     return angleConstant;
    // }

    public void updateValues() {
		m_leftTop.config_kP(0, SmartDashboard.getNumber("kLeftP", 0.1), 30);
		m_leftTop.config_kI(0, SmartDashboard.getNumber("kLeftI", 0), 30);
		m_leftTop.config_kD(0, SmartDashboard.getNumber("kLeftD", 0), 30);

        m_rightTop.config_kP(0, SmartDashboard.getNumber("kRightP", 0.1), 30);
		m_rightTop.config_kI(0, SmartDashboard.getNumber("kRightI", 0), 30);
		m_rightTop.config_kD(0, SmartDashboard.getNumber("kRightD", 0), 30);

        
        //Stabilization STUFFFFF
        //angleConstant =  SmartDashboard.getNumber("CAngle", 0);

        SmartDashboard.putNumber("right velo", m_rightTop.getSelectedSensorVelocity() * 600/2048);
        SmartDashboard.putNumber("left velo", m_leftTop.getSelectedSensorVelocity() * 600/2048);
        SmartDashboard.putNumber("Angle", m_gyro.getAngle());
    }
}
