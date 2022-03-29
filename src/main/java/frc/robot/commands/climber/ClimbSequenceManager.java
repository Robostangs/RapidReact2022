package frc.robot.commands.climber;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
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
        private boolean autoSchedule = false;

        public Transition(CommandBase behavior, Supplier<ClimbState> determineNextState, boolean autoSchedule) {
            this.behavior = behavior;
            this.determineNextState = determineNextState;
            this.autoSchedule = autoSchedule;
        }

        private Transition(CommandBase behavior, ClimbState nextState, boolean autoSchedule) {
            this(behavior, () -> nextState, autoSchedule);
        }

        public Transition(CommandBase behavior, Supplier<ClimbState> determineNextState) {
            this(behavior, determineNextState, false);
        }
        
        private Transition(CommandBase behavior, ClimbState nextState) {
            this(behavior, nextState, false);
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
    private final Map<ClimbState, Transition> mTransitions = new HashMap<ClimbState, Transition>() {{
        put(ClimbState.kStarting, new Transition(new ClimbPrep(), ClimbState.kCallibrated));
        put(ClimbState.kCallibrated, new Transition(new Protect().andThen(new ReleaseElevator()).withName("Lift climber"), ClimbState.kHigh));
        put(ClimbState.kTall, new Transition(new RotateToPosition(Constants.Climber.Rotator.kStartingAngle).withName("Rotate to Mid Bar"), ClimbState.kPreppedMid));
        put(ClimbState.kPreppedMid, new Transition(new DriveToMidBar(), ClimbState.kGrabbingMid));
        put(ClimbState.kGrabbingMid, new Transition(new CloseHand(mHandA).withName("Grab Mid Bar"), ClimbState.kMid));
        put(ClimbState.kMid, new Transition(new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockUnlockedPositon), ClimbState.kPreppedHigh, true));
        put(ClimbState.kPreppedHigh, new Transition(new GrabNextBar(mHandB).withName("Grab High Bar"), () -> mHandB.getEngaged() ? ClimbState.kMidHigh : ClimbState.kMid));
        put(ClimbState.kMidHigh, new Transition(new ParallelDeadlineGroup(new OpenHand(mHandA).andThen(new PrintCommand("Let go mid done")), new RotateWithWiggle(Constants.Climber.Rotator.kCGHoldSpeed, mWiggleSupplier)), ClimbState.kHigh));
        put(ClimbState.kHigh, new Transition(new ParallelCommandGroup(new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockLockedPositon), new SetHandLockPosition(mHandB, Constants.Climber.Hand.kClawLockUnlockedPositon)), ClimbState.kPreppedTraversal));
        put(ClimbState.kPreppedTraversal, new Transition(new GrabNextBar(mHandA).withName("Grab Traversal Bar"), () -> mHandA.getEngaged() ? ClimbState.kHighTraversal : ClimbState.kHigh));
        put(ClimbState.kHighTraversal, new Transition(new ParallelDeadlineGroup(new OpenHand(mHandB).andThen(new PrintCommand("Let go high done")), new RotateWithWiggle(Constants.Climber.Rotator.kCGHoldSpeed, mWiggleSupplier)), ClimbState.kTraversal));
    }};
    private ClimbState mCurrentState = ClimbState.kStarting;
    private Transition mCurrentTransition;
    private Supplier<Double> mWiggleSupplier;

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("ClimbSequence State", () -> mCurrentState.toString(), null);
        builder.addStringProperty("ClimbSequence Command", () -> mCurrentTransition != null ? mCurrentTransition.behavior.getName() : "", null);
    }

    public static ClimbSequenceManager getInstance() {
        if(instance == null) {
            instance = new ClimbSequenceManager();
        }
        return instance;
    }

    private ClimbSequenceManager() {
        SendableRegistry.addLW(this, "Climber", "ClimbSequenceManager");
        mCommandScheduler.onCommandFinish(this::checkForTransitionFinish);
    }

    private void checkForTransitionFinish(Command command) {
        if(command == mCurrentTransition.behavior) {
            advanceState();
        }
    }

    public void setWiggleSupplier(Supplier<Double> wiggleSupplier) {
        mWiggleSupplier = wiggleSupplier;
    }

    public void proceed() {
        if(mCommandScheduler.requiring(mClimber) != null) {
            interrupt();
        }
    }

    public void interrupt() {
        mCurrentTransition.behavior.cancel();
        advanceState();
    }

    private void advanceState() {
        mCurrentState = mCurrentTransition.determineNextState.get();
        mCurrentTransition = mTransitions.get(mCurrentState);
        if (mCurrentTransition.autoSchedule) {
            mCommandScheduler.schedule(mCurrentTransition.behavior);
        }
    }
}
