package frc.robot.commands.climber;

import java.util.function.Supplier;

import frc.robot.Constants;

public class RotateWithWiggle extends Rotate {
    private final double mBaseSpeed;
    private final Supplier<Double> mWiggleSupplier;

    public RotateWithWiggle(double speed, Supplier<Double> wiggleSupplier) {
        super(speed);
        setName("Rotate with Wiggle");
        mBaseSpeed = speed;
        mWiggleSupplier = wiggleSupplier;
    }

    @Override
    public void execute() {
        mSpeed = mBaseSpeed + Constants.Climber.Rotator.kWiggleConstant * mWiggleSupplier.get();
        super.execute();
    }
}
