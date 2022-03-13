package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Feeder.moveUp1Ball;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class autoShoot extends SequentialCommandGroup{
    
    private Shooter m_Shooter;
    private Feeder m_Feeder;
    private double m_leftShooterPower, m_rightShooterPower;

    public autoShoot(double leftShooterPower, double rightShooterPower) {
        m_Shooter = Shooter.getInstance();
        m_Feeder = Feeder.getInstance();

        this.addRequirements(m_Shooter);
        this.addRequirements(m_Feeder);
        this.setName("Shoot");

        m_leftShooterPower = leftShooterPower;
        m_rightShooterPower = rightShooterPower;

        this.addCommands(new setShooterPower(m_leftShooterPower, m_rightShooterPower));
        this.addCommands(new WaitCommand(1.5));
        this.addCommands(new setElevatorPower(-1));
        this.addCommands(new WaitCommand(0.5));
        this.addCommands(new setElevatorPower(0));
        this.addCommands(new moveUp1Ball());
        this.addCommands(new WaitCommand(0.5));
        this.addCommands(new setElevatorPower(-1));
        this.addCommands(new WaitCommand(0.5));
        this.addCommands(new setShooterPower(0, 0));
        this.addCommands(new setElevatorPower(0));
    }
}
