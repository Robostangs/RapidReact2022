package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class CloseHand extends CommandBase {
    private final HandHolder mHandContainer;
    private Climber.Hand mHand;
    private final double mSpeed;

    public CloseHand(HandHolder handContainer, double speed) {
        addRequirements(Climber.getInstance());
        setName("Close Hand");
        mHandContainer = handContainer;
        mSpeed = Math.abs(speed);
    }

    public CloseHand(HandHolder hand) {
        this(hand, Constants.Climber.Hand.kClawDefaultMoveSpeed);
    }

    public CloseHand(Climber.Hand hand, double speed) {
        addRequirements(hand);
        setName("Close Hand");
        mHandContainer = null;
        mHand = hand;
        mSpeed = Math.abs(speed);
    }

    public CloseHand(Climber.Hand hand) {
        this(hand, Constants.Climber.Hand.kClawDefaultMoveSpeed);
    }

    @Override
    public void initialize() {
        if(mHandContainer != null) {
            mHand = mHandContainer.hand;
        }
        if(mHand.getCallibrationStatus() == Climber.HandCallibrationStatus.kCalibrated) {
            mHand.setClawSpeed(mSpeed);
        }
    }

    @Override
    public boolean isFinished() {
        return mHand.getCallibrationStatus() != Climber.HandCallibrationStatus.kCalibrated
            || mHand.getClawPosition() >= Constants.Climber.Hand.kClawForwardSoftLimit;
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setClawSpeed(0);
    }
}
