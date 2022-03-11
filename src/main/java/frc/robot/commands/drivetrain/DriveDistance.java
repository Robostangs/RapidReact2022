// TODO: REMOVE THIS FILE
package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {

    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    // Use PID to use encoder values to calculate distance
    // Implement a better command, which can drive you at a given distance, at a
    // given speed, at a given angle during the drive
    public DriveDistance(double distance) {
        addRequirements(mDrivetrain);
        double encoderResult = Utils.motorConversionInches(Constants.Drivetrain.kFalconEncoderMax, distance);
        // System.out.println(encoderResult);
        // mDrivetrain.driveDistance(encoderResult);
    }
}
