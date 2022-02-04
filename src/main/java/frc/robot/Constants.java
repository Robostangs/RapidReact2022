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

    public static class Limelight {
        public static final double targetHeight = 104;
        public static final double limelightAngle = 30; 
    }

}
