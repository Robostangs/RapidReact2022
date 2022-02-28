package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class setElevatorPower extends InstantCommand{
    
    private Shooter m_Shooter;
    private double m_power;

    public setElevatorPower(double power) {
        m_Shooter = Shooter.getInstance();
        m_power = power;
    }

    @Override
    public void initialize() {
        m_Shooter.setElevatorPower(m_power);
    }
}
