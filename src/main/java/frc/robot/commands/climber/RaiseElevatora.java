package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class RaiseElevator extends CommandBase {

    private final Climber mClimber = Climber.getInstance();

    public RaiseElevator() {
        addRequirements(mClimber);
        setName("Raise Elevator");
    }

    @Override
    public void initialize() {
        mClimber.setElevatorPosition(Constants.Climber.kElevatorReleaseConstant);
    }
}
