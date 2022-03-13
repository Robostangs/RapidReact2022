package frc.robot.commands.shooter;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends SequentialCommandGroup {

    private final Shooter mShooter = Shooter.getInstance();

    public AutoShoot(Supplier<Double> distance) {
        addRequirements(mShooter);
        setName("Shoot");

        // for(int i = 0; i < distance.length; i++) {
        // int j = i + 1;
        // if(distance[i] <= limelightDistance) {
        // m_leftShooterPower = leftShooterPower[i];
        // m_rightShooterPower = rightShooterPower[i];
        // m_angle = angle[i];
        // }
        // }

        addCommands(); // TODO: FIX
    }
}
