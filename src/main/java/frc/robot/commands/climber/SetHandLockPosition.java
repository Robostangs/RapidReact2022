package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class SetHandLockPosition extends WaitCommand {
    private Climber.Hand mHand;
    private final double mPosition;
    private boolean misAtSetpoint = false;

    public SetHandLockPosition(Climber.Hand hand, double position) {
        super(Constants.Climber.Hand.kHandLockWaitTime);
        addRequirements(hand);
        setName("Set Hand Lock Position");
        mHand = hand;
        mPosition = position;
    }

    @Override
    public void initialize() {
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
