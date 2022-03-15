package frc.robot.auto;

import java.io.IOException;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.commands.intake.Active;
import frc.robot.commands.shooter.AutoShoot;
import frc.robot.subsystems.Limelight;

public class FiveBallAuto extends SequentialCommandGroup {

    public FiveBallAuto() {
        this.setName("Five Ball Auto - Not Rude");
        try {        
            new ParallelCommandGroup(
                new FollowPath("src/main/deploy/output/5BallPt1.wpilib.json"), 
                new Active(Constants.IntakeConstants.kIntakeSpeed)
            );
            //TODO: MAKE SURE THIS IS FIXED
            new AutoShoot(Limelight.getDistance());
            new ParallelCommandGroup(
                new FollowPath("src/main/deploy/output/5BallPt2.wpilib.json"), 
                new Active(Constants.IntakeConstants.kIntakeSpeed)
            );
            new ParallelCommandGroup(
                new FollowPath("src/main/deploy/output/5BallPt3.wpilib.json"), 
                new Active(Constants.IntakeConstants.kIntakeSpeed)
            );
            new FollowPath("src/main/deploy/output/5BallPt4.wpilib.json");
            new AutoShoot(Limelight.getDistance()); 
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

}
