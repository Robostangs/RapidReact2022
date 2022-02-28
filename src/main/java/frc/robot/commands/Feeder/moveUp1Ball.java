package frc.robot.commands.Feeder;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class moveUp1Ball extends InstantCommand {

    public final Feeder m_Feeder = Feeder.getInstance();

    public moveUp1Ball() {
        this.addRequirements(m_Feeder);
        this.setName("Move Feeder");
    }

    @Override
    public void initialize() {
        m_Feeder.moveBelt(Constants.Feeder.slowBeltSpeed);
    }

    @Override
    public void execute() {        
        if (m_Feeder.getShooterSensorLight()) {
            m_Feeder.moveBelt(0);
        }
    }
}