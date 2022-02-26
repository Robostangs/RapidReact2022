package frc.robot.commands.Turret;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Limelight;

public class defaultLimelight extends CommandBase {
    
    private Turret m_Turret;
    private Supplier<Double> m_manip;

    public defaultLimelight(Supplier<Double> manip) {
        this.addRequirements(m_Turret);
        this.setName("Full Auto");
        m_manip = manip;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
