// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class Shooter {
        public static final int alignmentID = 1;
        public static final int leftShooterID = 2;
        public static final int rightShooterID = 3;
        public static final int angleShooterID = 4;

        public static final double alignmentMotorKP = 1;
        public static final double alignmentMotorKI = 1;
        public static final double alignmentMotorKD = 1;

        public static final double leftMotorKP = 1;
        public static final double leftMotorKI = 1;
        public static final double leftMotorKD = 1;

        public static final double rightMotorKP = 1;
        public static final double rightMotorKI = 1;
        public static final double rightMotorKD = 1;

        public static final double angleMotorKP = 1;
        public static final double angleMotorKI = 1;
        public static final double angleMotorKD = 1;

        public static final double ticksPerDegree = 10;
    }

    public static final class IntakeConstants {
        public static final double kIntakeSpeed = 4; //TODO: Set intake speed\
        public static final int intakeMotorID = 1;
        public static final int sensorID = 2;

        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
    }


    public static final class Drivetrain {
        public static final int LT = 2;
        public static final int LB = 0;

        public static final int RT = 3;
        public static final int RB = 1;

        public static final int gyro = 7;
        public static final int falcon_encoder_max = 2048;
        public static final double wheelDiameter = 3;

        //PID - never used, PID controllers don't work on Falcons, they use custom built MotionMagic Profiles?
        public static final double kLeftP = SmartDashboard.getNumber("kLeftP", 0.1);
        public static final double kLeftI = SmartDashboard.getNumber("kLeftI", 0);
        public static final double kLeftD = SmartDashboard.getNumber("kLeftD", 0);
        public static final double kLeftF = 1;
        public static final double kLeftV = 1;

        public static final double kRightP = SmartDashboard.getNumber("kRightP", 0.1);
        public static final double kRightI = SmartDashboard.getNumber("kRightI", 0);
        public static final double kRightD = SmartDashboard.getNumber("kRightD", 0);
        public static final double kRightF = 1;
        public static final double kRightV = 1;
    }

    public static class Limelight {
        public static final double targetHeight = 104;
        public static final double limelightAngle = 30; 
    }
}
