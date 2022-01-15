package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    
    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    //Use PID to use encoder values to calculate distance
    //Implement a better command, which can drive you at a given distance, at a given speed, at a given angle during the drive
    public DriveDistance(double distance, double speed) {
        double encoderResult = Utils.motorConversionInches(Constants.Drivetrain.falcon_encoder_max, distance);
        
        mDrivetrain.drivePower(speed, speed);
    }
}
