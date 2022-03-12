package frc.robot.commands.climber;

import java.util.function.Supplier;

import frc.robot.subsystems.Climber;

public class RuntimeOpenHand extends OpenHand {
    private final Supplier<Climber.Hand> mHandGetter;

    public RuntimeOpenHand(Supplier<Climber.Hand> handGetter) {
        super(null);
        mHandGetter = handGetter;
    }

    @Override
    public void initialize() {
        mHand = mHandGetter.get();
        super.initialize();
    }
}
