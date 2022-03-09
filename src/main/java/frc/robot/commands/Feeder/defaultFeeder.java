package frc.robot.commands.Feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class defaultFeeder extends CommandBase {

    public final Feeder m_Feeder = Feeder.getInstance();

    public defaultFeeder() {
        this.addRequirements(m_Feeder);
        this.setName("Move Feeder");
    }

    @Override
    public void execute() {  
        if(m_Feeder.getIntakeSensorLight() && !m_Feeder.getShooterSensorLight()) {
            m_Feeder.moveBelt(Constants.Feeder.slowBeltSpeed);
        } else {
            m_Feeder.moveBelt(0);
        }
    }    
}