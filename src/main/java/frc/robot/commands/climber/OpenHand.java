package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.HandCallibrationStatus;

public class OpenHand extends CommandBase {
    private final HandHolder mHandContainer;
    private Climber.Hand mHand;
    private final double mSpeed;

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
        addRequirements(Climber.getInstance());
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
        if(mHand.getCallibrationStatus() == Climber.HandCallibrationStatus.kNotCalibrated) {
            mHand.setCallibrationStatus(HandCallibrationStatus.kCalibrating);
        }
        mHand.setClawSpeed(mSpeed);
    }

    @Override
    public boolean isFinished() {
        return mHandContainer.hand.isFullyOpen();
    }

    @Override
    public void end(boolean interrupted) {
        if(interrupted && mHand.getCallibrationStatus() == Climber.HandCallibrationStatus.kCalibrating) {
            mHand.setCallibrationStatus(HandCallibrationStatus.kNotCalibrated);
            System.out.println("Hand calibration interrupted!");
        }   
        mHandContainer.hand.setClawSpeed(0);
    }
}
