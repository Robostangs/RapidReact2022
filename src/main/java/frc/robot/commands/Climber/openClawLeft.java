package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class openClawLeft extends CommandBase {
    private Climber m_climber = new Climber();

    public openClawLeft() {
        this.addRequirements(m_climber);
        this.setName("Opening Left Clw");
    }

    @Override
    public void execute() {
        m_climber.setLeftClawPosition(-Constants.Climber.clawMoveConstant);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
