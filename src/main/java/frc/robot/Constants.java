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

    public static class Climber {
        public static final int rotationMotorID = 0;        
        public static final int leftClawID = 1;
        public static final int rightClawID = 2;
        public static final int leftClawLockID = 3;
        public static final int rightClawLockID = 4;
        public static final int elevatorID = 5;

        public static final int leftClawSensorID = 0;
        public static final int rightClawSensorID = 0;

        public static final double rotationKP = 1;
        public static final double rotationKI = 1;
        public static final double rotationKD = 1;

        public static final double rotationStaticGain = 1;
        public static final double gravityGain = 1;
        public static final double velocityGain = 1;
        public static final double accelerationGain = 1;

        public static final double elevatorReleaseConstant = 500;
        public static final double clawMoveConstant = 500;

        public static final double moveClimberSpeed = 0.5;
    }
    public static final class Shooter {
        public static final int alignmentID = -1;
        public static final int leftShooterID = 13;
        public static final int rightShooterID = 15;
        public static final int angleShooterID = 9;

        public static final double alignmentMotorKP = 1;
        public static final double alignmentMotorKI = 1;
        public static final double alignmentMotorKD = 1;

        public static final double leftMotorKP = 0.0002;
        public static final double leftMotorKI = 0;
        public static final double leftMotorKD = 0;

        public static final double rightMotorKP = 0.0002;
        public static final double rightMotorKI = 0;
        public static final double rightMotorKD = 0;

        public static final double angleMotorKP = 1;
        public static final double angleMotorKI = 1;
        public static final double angleMotorKD = 1;

        public static final double ticksPerDegree = 10;

        public static final double homeSpeed = -0.1;
    }


    public static final class Feeder {
        public static final int beltMotorID = 4;
        public static final int elevatorMotorID = 5; 
        public static final int dcolorIntakeID = 1;
        public static final int colorIntakeID = 0;
        public static final int dcolorShooterID = 3;
        public static final int colorShooterID = 2;

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
        public static final int intakeMotorID = 8;
        public static final int sensorID = 2;

        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
    }


    public static final class Drivetrain {
        public static final int LT = 1;
        public static final int LB = 20;

        public static final int RT = 3;
        public static final int RB = 2;

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

        //Odometry
        public static final double trackWidth = 0;
    }

    public static class Turret {
        public static final int rotationMotorID = 6;
        public static final double rotationMotorKp = 0.1;
        public static final double rotationMotorKi = 0.001;
        public static final double rotationMotorKd = 0;
        public static final double rotationMotorFF = -0.3;
        public static final double rotationMotorIZone = 1000;
        
        public static final double rotationMotorMax = 74000;
        public static final double rotationMotorSoftLimitOffset = 2000;
        public static final double rotationMotorSpeed = -0.2;
        public static final double backPosition = 34595;
        public static final double leftNinety = 63428;

        public static final double filterConstant = 0.5;
    }
    
    public static class Limelight {
        public static final double targetHeight = 78.5;
        public static final double limelightAngle = 46; 
    }
}
