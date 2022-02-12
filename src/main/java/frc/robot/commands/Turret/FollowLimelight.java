package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;

public class FollowLimelight extends CommandBase{
    private final Turret m_Turret = Turret.getInstance();
    
    public FollowLimelight() {
        this.addRequirements(m_Turret);
        this.setName("Follow Limelight");
    }

    @Override
    public void execute() {
        super.execute();
        double currentTX = Limelight.getTx();
        m_Turret.setSetpoint(currentTX);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
