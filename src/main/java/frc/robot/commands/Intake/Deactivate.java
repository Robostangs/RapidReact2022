package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class Deactivate extends InstantCommand {
    
    public Deactivate() {
        super(() -> {Intake.getInstance().setSpeed(0);}, Intake.getInstance());
    }
}
