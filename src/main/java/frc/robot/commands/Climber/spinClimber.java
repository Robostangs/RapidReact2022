package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class spinClimber extends CommandBase{
    private Climber m_climber = new Climber();
    private final double m_position;


    public spinClimber(double position) {
        this.addRequirements(m_climber);
        this.setName("Spinning Climber");
        m_position = position;
    }

    @Override
    public void execute() {
        m_climber.setRotationMotorPosition(m_position);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
