package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class CloseHand extends CommandBase {
    private final HandHolder mHandContainer;
    private Climber.Hand mHand;
    private final double mPosition;

    public CloseHand(HandHolder handContainer, double position) {
        addRequirements(Climber.getInstance());
        setName("Close Hand");
        mHandContainer = handContainer;
        mPosition = position;
    }

    public CloseHand(HandHolder hand) {
        this(hand, Constants.Climber.Hand.kClawForwardSoftLimit);
    }

    public CloseHand(Climber.Hand hand, double position) {
        addRequirements(hand);
        setName("Close Hand");
        mHandContainer = null;
        mHand = hand;
        mPosition = position;
    }

    public CloseHand(Climber.Hand hand) {
        this(hand, Constants.Climber.Hand.kClawForwardSoftLimit);
    }

    @Override
    public void initialize() {
        if(mHandContainer != null) {
            mHand = mHandContainer.hand;
        }
        mHand.setClawReference(mPosition);
    }

    @Override
    public boolean isFinished() {
        return mHand.atReference();
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setClawSpeed(0);
    }
}
