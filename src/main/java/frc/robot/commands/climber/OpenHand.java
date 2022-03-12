package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class OpenHand extends CommandBase {
    private final HandHolder mHandHolder;

    public OpenHand(HandHolder hand) {
        addRequirements(Climber.getInstance());
        setName("Open Hand");
        mHandHolder = hand;
    }

    @Override
    public void initialize() {
        mHandHolder.hand.setClawSpeed(Constants.Climber.Hand.kClawDefaultOpenSpeed);
    }

    @Override
    public boolean isFinished() {
        return mHandHolder.hand.isFullyOpen();
    }

    @Override
    public void end(boolean interrupted) {
        mHandHolder.hand.setClawSpeed(0);
    }
}
