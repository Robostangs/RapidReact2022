package frc.robot.commands.turret;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.Constants;

public class FollowLimelight extends ToFeildAngle{
    

    
    @Override
    public void execute() {
        mAngle = Limelight.getTx();
        mFeedforward = (Drivetrain.getInstance().getGyroRate()*Constants.Turret.kTurningFeedForward);
        super.execute();
    }

}
