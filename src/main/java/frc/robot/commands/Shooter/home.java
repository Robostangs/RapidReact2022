package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class home extends CommandBase{
    
    private Shooter m_Shooter = Shooter.getInstance();
    private Timer m_timer;

    public home() {
        this.addRequirements(m_Shooter);
        this.setName("Home Hood");
        m_timer = new Timer();
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_Shooter.setSoftLimitEnable(false);
        m_timer.start();
        m_Shooter.setClearPosition(true);
    }

    @Override
    public void execute() {
        if(m_timer.get() <= 3) {
            System.out.println("I ran " + Double.toString(Timer.getFPGATimestamp()));
            m_Shooter.setAnglePower(Constants.Shooter.homeSpeed);
        } else {
            System.out.println("I time exceededs " + Double.toString(Timer.getFPGATimestamp()));
            m_Shooter.setAnglePower(0);
            m_Shooter.setMaxSpeed(0.25);
            m_Shooter.resetHoodEncoder();
            this.cancel();
            
        }
    }

    @Override
    public boolean isFinished() {
        return m_Shooter.getForwardLimit() || (m_timer.get() >= 5);
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted) {
            m_Shooter.setAnglePower(0);
            m_Shooter.setMaxSpeed(1);
            m_Shooter.setHomed(true);
        }
        m_Shooter.setSoftLimitEnable(true);
        m_Shooter.setClearPosition(false);
        m_Shooter.setHomed(true);
    }
}
