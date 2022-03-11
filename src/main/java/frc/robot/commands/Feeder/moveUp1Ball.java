package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class MoveUp1Ball extends CommandBase {

    private final Feeder mFeeder = Feeder.getInstance();
    private final Timer mTimer = new Timer();

    public MoveUp1Ball() {
        addRequirements(mFeeder);
        setName("Move Feeder");
    }

    @Override
    public void initialize() {
        mTimer.reset();
        mTimer.start();
        mFeeder.moveBelt(Constants.Feeder.kSlowBeltSpeed);
    }

    @Override
    public void execute() {
        if (mTimer.get() <= 1) {
            if (mFeeder.getShooterSensorLight()) {
                mFeeder.moveBelt(0);
            }
        } else {
            mFeeder.moveBelt(0);
            cancel();
        }
    }

    @Override
    public boolean isFinished() {
        return mFeeder.getShooterSensorLight() || (mTimer.get() >= 3);
    }
}
