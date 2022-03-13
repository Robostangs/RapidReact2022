package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class Home extends CommandBase {

    private final Shooter mShooter = Shooter.getInstance();
    private final Timer mTimer = new Timer();

    public Home() {
        addRequirements(mShooter);
        setName("Home Hood");
    }

    @Override
    public void initialize() {
        mTimer.reset();
        mTimer.start();
        mShooter.setSoftLimitEnable(false);
        mShooter.setClearPosition(true);
    }

    @Override
    public void execute() {
        if (mTimer.get() <= 3) {
            System.out.println("I ran " + Double.toString(Timer.getFPGATimestamp()));
            mShooter.setHoodPower(Constants.Shooter.kHomeSpeed);
        } else {
            System.out.println("I time exceededs " + Double.toString(Timer.getFPGATimestamp()));
            mShooter.setHoodPower(0);
            mShooter.setMaxSpeed(0.25);
            mShooter.resetHoodEncoder();
            cancel();
        }
    }

    @Override
    public boolean isFinished() {
        return mShooter.getForwardLimit() || (mTimer.get() >= 5);
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            mShooter.setHoodPower(0);
            mShooter.setMaxSpeed(1);
            mShooter.setHomed(true);
        }
        mShooter.setSoftLimitEnable(true);
        mShooter.setClearPosition(false);
        mShooter.setHomed(true);
    }
}
