// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
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
        public static final int kLeftShooterID = 13;
        public static final int kRightShooterID = 15;
        public static final int kAngleShooterID = 9;
        public static final TalonFXConfiguration kBottomShooterConfig = new TalonFXConfiguration();
        static {
            kBottomShooterConfig.slot0 = new SlotConfiguration();
            kBottomShooterConfig.slot0.kP = 0.01;
            kBottomShooterConfig.slot0.kI = 0.000_1;
            kBottomShooterConfig.slot0.kD = 0.01;
            kBottomShooterConfig.slot0.kF = 0.053;
            kBottomShooterConfig.slot0.maxIntegralAccumulator = 0.000_000_01;
        }
        public static final TalonFXConfiguration kTopShooterConfig = new TalonFXConfiguration();
        static {
            kTopShooterConfig.slot0 = new SlotConfiguration();
            kTopShooterConfig.slot0.kP = 0.013;
            kTopShooterConfig.slot0.kI = 0.000_05;
            kTopShooterConfig.slot0.kD = 0.01;
            kTopShooterConfig.slot0.kF = 0.053;
            kTopShooterConfig.slot0.maxIntegralAccumulator = 0.000_000_01;
        }
        public static final TalonFXConfiguration kHoodConfig = new TalonFXConfiguration();
        static {
            kHoodConfig.slot0 = new SlotConfiguration();
            kHoodConfig.slot0.kP = 1;
            kHoodConfig.slot0.kI = 1;
            kHoodConfig.slot0.kD = 1;
            kHoodConfig.slot0.integralZone = 100;
            kHoodConfig.reverseSoftLimitThreshold = -3000;
            kHoodConfig.forwardSoftLimitThreshold = 0;
            kHoodConfig.neutralDeadband = 0;
        }

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

        public static final TalonFXConfiguration kBeltConfig = new TalonFXConfiguration();
        static {
            kBeltConfig.neutralDeadband = 0.001;
        }

        public static final double kSlowBeltSpeed = -0.8;
    }

    public static final class IntakeConstants {
        public static final double kIntakeSpeed = 1; // TODO: Set intake speed
        public static final int kIntakeMotorID = 8;
        public static final int kSensorID = 2;
    }

    public static final class Drivetrain {
        public static final int kLeftTopID = 1;
        public static final int kLeftBackID = 20;

        public static final int kRightTopID = 3;
        public static final int kRightBackID = 2;

        public static final int kGyro = 7;
        public static final int kFalconEncoderMax = 2048;
        public static final double kWheelDiameter = 3;

        // Odometry
        public static final double kTrackWidth = 0.88817;
    }

    public static class Turret {
        public static final int kRotationMotorID = 6;
        
        public static final double kRotationMotorMax = 74000;
        public static final double kRotationMotorSoftLimitOffset = 2000;
        public static final double kRotationMotorSpeed = -0.2;
        public static final double kBackPosition = 34595;
        public static final double kLeftNinety = 63428;

        public static final double kFilterConstant = 0.5;
        public static final double kDrivingOffset = 0;

        public static final double kTurningFeedForward = -0.03;

        public static final TalonFXConfiguration kRotationConfig = new TalonFXConfiguration();
        static {
            kRotationConfig.slot0 = new SlotConfiguration();
            kRotationConfig.slot0.kP = 0.1;
            kRotationConfig.slot0.kI = 0.001;
            kRotationConfig.slot0.kD = 0;
            kRotationConfig.slot0.kF = -0.03;
            kRotationConfig.slot0.integralZone = 1000;
            kRotationConfig.forwardSoftLimitThreshold = kRotationMotorMax - kRotationMotorSoftLimitOffset;
            kRotationConfig.reverseSoftLimitThreshold = kRotationMotorSoftLimitOffset;
            kRotationConfig.neutralDeadband = 0;
        }
    }

    public static class Limelight {
        public static final double kTargetHeight = 98.375;
        public static final double kLimelightHeight = 24;
        public static final double kTargetHeightDelta = kTargetHeight - kLimelightHeight;
        public static final double kLimelightAngle = 41.3;
    }
}
