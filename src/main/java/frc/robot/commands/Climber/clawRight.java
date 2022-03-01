package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class clawRight extends CommandBase{

    private double m_position;
    private Climber m_climber = new Climber();

    public clawRight(double position) {
        this.addRequirements(m_climber);
        this.setName("Right Claw");
        m_position = position;
    }

    @Override
    public void initialize() {
        m_climber.setLeftClawPosition(m_position);
    }
}
