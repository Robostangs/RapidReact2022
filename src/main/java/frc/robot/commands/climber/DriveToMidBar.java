package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;

public class DriveToMidBar extends CommandBase {
    private final Climber mClimber = Climber.getInstance();
    private final Drivetrain mDrivetrain = Drivetrain.getInstance();
    private final double mDriveSpeed;

    public DriveToMidBar(double driveSpeed) {
        addRequirements(mDrivetrain);
        setName("Drive To Climber Mid Bar");
        mDriveSpeed = driveSpeed;
    }

    public DriveToMidBar() {
        this(Constants.Climber.kDefaultDriveSpeed);
    }

    @Override
    public void initialize() {
        mDrivetrain.drivePower(Constants.Drivetrain.kPowerOffsetMultiplier * mDriveSpeed, -mDriveSpeed);
    }

    @Override
    public boolean isFinished() {
        return mClimber.getHands()[0].getEngaged();
    }

    @Override
    public void end(boolean interrupted) {
        mDrivetrain.drivePower(0, 0);
    }
}
