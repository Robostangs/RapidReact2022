package frc.robot.commands.climber;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimbSequenceManager implements Sendable {
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
    private CommandBase waitlistedCommand;

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("ClimbSequence State", () -> getState().toString(), null);
        builder.addStringProperty("Waitlisted Command", () -> waitlistedCommand != null ? waitlistedCommand.toString() : "None", null);
        builder.addStringProperty("Next Command", () -> getNextCommand() != null ? getNextCommand().toString() : "None", null);
    }

    public static ClimbSequenceManager getInstance() {
        if(instance == null) {
            instance = new ClimbSequenceManager();
        }
        return instance;
    }

    private ClimbSequenceManager() {
        SendableRegistry.addLW(this, "Climber", "ClimbSequenceManager");
    }

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
