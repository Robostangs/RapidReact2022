package frc.robot;

public final class Utils {
    public static double saturate(double input, double min, double max) {
        if(input > max) {
            return max;
        } else if(input < min) {
            return min;
        } else {
            return input;
        }
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
        return Constants.Limelight.targetHeight / (Math.tan(degToRad(ty + Constants.Limelight.limelightAngle)));
    }

    public static double degToRad(double x) {
        return (Math.PI / 180.0) * x;
    }
    
    public static double motorConversionInches(double encoderMax, double distance) {
        //Update wheel diameter in constants
        double rotations = distance / 2*Math.PI*Constants.Drivetrain.wheelDiameter;
        return encoderMax * rotations;
    }

    public static double getEncoderDrivetrain(double distance) {
        // distance in meters
        return (distance / 0.15*Math.PI) * 7.82887701 / 2048;  
   }
}
