package frc.robot.commands.climber;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.turret.Protect;
import frc.robot.subsystems.Climber;

public class ClimbSequenceManager implements Sendable {
    public enum ClimbState {
        kStarting,
        kCallibrated,
        kTall,
        kPreppedMid,
        kHitMid,
        kMid,
        kPreppedHigh,
        kHitHigh,
        kUnknownHigh,
        kMissedHigh,
        kMidHigh,
        kHigh,
        kPreppedTraversal,
        kHitTraversal,
        kUnknownTraversal,
        kMissedTraversal,
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
    private final Climber.Rotator mRotator = mClimber.getRotator();
    private final CommandScheduler mCommandScheduler = CommandScheduler.getInstance();
    private final Climber.Hand mHandA;
    private final Climber.Hand mHandB;
    {
        var hands = Climber.getInstance().getHands();
        mHandA = hands[0];
        mHandB = hands[1];
    }
    private final Map<ClimbState, Transition> mTransitions = new HashMap<ClimbState, Transition>() {{
        put(ClimbState.kStarting, new Transition(new ClimbPrep(), ClimbState.kCallibrated));
        put(ClimbState.kCallibrated, new Transition(new ReleaseElevator().deadlineWith(new Protect()).withName("Lift climber"), ClimbState.kTall));
        put(ClimbState.kTall, new Transition(new RotateToPosition(Constants.Climber.Rotator.kStartingAngle).withName("Rotate to Mid Bar"), ClimbState.kPreppedMid));
        put(ClimbState.kPreppedMid, new Transition(new DriveToMidBar(), ClimbState.kHitMid));
        put(ClimbState.kHitMid, new Transition(new CloseHand(mHandA).withName("Grab Mid Bar"), ClimbState.kMid));
        put(ClimbState.kMid, new Transition(new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockUnlockedPositon), ClimbState.kPreppedHigh, true));
        put(ClimbState.kPreppedHigh, new Transition(new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed).withInterrupt(() -> (mHandB.getEngaged())).withName("Rotate to High"), ClimbState.kHitHigh));
        put(ClimbState.kHitHigh, new Transition(new CloseHand(mHandB).deadlineWith(new Rotate(Constants.Climber.Rotator.kClimbHoldSpeed)).withName("Grab High"), ClimbState.kUnknownHigh, true));
        put(ClimbState.kUnknownHigh, new Transition(new InstantCommand(), () -> mGrabbedBarSupplier.get() ? ClimbState.kMidHigh : ClimbState.kMissedHigh));
        put(ClimbState.kMissedHigh, new Transition(new OpenHand(mHandB).withName("Reopen Hand B"), ClimbState.kMid, true));
        put(ClimbState.kMidHigh, new Transition(new OpenHand(mHandA).deadlineWith(new RotateWithWiggle(Constants.Climber.Rotator.kCGHoldSpeed, () -> mWiggleSupplier.get())).withName("Let go mid"), ClimbState.kHigh, true));
        put(ClimbState.kHigh, new Transition(new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockLockedPositon).alongWith(new SetHandLockPosition(mHandB, Constants.Climber.Hand.kClawLockUnlockedPositon)).andThen(mRotator::setNeutralModeCoast).withName("Prepare lock positions"), ClimbState.kPreppedTraversal));
        put(ClimbState.kPreppedTraversal, new Transition(new InstantCommand(mRotator::setNeutralModeBrake).andThen(new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed).withInterrupt(() -> (mHandA.getEngaged()))).withName("Rotate to Traversal"), ClimbState.kHitTraversal));
        put(ClimbState.kHitTraversal, new Transition(new CloseHand(mHandA).deadlineWith(new Rotate(Constants.Climber.Rotator.kClimbHoldSpeed)).withName("Grab Traversal"), ClimbState.kUnknownTraversal, true));
        put(ClimbState.kUnknownTraversal, new Transition(new InstantCommand(), () -> mGrabbedBarSupplier.get() ? ClimbState.kHighTraversal : ClimbState.kMissedTraversal));
        put(ClimbState.kMissedTraversal, new Transition(new OpenHand(mHandA).withName("Reopen Hand A"), ClimbState.kHigh, true));
        put(ClimbState.kHighTraversal, new Transition(new OpenHand(mHandB).deadlineWith(new RotateWithWiggle(Constants.Climber.Rotator.kCGHoldSpeed, () -> mWiggleSupplier.get())).withName("Let go high"), ClimbState.kTraversal, true));
        put(ClimbState.kTraversal, new Transition(new InstantCommand(mRotator::setNeutralModeCoast).withName("Set Rotator Neutral Coast"), ClimbState.kTraversal));
    }};
    private ClimbState mCurrentState = ClimbState.kStarting;
    private Transition mCurrentTransition = mTransitions.get(mCurrentState);
    private Supplier<Double> mWiggleSupplier;
    private Supplier<Boolean> mGrabbedBarSupplier;

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("ClimbSequence State", () -> mCurrentState.toString(), null);
        builder.addStringProperty("ClimbSequence Command", () -> mCurrentTransition != null ? mCurrentTransition.behavior.getName() : "", null);
        builder.addBooleanProperty("ClimbSequence Command Active", this::isCurrentBehaviorScheduled, null);
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
        SmartDashboard.putString("Reset Climber State?", "No");
    }

    private void checkForTransitionFinish(Command command) {
        if(command == mCurrentTransition.behavior) {
            DataLogManager.log("Naturally finished " + mCurrentTransition.behavior.getName() + " of " + mCurrentState);
            advanceState();
        }
    }

    public void setWiggleSupplier(Supplier<Double> wiggleSupplier) {
        mWiggleSupplier = wiggleSupplier;
    }

    public void setGrabbedBarSupplier(Supplier<Boolean> grabbedBarSupplier) {
        mGrabbedBarSupplier = grabbedBarSupplier;
    }

    public ClimbState getState() {
        return mCurrentState;
    }

    public void setState(ClimbState newState) {
        mCurrentState = newState;
        SmartDashboard.putString("Current State", newState.toString());
        mCurrentTransition = mTransitions.get(newState);
        if (mCurrentTransition.autoSchedule) {
            mCommandScheduler.schedule(mCurrentTransition.behavior);
        }
    }

    private boolean isCurrentBehaviorScheduled() {
        return mCurrentTransition != null && mCurrentTransition.behavior != null && mCommandScheduler.isScheduled(mCurrentTransition.behavior);
    }

    public void proceed() {
        if (SmartDashboard.getString("Reset Climber State?", "No").equals("Confirm")) {
            SmartDashboard.putString("Reset Climber State?", "Understood");
            mCurrentTransition = new Transition(new InstantCommand(mRotator::setNeutralModeBrake), ClimbState.kStarting);
        }
        if (!isCurrentBehaviorScheduled()) {
            DataLogManager.log("Starting " + mCurrentTransition.behavior.getName() + " of " + mCurrentState);
            mCurrentTransition.behavior.schedule();
        }
    }

    public void interrupt() {
        mCurrentTransition.behavior.cancel();
        DataLogManager.log("Interrupting " + mCurrentTransition.behavior.getName() + " of " + mCurrentState);
        advanceState();
    }

    private void advanceState() {
        final ClimbState nextState = mCurrentTransition.determineNextState.get();
        DataLogManager.log("Advancing state from " + mCurrentState + " to " + nextState);
        setState(nextState);
    }
}
