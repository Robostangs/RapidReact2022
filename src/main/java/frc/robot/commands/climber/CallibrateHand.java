package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.DataLogManager;
import frc.robot.subsystems.Climber.Hand;

public class CallibrateHand extends OpenHand {

    public CallibrateHand(Hand hand, double speed) {
        super(hand, speed);
        setName("Hand Callibration");
    }

    public CallibrateHand(Hand hand) {
        super(hand);
        setName("Hand Callibration");
    }

    @Override
    public boolean isFinished() {
        return mHand.isFullyOpen();
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted) {
            mHand.zeroClawEncoder();
        } else {
            DataLogManager.log("Hand calibration interrupted!");
        }
        super.end(interrupted);
    }
}
