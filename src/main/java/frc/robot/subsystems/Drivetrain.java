package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

    private static Drivetrain Instance;

    private final DifferentialDriveOdometry mDrivetrainOdometry
        = new DifferentialDriveOdometry(getGyroRotation2d(), new Pose2d(0, 0, new Rotation2d(0, 0)));
    private final WPI_TalonFX mLeftTop = new WPI_TalonFX(Constants.Drivetrain.kLeftTopID);
    private final WPI_TalonFX mLeftBottom = new WPI_TalonFX(Constants.Drivetrain.kLeftBackID);
    private final WPI_TalonFX mRightTop = new WPI_TalonFX(Constants.Drivetrain.kRightTopID);
    private final WPI_TalonFX mRightBottom = new WPI_TalonFX(Constants.Drivetrain.kRightBackID);
    private final AHRS mGyro = new AHRS(SPI.Port.kMXP);
    private final Field2d mField = new Field2d();

    // // Stabilization STUFFFF
    // private double angleConstant, actualEncoderValueLeft, lastEncoderValueLeft, actualEncoderValueRight,
    //         lastEncoderValueRight;
    // // private SlotConfiguration m_allMotorPID;
    // private PIDController m_leftPIDController;
    // private PIDController m_rightPIDController;
    // private SimpleMotorFeedforward m_leftFeedForward;
    // private SimpleMotorFeedforward m_rightFeedForward;

    public static Drivetrain getInstance() {
        if (Instance == null) {
            Instance = new Drivetrain();
        }
        return Instance;
    }

    private Drivetrain() {
        // mLeftTop.setNeutralMode(NeutralMode.Brake);
        // mRightTop.setNeutralMode(NeutralMode.Brake);
        // mLeftBottom.setNeutralMode(NeutralMode.Brake);
        // mRightBottom.setNeutralMode(NeutralMode.Brake);

        // m_leftPIDController = new PIDController(Constants.Drivetrain.kLeftP,
        // Constants.Drivetrain.kLeftI, Constants.Drivetrain.kLeftD);
        // m_rightPIDController = new PIDController(Constants.Drivetrain.kRightP,
        // Constants.Drivetrain.kRightI, Constants.Drivetrain.kRightD);

        // m_leftFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kLeftS,
        // Constants.Drivetrain.kLeftV, Constants.Drivetrain.kLeftA);
        // m_rightFeedForward = new SimpleMotorFeedforward(Constants.Drivetrain.kRightS,
        // Constants.Drivetrain.kRightV, Constants.Drivetrain.kRightD);

        mLeftBottom.follow(mLeftTop);
        mRightBottom.follow(mRightTop);

        mLeftTop.setNeutralMode(NeutralMode.Brake);
        mRightTop.setNeutralMode(NeutralMode.Brake);

        mLeftTop.configFactoryDefault();
        mLeftTop.configAllSettings(Constants.Drivetrain.kLeftMotorsConfig);

        mRightTop.configFactoryDefault();
        mRightTop.configAllSettings(Constants.Drivetrain.kRightMotorsConfig);

       
        resetEncoder();

        SmartDashboard.putData("Field", mField);
    }

    @Override
    public void periodic() {
        mField.setRobotPose(updateOdometry());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Left Position", this::getLeftPosition, null);
        builder.addDoubleProperty("Right Position", this::getRightPosition, null);
        builder.addDoubleProperty("Left Velocity", this::getLeftVelocity, null);
        builder.addDoubleProperty("Right Velocity", this::getRightVelocity, null);
    }

    public Rotation2d getGyroRotation2d() {
        if (mGyro != null && mGyro.getRotation2d() != null) {
            return mGyro.getRotation2d();
        } else {
            return new Rotation2d();
        }
    }

    public double getGyroRate() {
        return mGyro.getRate();
    }

    public double getGyroVelocityX() {
        return mGyro.getVelocityX();
    }

    public double getGyroVelocityY() {
        return mGyro.getVelocityY();
    }

    public void drivePower(double leftPwr, double rightPwr) {
        mLeftTop.set(leftPwr);
        mRightTop.set(rightPwr);
    }

    public double getLeftPosition() {
        return mLeftTop.getSelectedSensorPosition();
    }

    public double getRightPosition() {
        return mRightTop.getSelectedSensorPosition();
    }

    public double getLeftVelocity() {
        return mLeftTop.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return mRightTop.getSelectedSensorVelocity();
    }

    public double getLeftCurrent() {
        return mLeftTop.getSupplyCurrent();
    }

    public double getRightCurrent() {
        return mRightTop.getSupplyCurrent();
    }

    public double getLeftVoltage() {
        return mLeftTop.getBusVoltage();
    }

    public double getRightVoltage() {
        return mRightTop.getBusVoltage();
    }

    public double getLeftTemp() {
        return mLeftTop.getTemperature();
    }

    public double getRightTemp() {
        return mRightTop.getTemperature();
    }

    public void resetEncoder() {
        mLeftTop.setSelectedSensorPosition(0);
        mRightTop.setSelectedSensorPosition(0);
    }

    public double getAngle() {
        return mGyro.getAngle();
    }

    public void brake() {
        drivePower(0, 0);
    }

    public Pose2d getPose() {
        return mDrivetrainOdometry.getPoseMeters();
    }

    public void setDriveVelos(double leftVelo, double rightVelo) {
        mLeftTop.set(ControlMode.Velocity, leftVelo * (Constants.Drivetrain.kFalconEncoderMax / 10));
        mRightTop.set(ControlMode.Velocity, rightVelo * (Constants.Drivetrain.kFalconEncoderMax / 10));
    }

    private Pose2d updateOdometry() {
        return mDrivetrainOdometry.update(
            getGyroRotation2d(),
            mLeftTop.getSelectedSensorPosition() * ((0.15 * Math.PI) * 0.11 / 2048),
            -mRightTop.getSelectedSensorPosition() * ((0.15 * 0.11 * Math.PI) / 2048));
    }
}
