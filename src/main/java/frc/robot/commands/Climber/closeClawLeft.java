package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class closeClawLeft extends CommandBase{

    private Climber m_climber = new Climber();

    public closeClawLeft() {
        this.addRequirements(m_climber);
        this.setName("Closing Left Class");
    }

    @Override
    public void execute() {
        m_climber.setLeftClawPosition(Constants.Climber.);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
