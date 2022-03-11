package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class Activate extends InstantCommand {

    private final Intake mIntake = Intake.getInstance();
    // private double m_speed;

    public Activate() {
        addRequirements(mIntake);
        setName("Auto Activate");
        // m_speed = speed;
    }

    @Override
    public void initialize() {
        mIntake.setSpeed(0.5);
    }
}
