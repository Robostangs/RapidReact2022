// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
        public static final int alignmentID = -1;
        public static final int leftShooterID = 3;
        public static final int rightShooterID = 1;
        public static final int angleShooterID = -1;

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


    public static final class Feeder {
        public static final int beltMotorID = 5;
        public static final int elevatorMotorID = 0; 
        public static final int dcolorIntakeID = 0;
        public static final int colorIntakeID = 1;
        public static final int dcolorShooterID = 2;
        public static final int colorShooterID = 3;

        public static final double belt_kP = 1;
        public static final double belt_kI = 1;
        public static final double belt_kD = 1; 
        public static final double belt_kF = 1; 

        public static final double elevator_kP = 1;
        public static final double elevator_kI = 1;
        public static final double elevator_kD = 1; 
        public static final double elevator_kF = 1; 

        public static final double slowBeltSpeed = -0.8;
        public static final double slowElevatorSpeed = 0.1;
    }

    public static final class IntakeConstants {
        public static final double kIntakeSpeed = 1; //TODO: Set intake speed\
        public static final int intakeMotorID = 4;
        public static final int sensorID = 2;

        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
    }


    public static final class Drivetrain {
        public static final int LT = 2;
        public static final int LB = 6;

        public static final int RT = 7;
        public static final int RB = 8;

        public static final int gyro = 7;
        public static final int falcon_encoder_max = 2048;
        public static final double wheelDiameter = 3;

        //PID - never used, PID controllers don't work on Falcons, they use custom built MotionMagic Profiles?
        public static final double kLeftP = 6.6416;
        public static final double kLeftI = 0.01;
        public static final double kLeftD = 0.34565;
        public static final double kLeftS = 0.69159;
        public static final double kLeftV = 1.8049;
        public static final double kLeftA = 0.14022;

        public static final double kRightP = 6.7338;
        public static final double kRightI = 0.01;
        public static final double kRightD = 0.35998;
        public static final double kRightS = 0.6924;
        public static final double kRightV = 1.8045;
        public static final double kRightA = 0.14905;
    }

    public static class Turret {
        public static final int rotationMotorID = 1;
        public static final double rotationMotorKp = 1;
        public static final double rotationMotorKi = 1;
        public static final double rotationMotorKd = 1;

        public static final double rotationMotorMax = 2048;
        public static final double rotationMotorMin = 0;

        public static final double leftLimit = 0;
        public static final double rightLimit = 5000;

        public static final double leftSpeedMaxAuto = 0.5;
        public static final double rightSpeedMaxAuto = 0.5;

        public static final int goHomeIDOn = 0;
        public static final int goHomeIDOff = 1;
        public static final double rotationMotorSpeed = -0.2;
    }
    
    public static class Limelight {
        public static final double targetHeight = 104;
        public static final double limelightAngle = 30; 
    }
}
