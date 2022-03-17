package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Turret;

public class GoHome extends CommandBase{
    private Turret m_Turret = Turret.getInstance();
    private Timer m_timer;

    public GoHome() {
        this.addRequirements(m_Turret);
        this.setName("Take Me Home, Country Road");
        m_timer = new Timer();
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_Turret.setSoftLimitEnable(false);
        m_timer.start();
        m_Turret.setClearPosition(true);
    }

    @Override
    public void execute() {
        if(m_timer.get() <= 3) {
            System.out.println("I ran " + Double.toString(Timer.getFPGATimestamp()));
            m_Turret.setSpeed(Constants.Turret.rotationMotorSpeed);
        } else {
            System.out.println("I time exceededs " + Double.toString(Timer.getFPGATimestamp()));

            m_Turret.setSpeed(0);
            m_Turret.setMaxSpeed(0.2);
            this.cancel();
        }
    }

    @Override
    public boolean isFinished() {
        return m_Turret.getReverseLimit();
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted) {
            m_Turret.setSoftLimitEnable(true);
            m_Turret.setMaxSpeed(1);
            m_Turret.setHomed(true);
        
        }
        m_Turret.setClearPosition(false);

    }
}