package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.Climber;

public class ReleaseElevator extends CommandBase {
    private final Climber mClimber = Climber.getInstance();

    public ReleaseElevator() {
        addRequirements(mClimber);
        setName("Release Elevator");
    }

    @Override
    public void initialize() {
        mClimber.setElevatorReleasePosition(Constants.Climber.kElevatorReleasePosition);
    }

    @Override
    public boolean isFinished() {
        return Utils.roughlyEqual(mClimber.getElevatorReleasePosition(), Constants.Climber.kElevatorReleasePosition);
    }
}
