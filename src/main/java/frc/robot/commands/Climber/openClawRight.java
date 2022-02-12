package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class openClawRight extends CommandBase{
    private Climber m_climber = new Climber();

    public openClawRight() {
        this.addRequirements(m_climber);
        this.setName("Opening Claw Right");
    }

    @Override
    public void execute() {
        m_climber.setRightClawPosition(Constants.Climber.clawMoveConstant);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
