package frc.robot.commands.Turret;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Limelight;

public class defaultLimelight extends CommandBase {
    
    private Turret m_Turret;
    private Supplier<Double> m_manip;

    public defaultLimelight(Supplier<Double> manip) {
        m_Turret = Turret.getInstance();
        this.addRequirements(m_Turret);
        this.setName("Full Auto");
        m_manip = manip;
    }

    @Override
    public void execute() {
        double outreq;
        if (Limelight.getTv() != 0 && (Math.abs(m_manip.get()) < 0.1)) {
            outreq = 0.2 * Limelight.getTx();
        } else {
            outreq = (m_manip.get());
        }

        if (outreq > 0.5){
            outreq = 0.5;
        }
        else if (outreq < -0.5){
            outreq = -0.5;
        }

        m_Turret.setSpeed(outreq);
    }
}
