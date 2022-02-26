package frc.robot.commands.Turret;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class reset extends CommandBase{
 
    private Turret m_Turret = Turret.getInstance();
    private Supplier<Boolean> m_activate;

    public reset(Supplier<Boolean> activate) {
        this.addRequirements(m_Turret);
        this.setName("Reset");
        m_activate = activate;
    }

    @Override
    public void execute() {
        if(m_activate.get()) {
            m_Turret.resetEncoder();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
   
}