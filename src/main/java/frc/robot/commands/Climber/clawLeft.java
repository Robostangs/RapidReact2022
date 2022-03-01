package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class clawLeft extends CommandBase {

    private double m_position;
    private Climber m_climber = new Climber();

    public clawLeft(double position) {
        this.addRequirements(m_climber);
        this.setName("Left Claw");
        m_position = position;
    }

    @Override
    public void initialize() {
        m_climber.setLeftClawPosition(m_position);
    }
}
