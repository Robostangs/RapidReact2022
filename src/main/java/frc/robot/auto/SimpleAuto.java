package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.PrimeShooting;
import frc.robot.commands.elevator.RunElevator;
import frc.robot.commands.feeder.MoveUp1Ball;
import frc.robot.commands.intake.Active;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class SimpleAuto extends SequentialCommandGroup {

    private final Drivetrain mDrivetrain = Drivetrain.getInstance();
    private final Intake mIntake = Intake.getInstance();
    private final Shooter mShooter = Shooter.getInstance();

    public SimpleAuto() {
        addRequirements(mDrivetrain, mIntake, mShooter);
        setName("Simple Auto");
        // TODO: MAKE BETTER
        addCommands(
            new InstantCommand(() -> 
                mDrivetrain.drivePower(0.2, -0.2)),
            new Active(0.5).withTimeout(2.5),
            new InstantCommand(() -> mDrivetrain.drivePower(-0.2, 0.2)),
            new WaitCommand(1),
            new InstantCommand(() -> mDrivetrain.drivePower(0, 0)),

            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new WaitCommand(1),
                    new RunElevator().withTimeout(0.3),
                    new WaitCommand(1),
                    new RunElevator().withTimeout(0.3),
                    new WaitCommand(1)),
                new PrimeShooting())
        );

            // new autoShoot(-0.55, 0.4));
    }
}
