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


    public static final class Feeder {
        public static final int beltMotorID = 7;
        public static final int elevatorMotorID = 8; 
        public static final int colorIntakeID = 9;
        public static final int colorShooterID = 10;

        public static final double belt_kP = 1;
        public static final double belt_kI = 1;
        public static final double belt_kD = 1; 

        public static final double elevator_kP = 1;
        public static final double elevator_kI = 1;
        public static final double elevator_kD = 1; 

        public static final double slowBeltSpeed = 0.1;
        public static final double slowElevatorSpeed = 0.1;
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
}
