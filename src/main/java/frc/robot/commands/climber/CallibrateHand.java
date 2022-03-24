package frc.robot.commands.climber;

import frc.robot.subsystems.Climber.Hand;
import frc.robot.subsystems.Climber.HandCallibrationStatus;

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
    public void initialize() {
        super.initialize();
        mHand.setCallibrationStatus(HandCallibrationStatus.kCalibrating);
    }

    @Override
    public boolean isFinished() {
        return mHand.isFullyOpen();
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted) {
            mHand.zeroClawEncoder();
            mHand.setCallibrationStatus(HandCallibrationStatus.kCalibrated);
        } else {
            mHand.setCallibrationStatus(HandCallibrationStatus.kNotCalibrated);
            System.out.println("Hand calibration interrupted!");
        }
        super.end(interrupted);
    }
}
