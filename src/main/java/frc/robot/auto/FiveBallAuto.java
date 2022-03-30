package frc.robot.auto;

import java.io.IOException;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.PrimeShooting;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.commands.elevator.RunElevator;
import frc.robot.commands.intake.Active;
import frc.robot.commands.shooter.SetDistanceShooterState;
import frc.robot.subsystems.Limelight;

public class FiveBallAuto extends SequentialCommandGroup {

    public FiveBallAuto() {
        setName("Five Ball Auto - Not Rude");
        try {        
            new ParallelCommandGroup(
                new FollowPath("output/5BallPt1.wpilib.json"), 
                new Active(Constants.IntakeConstants.kDefaultSpeed)
            );
            new SetDistanceShooterState(() -> Limelight.getDistance());
            new ParallelCommandGroup(
                new FollowPath("output/5BallPt2.wpilib.json"), 
                new Active(Constants.IntakeConstants.kDefaultSpeed)
            );
            new ParallelCommandGroup(
                new FollowPath("output/5BallPt3.wpilib.json"), 
                new Active(Constants.IntakeConstants.kDefaultSpeed)
            );
            new FollowPath("output/5BallPt4.wpilib.json");
            new SetDistanceShooterState(() -> Limelight.getDistance());
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new WaitCommand(1),
                    new RunElevator().withTimeout(0.3),
                    new WaitCommand(1),
                    new RunElevator().withTimeout(0.3),
                    new WaitCommand(1)),
                new PrimeShooting());
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

}
    