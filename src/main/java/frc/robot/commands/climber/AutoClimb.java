package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.turret.Protect;
import frc.robot.subsystems.Climber;

public class AutoClimb extends SequentialCommandGroup {
    private static final Climber mClimber = Climber.getInstance();
    private static final Climber.Rotator mRotator = mClimber.getRotator();
    private static final Climber.Hand mHandA;
    private static final Climber.Hand mHandB;
    static {
        final Climber.Hand[] hands = mClimber.getHands();
        mHandA = hands[0];
        mHandB = hands[1];
    }

    public AutoClimb() {
        setName("Auto Climb");
        addCommands(
            new ScheduleCommand(new Protect()),
            new ReleaseElevator(),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new RotateToPosition(Constants.Climber.Rotator.kStartingAngle),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new ProxyScheduleCommand(new DriveToMidBar()),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new CloseHand(mHandA),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockUnlockedPositon),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Climb1Bar(mHandA, mHandB, Constants.Climber.kFirstCGPosition),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .withTimeout(0.5),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new CloseHand(mHandA),
                    new SetHandLockPosition(mHandA, Constants.Climber.Hand.kClawLockLockedPositon),
                    new OpenHand(mHandA)),
                new SetHandLockPosition(mHandB, Constants.Climber.Hand.kClawLockUnlockedPositon)),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Climb1Bar(mHandB, mHandA, Constants.Climber.kSecondCGPosition),
            // new WaitUntilCommand(RobotContainer::getClimbProceed),
            // new OpenHand(mRotationHandHolder),
                        new WaitUntilCommand(RobotContainer::getClimbProceed),
            new Rotate(Constants.Climber.Rotator.kClimbRotationSpeed)
                .withTimeout(1));
    }

    @Override
    public void initialize() {
        super.initialize();
        if(mClimber.getState() == Climber.State.kPrimed) {
            mClimber.advanceState();
        } else {
            cancel();
        }
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted) {
            mClimber.advanceState();
        }
    }
}
