package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Utils;
import frc.robot.commands.shooter.SetDistanceShooterState;
import frc.robot.commands.turret.DefaultTurret;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class PrimeShooting extends ParallelCommandGroup {
    private final DefaultTurret mDefaultTurret = new DefaultTurret();

    public PrimeShooting() {
        setName("Prime Shooting");
        addCommands(
            new SetDistanceShooterState(() -> Utils.dist(Limelight.getTy()))
        );
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
        // TODO Auto-generated method stub
        super.end(interrupted);
        Shooter.getInstance().setState(new Shooter.State(0));
    }
}
