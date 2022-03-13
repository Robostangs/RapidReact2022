package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class SetHandLockPosition extends WaitCommand {
    private final HandHolder mHandHolder;
    private Climber.Hand mHand;
    private final double mPosition;
    private boolean misAtSetpoint = false;

    public SetHandLockPosition(HandHolder handContainer, double position) {
        super(Constants.Climber.Hand.kHandLockWaitTime);
        addRequirements(Climber.getInstance());
        setName("Set Hand Lock Position");
        mHandHolder = handContainer;
        mPosition = position;
    }

    public SetHandLockPosition(Climber.Hand hand, double position) {
        super(Constants.Climber.Hand.kHandLockWaitTime);
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
        if(mHand.getLockPosition() == mPosition) {
            misAtSetpoint = true;
        }
        mHand.setLockReference(mPosition);
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        if(misAtSetpoint) {
            return true;
        }
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setLockReference(mHand.getLockPosition());
        super.end(interrupted);
    }
}
