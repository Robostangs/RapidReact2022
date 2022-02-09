package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class Activate extends InstantCommand {

    public Activate(double speed) {
        super(() -> {Intake.getInstance().setSpeed(speed);}, Intake.getInstance());
    }

    public Activate() {
        this(Constants.IntakeConstants.kIntakeSpeed);
    }
}
