package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.Feeder.moveUp1Ball;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;

public class Activate extends CommandBase {

    private Intake m_Intake = Intake.getInstance();
    private Feeder m_Feeder = Feeder.getInstance();
    private Boolean m_isPressed = false;

    public Activate(Boolean isPressed) {
        this.addRequirements(m_Intake);
        this.addRequirements(m_Feeder);
        this.setName("Auto Activate");
    }

    @Override
    public void execute() {
        if(m_isPressed) {
            m_Intake.setSpeed(Constants.IntakeConstants.kIntakeSpeed);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
