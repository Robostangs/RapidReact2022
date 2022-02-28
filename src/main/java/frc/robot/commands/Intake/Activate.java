package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class Activate extends InstantCommand {

    private Intake m_Intake = Intake.getInstance();
    private double m_speed;

    public Activate(double speed) {
        this.addRequirements(m_Intake);
        m_speed = speed; 
        this.setName("Auto Activate");
    }

    @Override
    public void initialize() {
        m_Intake.setSpeed(m_speed);
    }
}
