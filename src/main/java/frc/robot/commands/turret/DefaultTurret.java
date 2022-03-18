package frc.robot.commands.turret;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;

public class DefaultTurret extends SequentialCommandGroup {
    private final Debouncer limelightTvDebouncer = new Debouncer(Constants.Turret.kDebounceTime);

    public DefaultTurret() {
        this.setName("default Turret");
        this.addCommands(
            new Search()
                .withInterrupt(() -> limelightTvDebouncer.calculate(Limelight.getTv() == 1))
                .andThen(new PrintCommand("Found Target")),
            new FollowLimelight()
                .withInterrupt(() -> limelightTvDebouncer.calculate(Limelight.getTv() == 1))
                .andThen(new PrintCommand("Lost Target")));
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
