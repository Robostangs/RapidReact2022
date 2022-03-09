package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class deactivate extends InstantCommand {

    private Intake m_Intake = Intake.getInstance();
    // private double m_speed;

    public deactivate() {
        this.addRequirements(m_Intake);
        // m_speed = speed;
        this.setName("Auto deactivate");
    }

    @Override
    public void initialize() {
        m_Intake.setSpeed(0);
    }
}
