package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimbSequenceManager {
    enum ClimbState {
        kStarting,
        kHorizontal,
        kCallibrated,
        kPositionedMid,
        kMid,
        kGrabbedMid,
        kPreppedHigh,
        kGrabbingHigh,
        kMidHigh,
        kHigh,
        kPreppedTraversal,
        kGrabbingTraversal,
        kHighTraversal,
        kTraversal,
    }

    private static ClimbSequenceManager instance;
    private ClimbState mCurrentState = ClimbState.kStarting;

    public static ClimbSequenceManager getInstance() {
        if(instance == null) {
            instance = new ClimbSequenceManager();
        }
        return instance;
    }

    private ClimbSequenceManager() {}

    void setState(ClimbState newState) {
        mCurrentState = newState;
    }

    ClimbState getState() {
        return mCurrentState;
    }

    public CommandBase getNextCommand() {
        return null;
    }
}
