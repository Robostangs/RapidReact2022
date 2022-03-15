package frc.robot.commands.climber;

import frc.robot.Constants;

public class DumbRotateToPosition extends Rotate {
    private static final double mSpeedMagnitude = Constants.Climber.Rotator.kDumbRotatorSpeed;
    private final double mPosition;
    private double mStart;

    public DumbRotateToPosition(double position) {
        super(0);
        mPosition = position;
    }

    @Override
    public void initialize() {
        mStart = mRotator.getPosition();
        mSpeed = mStart < mPosition ? mSpeedMagnitude : -mSpeedMagnitude;
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        if(mStart < mPosition) {
            return mRotator.getPosition() > mPosition - Constants.Climber.Rotator.kDumbPositionTolerance;
        } else {
            return mRotator.getPosition() < mPosition + Constants.Climber.Rotator.kDumbPositionTolerance;
        }
    }
}
