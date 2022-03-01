package frc.robot.commands.Feeder;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class controlManual extends CommandBase {
    
    private Feeder m_Feeder = Feeder.getInstance();
    private Supplier<Double> m_beltPower;

    public controlManual(Supplier<Double> beltPower) {
        this.addRequirements(m_Feeder);
        this.setName("Manual Move Feeder");
        m_beltPower = beltPower;
    }

    @Override
    public void execute() {
        m_Feeder.moveBelt(m_beltPower.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}