package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Turret;

public class GoHome extends CommandBase {
    private final Turret mTurret = Turret.getInstance();
    private final Timer mTimer = new Timer();

    public GoHome() {
        addRequirements(mTurret);
        setName("Home Turret");
    }

    @Override
    public void initialize() {
        mTimer.reset();
        mTurret.setSoftLimitEnable(false);
        mTimer.start();
        mTurret.configClearPosition(true);
    }

    @Override
    public void execute() {
        if (mTimer.get() <= 3) {
            System.out.println("I ran " + Double.toString(Timer.getFPGATimestamp()));
            mTurret.setSpeed(Constants.Turret.kRotationMotorSpeed);
        } else {
            System.out.println("I time exceededs " + Double.toString(Timer.getFPGATimestamp()));

            mTurret.setSpeed(0);
            mTurret.configMaxSpeed(0.2);
            cancel();
        }
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
        }
        mTurret.configClearPosition(false);
    }
}
