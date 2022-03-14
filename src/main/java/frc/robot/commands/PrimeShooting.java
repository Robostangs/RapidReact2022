package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Utils;
import frc.robot.commands.shooter.SetDistanceShooterState;
import frc.robot.commands.turret.DefaultLimelight;
import frc.robot.subsystems.Limelight;

public class PrimeShooting extends ParallelCommandGroup {
    public PrimeShooting() {
        setName("Prime Shooting");
        addCommands(
            new SetDistanceShooterState(() -> Utils.dist(Limelight.getTy())),
            new DefaultLimelight());
    }
}
