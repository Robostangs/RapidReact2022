package frc.robot.commands.climber;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.commands.turret.Protect;
import frc.robot.subsystems.Climber;

public class ClimbSequenceManager implements Sendable {
    enum ClimbState {
        kStarting,
        kCallibrated,
        kTall,
        kPreppedMid,
        kGrabbingMid,
        kMid,
        kPreppedHigh,
        kGrabbingHigh,
        kMidHigh,
        kHigh,
        kPreppedTraversal,
        kGrabbingTraversal,
        kHighTraversal,
        kTraversal,
    }

    private class Transition {
        private CommandBase behavior;
        private Supplier<ClimbState> determineNextState;

        public Transition(CommandBase behavior, Supplier<ClimbState> determineNextState) {
            this.behavior = behavior;
            this.determineNextState = determineNextState;
        }

        private Transition(CommandBase behavior, ClimbState nextState) {
            this(behavior, () -> nextState);
        }
    }

    private static ClimbSequenceManager instance;
    private final Climber mClimber = Climber.getInstance();
    private final CommandScheduler mCommandScheduler = CommandScheduler.getInstance();
    private final Climber.Hand mHandA;
    private final Climber.Hand mHandB;
    {
        var hands = Climber.getInstance().getHands();
        mHandA = hands[0];
        mHandB = hands[0];
    }
    private final Map<ClimbState, Transition> mNextCommands = new HashMap<ClimbState, Transition>() {{
        put(ClimbState.kStarting, new Transition(new ClimbPrep(), ClimbState.kCallibrated));
        put(ClimbState.kCallibrated, new Transition(new Protect().andThen(new ReleaseElevator()).withName("Lift climber"), ClimbState.kHigh));
        put(ClimbState.kTall, new Transition(new RotateToPosition(Constants.Climber.Rotator.kStartingAngle).withName("Rotate to Mid Bar"), ClimbState.kPreppedMid));
        put(ClimbState.kPreppedMid, new Transition(new DriveToMidBar(), ClimbState.kGrabbingMid));
        put(ClimbState.kGrabbingMid, new Transition(new CloseHand(mHandA), ClimbState.kMid));
    }};
    private final Map<ClimbState, Transition> mAutoCommands = new HashMap<ClimbState, Transition>() {{
        put(ClimbState.kMid, new Transition(new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockUnlockedPositon), ClimbState.kPreppedHigh));
    }};
    private ClimbState mCurrentState = ClimbState.kStarting;
    private CommandBase waitlistedCommand;

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("ClimbSequence State", () -> getState().toString(), null);
        builder.addStringProperty("Waitlisted Command", () -> waitlistedCommand != null ? waitlistedCommand.toString() : "None", null);
        builder.addStringProperty("Next Command", () -> getNextCommand() != null ? getNextCommand().toString() : "None", null);
    }

    public void periodic() {

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

    CommandBase getNextCommand() {
        return mNextCommands.get(getState()).behavior;
    }

    void waitlist(CommandBase nextCommand) {
        if(nextCommand != null) {

        }
    }

    public void advance() {
        if(mCommandScheduler.requiring(mClimber) != null) {
            interrupt();
        }
    }

    public void interrupt() {
        getNextCommand().schedule();
    }
}
