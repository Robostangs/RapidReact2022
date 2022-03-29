package frc.robot.commands.feeder;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class DefaultFeeder extends CommandBase {

    private final Feeder mFeeder = Feeder.getInstance();
    private final Debouncer inDebouncer = new Debouncer(Constants.Feeder.kInDebounceTime);
    private final Debouncer outDebouncer = new Debouncer(Constants.Feeder.kOutDebounceTime);

    public DefaultFeeder() {
        addRequirements(mFeeder);
        setName("Move Feeder");
    }

    @Override
    public void execute() {
        if (outDebouncer.calculate(mFeeder.getShooterSensorLight() || !mFeeder.getShooterSensorDark())) {
            mFeeder.moveBelt(0);
        } else if (inDebouncer.calculate(mFeeder.getIntakeSensorLight() || !mFeeder.getIntakeSensorDark())) {
            mFeeder.moveBelt(Constants.Feeder.kBeltSpeed);
        }
    }
}
