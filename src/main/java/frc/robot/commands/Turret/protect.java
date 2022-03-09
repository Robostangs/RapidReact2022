package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Turret;

public class protect extends InstantCommand{
    
    private Turret m_Turret;

    public protect() {
        m_Turret = Turret.getInstance();
    }

    @Override
    public void execute() {
        m_Turret.setFilteredAngle(-90);
    }

}
