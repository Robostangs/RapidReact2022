package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Turret;

public class goHome extends CommandBase{
    private Turret m_Turret = Turret.getInstance();

    public goHome() {
        this.addRequirements(m_Turret);
        this.setName("Take Me Home, Country Road");
    }

    @Override
    public void execute() {
        m_Turret.setSpeed(Constants.Turret.rightSpeedMaxAuto);
        Timer.delay(3);
        m_Turret.setSpeed(0);
        m_Turret.resetEncoder();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
