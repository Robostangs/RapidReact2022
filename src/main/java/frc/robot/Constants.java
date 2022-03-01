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
    }
}
