package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Turret;

public class GoHome extends CommandBase {
    private final Turret mTurret = Turret.getInstance();

    public GoHome() {
        addRequirements(mTurret);
        setName("Home Turret");
    }

    @Override
    public void initialize() {
        mTurret.setSoftLimitEnable(false);
        mTurret.configClearPosition(true);
    }

    @Override
    public void execute() {
        System.out.println("I ran " + Double.toString(Timer.getFPGATimestamp()));
        mTurret.setAngularVelocitySetpoint(Constants.Turret.kRotationMotorSpeed, Constants.Turret.kTurningFeedForward);
    }

    @Override
    public boolean isFinished() {
        return mTurret.getReverseLimit();
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            mTurret.setSoftLimitEnable(true);
            mTurret.configMaxSpeed(1);
            mTurret.setHomed(true);
        } else if(interrupted) {
            mTurret.setAngularVelocitySetpoint(0, Constants.Turret.kTurningFeedForward);
            mTurret.configMaxSpeed(0.2);
        }
        mTurret.configClearPosition(false);
    }
}
