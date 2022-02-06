package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;

public class search extends CommandBase{
    
    private static Turret m_turret = Turret.getInstance();

    public search() {
        this.addRequirements(m_turret);
        this.setName("Lost Limelight. Searching...");
    }

    @Override
    public void execute() {
        while(Limelight.getTv() == 0) {
            if(m_turret.getVelocity() > 0 && m_turret.getMeasurement() != Constants.Turret.leftLimit) {
                m_turret.rotateMotorVelocity(Constants.Turret.leftSpeedMaxAuto);
            } else if(m_turret.getVelocity() < 0 && m_turret.getMeasurement() != Constants.Turret.rightLimit) {
                m_turret.rotateMotorVelocity(Constants.Turret.rightSpeedMaxAuto);
            } else if(m_turret.getMeasurement() == Constants.Turret.leftLimit) {
                m_turret.rotateMotorVelocity(Constants.Turret.rightSpeedMaxAuto);
            } else if(m_turret.getMeasurement() == Constants.Turret.rightLimit) {
                m_turret.rotateMotorVelocity(Constants.Turret.leftSpeedMaxAuto);
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
