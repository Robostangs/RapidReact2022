package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class spinClimber extends CommandBase{
    private Climber m_climber = new Climber();
    private final double m_speed;


    public spinClimber(double speed) {
        this.addRequirements(m_climber);
        this.setName("Spinning Climber");
        m_speed = speed;
    }

    @Override
    public void initialize() {
        m_climber.setRotationMotorSpeed(m_speed);
    }
}
