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

    public static final class Drivetrain {
        public static final int LF = 0;
        public static final int LM = 1;
        public static final int LB = 2;

        public static final int RF = 3;
        public static final int RM = 4;
        public static final int RB = 5;

        public static final int gyro = 7;
        public static final int falcon_encoder_max = 4096;
        public static final double wheelDiameter = 3;

        //PID - never used, PID controllers don't work on Falcons, they use custom built MotionMagic Profiles?
        public static final double leftkP = 1;
        public static final double leftkI = 1;
        public static final double leftkD = 1;

        public static final double rightkP = 1;
        public static final double rightkI = 1;
        public static final double rightkD = 1;
    }

}
