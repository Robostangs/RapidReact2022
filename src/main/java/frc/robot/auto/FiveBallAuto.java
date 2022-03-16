package frc.robot.auto;

import java.io.IOException;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.commands.intake.Active;
import frc.robot.commands.shooter.SetDistanceShooterState;
import frc.robot.subsystems.Limelight;

public class FiveBallAuto extends SequentialCommandGroup {

    public FiveBallAuto() {
        this.setName("Five Ball Auto - Not Rude");
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
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

}
    