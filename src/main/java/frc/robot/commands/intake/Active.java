package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class Active extends CommandBase{
    
    private final Intake mIntake = Intake.getInstance();
    private double m_speed;

    public Active(double speed) {
        addRequirements(mIntake);
        setName("Intaking");
        m_speed = speed;
    }

    public Active() {
        this(Constants.IntakeConstants.kDefaultSpeed);
    }

    @Override
    public void initialize() {
        mIntake.setSpeed(m_speed);
    }

    @Override
    public void end(boolean interrupted) {
        mIntake.setSpeed(0);
    }
}
