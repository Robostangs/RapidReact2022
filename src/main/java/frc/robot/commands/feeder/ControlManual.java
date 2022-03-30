package frc.robot.commands.feeder;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class ControlManual extends CommandBase {

    private final Feeder mFeeder = Feeder.getInstance();
    private final Supplier<Double> mPowerSupplier;

    public ControlManual(Supplier<Double> powerSupplier) {
        addRequirements(mFeeder);
        setName("Manual Move Feeder");
        mPowerSupplier = powerSupplier;
    }

    @Override
    public void execute() {
        mFeeder.moveBelt(mPowerSupplier.get());
    }

    @Override
    public void end(boolean interrupted) {
        mFeeder.moveBelt(0);
        super.end(interrupted);
    }
}
