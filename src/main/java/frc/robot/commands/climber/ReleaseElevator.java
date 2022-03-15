package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ReleaseElevator extends WaitCommand {
    private static final Climber mClimber = Climber.getInstance();

    public ReleaseElevator() {
        super(Constants.Climber.kElevatorReleaseWaitTime);
        setName("Release Elevator");
    }

    @Override
    public void initialize() {
        mClimber.setElevatorReleasePositions(
            Constants.Climber.kLeftElevatorReleasePosition,
            Constants.Climber.kRightElevatorReleasePosition);
        super.initialize();
    }
}
