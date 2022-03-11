package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends SequentialCommandGroup {

    public static final double[]
        distance = {0, 56, 61, 68, 80, 84, 87, 92, 104, 108, 116, 128, 142, 177, 165},
        bottomShooterPower = {500, 2000, 2000, 2300, 2350, 2400, 2400, 2459, 2500, 2586, 2600, 2990, 3163, 3000, 2900},
        topShooterPower = {500, 2000, 2100, 2000, 2000, 2100, 2200, 2175, 2100, 2300, 2250, 2600, 2750, 2761, 2700},
        angle = {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10};

    private final Shooter mShooter = Shooter.getInstance();
    private final double mLeftShooterPower, mRightShooterPower, mAngle;

    // public autoShoot(double limelightDistance) {
    public AutoShoot(double bottomShooterPower, double topShooterPower, double angle) {
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

        System.out.println(mLeftShooterPower);
        System.out.println(mRightShooterPower);

        addCommands(
            new SetShooterPower(-mLeftShooterPower, mRightShooterPower),
            new Angle(mAngle),
            // new setShooterPower(-leftShooterPower, rightShooterPower),
            // new angle(angle),
            new WaitCommand(1),
            new SetElevatorPower(-1),
            // new WaitCommand(0.1),
            // new setElevatorPower(0),
            // new WaitCommand(0.1),
            // new setElevatorPower(-1),
            // new WaitCommand(0.1),
            new SetShooterPower(0, 0),
            new SetElevatorPower(0),
            new Angle(0));
    }
}
