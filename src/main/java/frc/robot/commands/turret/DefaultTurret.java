package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Limelight;

public class DefaultTurret extends SequentialCommandGroup{

    public DefaultTurret() {
        this.setName("default Turret");
        this.addCommands(new Search().withInterrupt(() -> {return Limelight.getTv() == 1;}));
        this.addCommands(new FollowLimelight());
    }

}
