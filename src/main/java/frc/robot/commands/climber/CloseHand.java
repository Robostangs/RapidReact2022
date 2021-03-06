package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class CloseHand extends CommandBase {
    private Climber.Hand mHand;
    private final double mSpeed;

    public CloseHand(Climber.Hand hand, double speed) {
        addRequirements(hand);
        setName("Close Hand");
        mHand = hand;
        mSpeed = Math.abs(speed);
    }

    public CloseHand(Climber.Hand hand) {
        this(hand, Constants.Climber.Hand.kClawDefaultMoveSpeed);
    }

    @Override
    public void initialize() {
        mHand.setClawSpeed(mSpeed);
    }

    @Override
    public boolean isFinished() {
        return mHand.getClawPosition() >= Constants.Climber.Hand.kClawForwardSoftLimit;
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setClawSpeed(0);
    }
}
