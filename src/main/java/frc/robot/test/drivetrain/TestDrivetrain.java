package frc.robot.test.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivetrain;

public class TestDrivetrain {
    
    private Drivetrain m_Drivetrain;

    public TestDrivetrain() {
        moveDrivetrainTime(0.1, 0.2);
        moveDrivetrainTime(0.2, 0.2);
        moveDrivetrainTime(0.3, 0.2);
        moveDrivetrainTime(0.4, 0.2);
        moveDrivetrainTime(0.5, 0.2);
        moveDrivetrainTime(0.6, 0.2);
        moveDrivetrainTime(0.7, 0.2);
        moveDrivetrainTime(0.8, 0.2);
        moveDrivetrainTime(0.9, 0.2);
        moveDrivetrainTime(1, 0.2);
    }

    private void moveDrivetrainTime(double speed, double second) {
        m_Drivetrain.drivePower(speed, speed);
        Timer.delay(second);
        SmartDashboard.putNumber("left Velocity", m_Drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("left Temperature", m_Drivetrain.getLeftTemp());
        SmartDashboard.putNumber("right Velocity", m_Drivetrain.getRightVelocity());
        SmartDashboard.putNumber("right Temperature", m_Drivetrain.getRightTemp());
        SmartDashboard.putNumber("left Voltage", m_Drivetrain.getLeftVoltage());
        SmartDashboard.putNumber("left Current", m_Drivetrain.getLeftCurrent());
        SmartDashboard.putNumber("right Voltage", m_Drivetrain.getRightVoltage());
        SmartDashboard.putNumber("right Current", m_Drivetrain.getRightCurrent());
    }
}
