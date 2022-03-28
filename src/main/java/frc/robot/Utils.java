package frc.robot;

import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public final class Utils {
    public static double saturate(double input, double min, double max) {
        return Math.min(Math.max(input, min), max);
    }

    public static double LinearFront(double distance) {
        return (7.98808*distance + 1724.21);
    }

    public static double LinearBack(double distance) {
        return (7.36177*distance + 1458.81);
    }

    public static double QuadFront(double distance) {
        return ((0.0352101 * Math.pow(distance, 2)) + (-2.67594 * distance) + 2377.43);
    }
    
    public static double QuadBack(double distance) {
        return ((0.0402967 * Math.pow(distance, 2)) + (-4.84282 * distance) + 2206.4);
    }

    public static double deadzone(double input) {
        return deadzone(input, 1.6);
    }

    public static double deadzone(double input, double power) {
        return deadzone(input, power, -1, -0.1, 0.1, 1);
    }

    public static double deadzone(double input, double power, double lower_maxzone, double lower_deadzone, double higher_deadzone, double higher_maxzone) {
        if (input <= lower_maxzone) {
            return -1;
        } else if (lower_maxzone < input && input < lower_deadzone) {
            return -Math.pow((-input + lower_deadzone) / (lower_deadzone - lower_maxzone), power);
        } else if (lower_deadzone <= input && input <= higher_deadzone) {
            return 0;
        } else if (higher_deadzone < input && input < higher_maxzone) {
            return Math.pow((input - higher_deadzone) / (higher_maxzone - higher_deadzone), power);
        } else {
            return 1;
        }
    }

    public static double dist(double ty) {
        return Constants.Limelight.kTargetHeightDelta / (Math.tan(degToRad(ty + Constants.Limelight.kLimelightAngle)));
    }

    public static double degToRad(double x) {
        return (Math.PI / 180.0) * x;
    }

    public static double motorConversionInches(double encoderMax, double distance) {
        //Update wheel diameter in constants
        double rotations = distance / 2*Math.PI*Constants.Drivetrain.kWheelDiameter;
        return encoderMax * rotations;
    }

    public static double getEncoderDrivetrain(double distance) {
        // distance in meters
        return (distance / 0.15*Math.PI) * 7.82887701 / 2048;  
    }

    public static boolean roughlyEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }

    public static boolean roughlyEqual(double a, double b) {
        return roughlyEqual(a, b, 0.001);
    }

    public static double round(double a, int roundAmount) {
        return BigDecimal.valueOf(a).setScale(roundAmount, RoundingMode.HALF_UP).doubleValue();
    }
}
