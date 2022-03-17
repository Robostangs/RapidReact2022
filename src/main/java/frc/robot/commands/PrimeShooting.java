package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.shooter.SetDistanceShooterState;
import frc.robot.commands.turret.DefaultTurret;
import frc.robot.subsystems.Limelight;

public class PrimeShooting extends ParallelCommandGroup {
    private final DefaultTurret mDefaultTurret = new DefaultTurret();

    public PrimeShooting() {
        setName("Prime Shooting");
        addCommands(
            new SetDistanceShooterState(() -> (Math.abs(Limelight.getTx()) <= 5) && (Limelight.getTv() == 1) ? Limelight.getDistance() : -10));
    }

    @Override
    public void initialize() {
        mDefaultTurret.schedule();
        super.initialize();
    }

    @Override
    public void execute() {
        if(!mDefaultTurret.isScheduled()) {
            mDefaultTurret.schedule();
        }
        super.execute();
    }

    @Override
    public void end(boolean interrupted) {
        mDefaultTurret.cancel();
        super.end(interrupted);
    }
}
