package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Feeder.moveUp1Ball;
import frc.robot.commands.Intake.Activate;
import frc.robot.commands.Shooter.autoShoot;
import frc.robot.subsystems.*;

public class simpleAuto extends SequentialCommandGroup {

    private Drivetrain m_Drivetrain;
    private Intake m_Intake;
    private Feeder m_Feeder;
    private Shooter m_Shooter;

    public simpleAuto() {
        this.setName("auto");
        m_Drivetrain = Drivetrain.getInstance();
        m_Intake = Intake.getInstance();
        m_Feeder = Feeder.getInstance();
        m_Shooter = Shooter.getInstance();

        this.addRequirements(m_Drivetrain);
        this.addRequirements(m_Intake);
        this.addRequirements(m_Feeder);
        this.addRequirements(m_Shooter);

        this.addCommands(new Activate());
        //TODO: MAKE BETTER
        this.addCommands(new InstantCommand(() -> {
            frc.robot.subsystems.Drivetrain.getInstance().drivePower(
                0.6, -0.6);
        }));
        this.addCommands(new WaitCommand(1.5));
        this.addCommands(new moveUp1Ball());
        this.addCommands(new WaitCommand(1.5));
        // this.addCommands(new autoShoot(-0.55, 0.4));
    }
}