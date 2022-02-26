package frc.robot.commands.Shooter;

import java.nio.file.WatchEvent;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SynchronousInterrupt.WaitResult;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.*;

public class betterShoot extends SequentialCommandGroup {

    public betterShoot(double allignmentPower, double leftShooterPower, double rightShooterPower) {
        super(
            new InstantCommand(() -> {        
                Shooter.getInstance().setRightShooterPower(rightShooterPower);
                Shooter.getInstance().setLeftShooterPower(leftShooterPower);
            }),
            new WaitCommand(1.5),
            new InstantCommand(() -> {Shooter.getInstance().setElevatorPower(-1);}),
            new WaitCommand(1),
            new InstantCommand(() -> {
                Shooter.getInstance().setRightShooterPower(0);
                Shooter.getInstance().setLeftShooterPower(0);
                // mShooter.setAnglePower(0);
                Shooter.getInstance().setElevatorPower(0);
            })
        );
        this.setName("Better Shoot");
    }
}
