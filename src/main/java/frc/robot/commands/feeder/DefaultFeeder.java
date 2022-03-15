package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class DefaultFeeder extends CommandBase {

    private final Feeder mFeeder = Feeder.getInstance();

    public DefaultFeeder() {
        addRequirements(mFeeder);
        setName("Move Feeder");
    }

    @Override
    public void execute() {
        if (mFeeder.getIntakeSensorLight() && !mFeeder.getShooterSensorLight()) {
            mFeeder.moveBelt(Constants.Feeder.kSlowBeltSpeed);
        } else {
            mFeeder.moveBelt(0);
        }
    }
}
