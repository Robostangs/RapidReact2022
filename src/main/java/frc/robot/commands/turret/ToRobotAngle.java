package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class ToRobotAngle extends CommandBase{

    protected static final Turret mTurret = Turret.getInstance();
    protected double mAngle, mFeedforward;

    public ToRobotAngle(double angle, double feedforward) {
        setName("To Robot Angle");
        addRequirements(mTurret);
        mAngle = angle;
        mFeedforward = feedforward;
    }

    public ToRobotAngle(double angle) {
        this(angle, 0);
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
