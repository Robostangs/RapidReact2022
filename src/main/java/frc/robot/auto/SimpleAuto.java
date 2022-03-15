package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.feeder.MoveUp1Ball;
import frc.robot.commands.intake.Active;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class SimpleAuto extends SequentialCommandGroup {

    private final Drivetrain mDrivetrain = Drivetrain.getInstance();
    private final Intake mIntake = Intake.getInstance();
    private final Feeder mFeeder = Feeder.getInstance();
    private final Shooter mShooter = Shooter.getInstance();

    public SimpleAuto() {
        addRequirements(mDrivetrain, mIntake, mFeeder, mShooter);
        setName("Simple Auto");
        // TODO: MAKE BETTER
        addCommands(
            new Active(0.5),
            new InstantCommand(() -> {
                frc.robot.subsystems.Drivetrain.getInstance().drivePower(0.6, -0.6);}),
            new WaitCommand(1.5),
            new MoveUp1Ball(),
            new WaitCommand(1.5));
            // new autoShoot(-0.55, 0.4));
    }
}
