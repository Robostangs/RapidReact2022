package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Limelight;

public class DefaultTurret extends SequentialCommandGroup{

    public DefaultTurret() {
        this.setName("default Turret");
        this.addCommands(
            new Search().withInterrupt(() -> Limelight.getTv() == 1),
            new FollowLimelight().withInterrupt(() -> Limelight.getTv() == 0));
    }

    @Override
    public void initialize() {
        Limelight.enableLEDs();
        super.initialize();
    }

    @Override
    public void end(boolean interrupted) {
        Limelight.disableLEDs();
        super.end(interrupted);
    }
}