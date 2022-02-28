package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Shooter.autoShoot;
import frc.robot.subsystems.*;

public class simpleAuto extends SequentialCommandGroup {

    // private SequentialCommandGroup name;

    public simpleAuto() {
        this.setName("auto");
        this.addRequirements(frc.robot.subsystems.Drivetrain.getInstance());
        this.addRequirements(Intake.getInstance());
        this.addRequirements(Feeder.getInstance());
        this.addRequirements(Shooter.getInstance());

        super.addCommands(new InstantCommand(() -> {
            Intake.getInstance().setSpeed(0.5);
        }, Intake.getInstance()));
        super.addCommands(new InstantCommand(() -> {
            frc.robot.subsystems.Drivetrain.getInstance().drivePower(
                0.6, -0.6);
        }, frc.robot.subsystems.Drivetrain.getInstance()));
        super.addCommands(new WaitCommand(1.5));
        super.addCommands(new InstantCommand(() ->{
            Feeder.getInstance().moveBelt(-1);
        }, Feeder.getInstance()));
        super.addCommands(new WaitCommand(1.5));
        super.addCommands(new autoShoot(-0.55, 0.4));
    }
}