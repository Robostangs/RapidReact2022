package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class CloseHand extends CommandBase {
    private final HandHolder mHandContainer;
    private final double mPosition;

    public CloseHand(HandHolder handHolder, double position) {
        addRequirements(Climber.getInstance());
        setName("Close Hand");
        mHandContainer = handHolder;
        mPosition = position;
    }

    public CloseHand(HandHolder hand) {
        this(hand, Constants.Climber.Hand.kClawForwardSoftLimit);
    }

    @Override
    public void initialize() {
        mHandContainer.hand.setClawReference(mPosition);
    }

    @Override
    public boolean isFinished() {
        return mHandContainer.hand.atReference();
    }

    @Override
    public void end(boolean interrupted) {
        mHandContainer.hand.setClawSpeed(0);
    }
}
