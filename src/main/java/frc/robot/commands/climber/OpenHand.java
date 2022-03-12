package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class OpenHand extends CommandBase {
    protected Climber.Hand mHand;

    public OpenHand(Climber.Hand hand) {
        addRequirements(Climber.getInstance());
        setName("Open Hand");
        mHand = hand;
    }

    @Override
    public void initialize() {
        mHand.setClawSpeed(Constants.Climber.Hand.kClawDefaultOpenSpeed);
    }

    @Override
    public boolean isFinished() {
        return mHand.isFullyOpen();
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setClawSpeed(0);
    }
}
