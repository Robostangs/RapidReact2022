package frc.robot.commands.Turret;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Limelight;

public class defaultLimelight extends CommandBase {
    
    private Turret m_Turret;
    private Supplier<Double> m_manip;
    private double lastTx;

    public defaultLimelight(Supplier<Double> manip) {
        m_Turret = Turret.getInstance();
        this.addRequirements(m_Turret);
        this.setName("Full Auto");
        m_manip = manip;
        lastTx = 1;
    }

    @Override
    public void execute() {
        // double outreq;
        // if (Limelight.getTv() != 0 && (Math.abs(m_manip.get()) < 0.1)) {
        //     outreq = 0.05 * Limelight.getTx();
        // } else {
        //     outreq = (m_manip.get());
        // }

        // if (outreq > 0.5){
        //     outreq = 0.5;
        // }
        // else if (outreq < -0.5){
        //     outreq = -0.5;
        // }        
        // m_Turret.setSpeed(outreq);
        /*
            m_Turret.setAngle(Limelight.getTV());
        */
        if(Limelight.getTv() == 1) {
            lastTx = Limelight.getTx();
            m_Turret.limelightSetAngle(Limelight.getTx());
        } else {
            double m_position = 200 * Math.signum(lastTx);
            m_Turret.setAngle(m_position);
            if((m_Turret.getTurrentPosition() >= Constants.Turret.rotationMotorMax - 4000.0) && (Math.signum(m_position) > 0)) {
                lastTx = -lastTx;
            } else if((m_Turret.getTurrentPosition() <= 4000.0) && (Math.signum(m_position) < 0)){
                lastTx = -lastTx;  
            }
            SmartDashboard.putNumber("m_position", m_position);
            SmartDashboard.putNumber("m_tx", lastTx);
        }

    }
}
