package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class SetHandLockPosition extends CommandBase {
    private final HandHolder mHandHolder;
    private Climber.Hand mHand;
    private final double mPosition;

    public SetHandLockPosition(HandHolder handContainer, double position) {
        addRequirements(Climber.getInstance());
        setName("Set Hand Lock Position");
        mHandHolder = handContainer;
        mPosition = position;
    }

    public SetHandLockPosition(Climber.Hand hand, double position) {
        addRequirements(hand);
        setName("Set Hand Lock Position");
        mHandHolder = null;
        mHand = hand;
        mPosition = position;
    }

    @Override
    public void initialize() {
        if(mHandHolder != null) {
            mHand = mHandHolder.hand;
        }
        mHand.setLockReference(mPosition);
    }

    @Override
    public boolean isFinished() {
        return mHand.atLockReference();
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setLockReference(mHand.getLockPosition());
    }
}
