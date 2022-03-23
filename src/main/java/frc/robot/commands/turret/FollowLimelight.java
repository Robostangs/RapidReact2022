package frc.robot.commands.turret;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Constants;

public class FollowLimelight extends ToRobotAngle {
    @Override
    public void initialize() {
        mAngle = mTurret.getAngle();
        super.initialize();
    }

    @Override
    public void execute() {
        Limelight.enableLEDs();
        mAngle = mAngle + Constants.Turret.kFilterConstant * (Limelight.getTx() + mTurret.getAngle() - mAngle);
        SmartDashboard.putNumber("Mangle", mAngle);
        mFeedforward = (Drivetrain.getInstance().getGyroRate() * Constants.Turret.kTurningFeedForward);
        super.execute();
    }
}
