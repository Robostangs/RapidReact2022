package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class Deactivate extends InstantCommand {

    private final Intake mIntake = Intake.getInstance();
    // private double m_speed;

    public Deactivate() {
        addRequirements(mIntake);
        setName("Auto deactivate");
        // m_speed = speed;
    }

    @Override
    public void initialize() {
        mIntake.setSpeed(0);
    }
}
