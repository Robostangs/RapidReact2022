package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class closeClawRight extends CommandBase{
    private Climber m_climber = new Climber();

    public closeClawRight() {
        this.addRequirements(m_climber);
        this.setName("Closing Right Claw");
    }

    @Override
    public void execute() {
        m_climber.setRightClawLockPosition(Constants.Climber.clawMoveConstant);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
