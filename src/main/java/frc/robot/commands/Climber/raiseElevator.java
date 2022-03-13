package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class raiseElevator extends CommandBase{
    
    private Climber m_climber = new Climber();

    public raiseElevator() {
        this.addRequirements(m_climber);
        this.setName("Raise Elevator");
    }

    @Override
    public void initialize() {
        m_climber.setElevatorPosition(Constants.Climber.elevatorReleaseConstant);
    }
}
