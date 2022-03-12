package frc.robot.commands.climber;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class CloseHand extends CommandBase {
    protected Climber.Hand mHand;
    private final double mPosition;

    public CloseHand(Climber.Hand hand, double position) {
        addRequirements(Climber.getInstance());
        setName("Close Hand");
        mHand = hand;
        mPosition = position;
    }

    public CloseHand(Climber.Hand hand) {
        this(hand, Constants.Climber.Hand.kClawForwardSoftLimit);
    }

    @Override
    public void initialize() {
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
