package frc.robot.commands.climber;

import java.util.function.Supplier;

import frc.robot.subsystems.Climber;

public class RuntimeCloseHand extends CloseHand {
    private final Supplier<Climber.Hand> mHandGetter;

    public RuntimeCloseHand(Supplier<Climber.Hand> handGetter) {
        super(null);
        mHandGetter = handGetter;
    }

    @Override
    public void initialize() {
        mHand = mHandGetter.get();
        super.initialize();
    }
}
