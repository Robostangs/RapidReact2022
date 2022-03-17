package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;

public class Protect extends InstantCommand{
    
    private Turret m_Turret;

    public Protect() {
        m_Turret = Turret.getInstance();
    }

    @Override
    public void execute() {
        m_Turret.setFilteredAngle(-90);
        Limelight.disableLEDs();
    }

}