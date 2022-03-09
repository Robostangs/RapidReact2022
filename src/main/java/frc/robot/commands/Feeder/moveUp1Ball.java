package frc.robot.commands.Feeder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class moveUp1Ball extends CommandBase {

    public final Feeder m_Feeder = Feeder.getInstance();
    private Timer m_timer;

    public moveUp1Ball() {
        this.addRequirements(m_Feeder);
        this.setName("Move Feeder");
        m_timer = new Timer();
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
        m_Feeder.moveBelt(Constants.Feeder.slowBeltSpeed);
    }

    @Override
    public void execute() {  
        if (m_timer.get() <= 1) {      
            if (m_Feeder.getShooterSensorLight()) {
                m_Feeder.moveBelt(0);
            }
        } else {
            m_Feeder.moveBelt(0);
            this.cancel();
        }
    }

    @Override
    public boolean isFinished() {
        return m_Feeder.getShooterSensorLight() || (m_timer.get() >= 3);
    }
    
}