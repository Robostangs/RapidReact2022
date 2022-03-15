package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class ToRobotAngle extends CommandBase{

    private static final Turret mTurret = Turret.getInstance();
    protected double mAngle, mFeedforward;

    public ToRobotAngle(double angle, double feedforward) {
        mAngle = angle;
        mFeedforward = feedforward;
    }

    public ToRobotAngle(){};

    @Override
    public void initialize() {
        this.setName("To Robot Angle");
        this.addRequirements(mTurret);
    }

    @Override
    public void execute() {
        mTurret.setAngleSetpoint(mAngle, mFeedforward);
    }

    @Override
    public void end(boolean interrupted) {
        mTurret.setAngularVelocitySetpoint(0);
    }

}