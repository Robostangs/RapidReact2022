package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class home extends CommandBase{
    
    private Shooter m_Shooter;

    public home() {
        m_Shooter = Shooter.getInstance();
        this.addRequirements(m_Shooter);
        this.setName("Home Hood");
    }

    @Override
    public void execute() {
        m_Shooter.setAnglePower(Constants.Shooter.homeSpeed);
    }

    @Override
    public boolean isFinished() {
        return m_Shooter.getHoodLimitSwitch();
    }

}
