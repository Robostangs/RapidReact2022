package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class ToRobotAngle extends CommandBase{

    protected static final Turret mTurret = Turret.getInstance();
    protected double mAngle, mFeedforward;

    public ToRobotAngle(double angle, double feedforward) {
        this.setName("To Robot Angle");
        this.addRequirements(mTurret);
        mAngle = angle;
        mFeedforward = feedforward;
    }

    protected ToRobotAngle(){};

    @Override
    public void execute() {
        mTurret.setAngleSetpoint(mAngle, mFeedforward);
    }

    @Override
    public void end(boolean interrupted) {
        mTurret.setPercentSpeed(0);
    }
}
