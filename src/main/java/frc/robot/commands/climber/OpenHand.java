package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.HandCallibrationStatus;

public class OpenHand extends CommandBase {
    protected final HandHolder mHandContainer;
    protected Climber.Hand mHand;
    protected final double mSpeed;

    public OpenHand(HandHolder handContainer, double speed) {
        addRequirements(Climber.getInstance());
        setName("Open Hand");
        mHandContainer = handContainer;
        mSpeed = speed;
    }

    public OpenHand(HandHolder hand) {
        this(hand, Constants.Climber.Hand.kClawDefaultOpenSpeed);
    }

    public OpenHand(Climber.Hand hand, double speed) {
        addRequirements(hand);
        setName("Open Hand");
        mHandContainer = null;
        mHand = hand;
        mSpeed = speed;
    }

    public OpenHand(Climber.Hand hand) {
        this(hand, Constants.Climber.Hand.kClawDefaultOpenSpeed);
    }

    @Override
    public void initialize() {
        if (mHandContainer != null) {
            mHand = mHandContainer.hand;
        }
        mHand.setClawSpeed(mSpeed);
    }

    @Override
    public boolean isFinished() {
        return mHand.getClawPosition() <= 5;
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setClawSpeed(0);
    }
}
