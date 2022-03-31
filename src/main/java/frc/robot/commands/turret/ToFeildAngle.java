package frc.robot.commands.turret;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class ToFeildAngle extends ToRobotAngle{
    
    private double desiredAngle;

    public ToFeildAngle(double angle) {
        super(Drivetrain.getInstance().getAngle(), Drivetrain.getInstance().getGyroRate()*Constants.Turret.kTurningFeedForward);
        setName("To Field Angle");
        desiredAngle = angle;
    }

    public ToFeildAngle() {
    }

    @Override
    public void execute() {
        mAngle = Drivetrain.getInstance().getAngle() - desiredAngle; 
        mFeedforward = (Drivetrain.getInstance().getGyroRate()*Constants.Turret.kTurningFeedForward);
        super.execute();
    }

}
