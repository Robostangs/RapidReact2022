package frc.robot.commands.Intake;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.Feeder.moveUp1Ball;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;

public class Activate extends CommandBase {

    private Intake m_Intake = Intake.getInstance();
    private Supplier<Double> m_speed;

    public Activate(Supplier<Double> speed) {
        this.addRequirements(m_Intake);
        m_speed = speed; 
        this.setName("Auto Activate");
    }
    @Override
    public void execute() {      
            m_Intake.setSpeed(m_speed.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
