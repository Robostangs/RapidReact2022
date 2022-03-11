package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Turret;

public class Protect extends InstantCommand {

    private final Turret mTurret = Turret.getInstance();

    @Override
    public void execute() {
        mTurret.setFilteredAngle(-90);
    }
}
