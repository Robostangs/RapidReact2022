package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class OpenHand extends CommandBase {
    protected Climber.Hand mHand;
    protected final double mSpeed;

    public OpenHand(Climber.Hand hand, double speed) {
        addRequirements(hand);
        setName("Open Hand");
        mHand = hand;
        mSpeed = speed;
    }

    public OpenHand(Climber.Hand hand) {
        this(hand, Constants.Climber.Hand.kClawDefaultOpenSpeed);
    }

    @Override
    public void initialize() {
        mHand.setClawSpeed(mSpeed);
    }

    @Override
    public boolean isFinished() {
        if (mHand.isLongFullyOpen()) {
            mHand.zeroClawEncoder();
            DataLogManager.log("Normal opening recallibrating");
            return true;
        }
        return mHand.getClawPosition() <= 5;
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setClawSpeed(0);
    }
}
