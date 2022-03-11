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
        public static final int kRotationMotorID = 0;        
        public static final int kLeftClawID = 1;
        public static final int kRightClawID = 2;
        public static final int kLeftClawLockID = 3;
        public static final int kRightClawLockID = 4;
        public static final int kElevatorID = 5;

        public static final int kLeftClawSensorID = 0;
        public static final int kRightClawSensorID = 0;

        public static final double kRotationKp = 1;
        public static final double kRotationKi = 1;
        public static final double kRotationKd = 1;

        public static final double kRotationStaticGain = 1;
        public static final double kGravityGain = 1;
        public static final double kVelocityGain = 1;
        public static final double kAccelerationGain = 1;

        public static final double kElevatorReleaseConstant = 500;
        public static final double kClawMoveConstant = 500;

        public static final double kMoveClimberSpeed = 0.5;
    }
    public static final class Shooter {
        public static final int kAlignmentID = -1;
        public static final int kLeftShooterID = 13;
        public static final int kRightShooterID = 15;
        public static final int kAngleShooterID = 9;

        public static final double kAlignmentMotorKp = 1;
        public static final double kAlignmentMotorKi = 1;
        public static final double kAlignmentMotorKd = 1;
        public static final double kAngleMotorIZone = 100;
        public static final double kAngleMotorReverseLimit = -3000;

        public static final double kLeftMotorKP = 0.01;
        public static final double leftMotorKI = 0.0001;
        public static final double kLeftMotorKd = 0.01;
        public static final double kLeftMotorKf = 0.053;
        public static final double kLeftMotorIntegralAccumulation = 0.00000001;

        public static final double kRightMotorKp = 0.013;
        public static final double kRightMotorKi = 0.00005;
        public static final double kRightMotorKd = 0.01;
        public static final double kRightMotorKf = 0.053;
        public static final double kRrightMotorIntegralAccumulation = 0.00000001;

        public static final double kAngleMotorKp = 1;
        public static final double kAngleMotorKi = 1;
        public static final double kAngleMotorKd = 1;

        public static final double kTicksPerDegree = 10;

        public static final double kHomeSpeed = 0.1;
    }


    public static final class Feeder {
        public static final int kBeltMotorID = 4;
        public static final int elevatorMotorID = 5; 
        public static final int kDarkColorIntakeID = 1;
        public static final int kLightColorIntakeID = 0;
        public static final int kDarkColorShooterID = 3;
        public static final int kLightColorShooterID = 2;

        public static final double kBeltKp = 1;
        public static final double kBeltKi = 1;
        public static final double kBeltKd = 1; 
        public static final double kBeltKf = 1; 

        public static final double kElevatorKp = 1;
        public static final double kElevatorKi = 1;
        public static final double KelevatorKd = 1; 
        public static final double kElevatorKf = 1; 

        public static final double KslowBeltSpeed = -0.8;
        public static final double kSlowElevatorSpeed = 0.1;
    }

    public static final class IntakeConstants {
        public static final double kIntakeSpeed = 1; //TODO: Set intake speed
        public static final int kIntakeMotorID = 8;
        public static final int kSensorID = 2;

        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
    }


    public static final class Drivetrain {
        public static final int kLeftTopID = 1;
        public static final int kLeftBackID = 20;

        public static final int kRightTopID = 3;
        public static final int kRightBackID = 2;

        public static final int kGyro = 7;
        public static final int kFalconEncoderMax = 2048;
        public static final double kWheelDiameter = 3;

        // XXX: Never used, convert to integrated pid
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
        public static final double kTrackWidth = 0;
    }

    public static class Turret {
        public static final int kRotationMotorID = 6;
        public static final double kRotationMotorKp = 0.1;
        public static final double kRotationMotorKi = 0.001;
        public static final double kRotationMotorKd = 0;
        public static final double kRotationMotorKf = -0.3;
        public static final double kRotationMotorIZone = 1000;
        
        public static final double kRotationMotorMax = 74000;
        public static final double kRotationMotorSoftLimitOffset = 2000;
        public static final double kRotationMotorSpeed = -0.2;
        public static final double kBackPosition = 34595;
        public static final double kLeftNinety = 63428;

        public static final double kFilterConstant = 0.5;
        public static final double kDrivingOffset = 0;
    }
    
    public static class Limelight {
        public static final double kTargetHeight = 80.25;
        public static final double kLimelightAngle = 41.3; 
    }
}
