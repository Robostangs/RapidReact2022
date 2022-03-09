package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class autoShoot extends SequentialCommandGroup{
    
    private Shooter m_Shooter;
    private double[] distance = {0, 56, 61, 68, 80,
        84,
        87,
        92,
        104,
        108,
        116,
        128,
        142,
        177,
        165 }, leftShooterPower = {500, 2000,
                2000,
                2300,
                2350,
                2400,
                2400,
                2459,
                2500,
                2586,
                2600,
                2990,
                3163,		
                3000,
                2900
        }, rightShooterPower = {500,2000, 2100, 2000, 2000, 2100, 2200, 2175, 2100,2300, 2250, 2600, 2750,2761,2700
        }, angle = {10,0,0,0,0,0,0,0,0,0,0,0,0,10,10};
    private double m_leftShooterPower, m_rightShooterPower, m_angle;

    // public autoShoot(double limelightDistance) {
    public autoShoot(double leftShooterPower, double rightShooterPower, double angle) {
        m_Shooter = Shooter.getInstance();

        this.addRequirements(m_Shooter);
        this.setName("Shoot");

        // for(int i = 0; i < distance.length; i++) {
		// 	int j = i + 1;
		// 	if(distance[i] <= limelightDistance) {
		// 		m_leftShooterPower = leftShooterPower[i];
        //         m_rightShooterPower = rightShooterPower[i];
        //         m_angle = angle[i];
		// 	}			
		// }

        System.out.println(m_leftShooterPower);
        System.out.println(m_rightShooterPower);
        

        this.addCommands(new setShooterPower(-m_leftShooterPower, m_rightShooterPower));
        this.addCommands(new angle(m_angle));
        // this.addCommands(new setShooterPower(-leftShooterPower, rightShooterPower));
        // //this.addCommands(new angle(angle));
        this.addCommands(new WaitCommand(1));
        this.addCommands(new setElevatorPower(-1));
        // this.addCommands(new WaitCommand(0.1));
        // this.addCommands(new setElevatorPower(0));
        // this.addCommands(new WaitCommand(0.1));
        // this.addCommands(new setElevatorPower(-1));
        // this.addCommands(new WaitCommand(0.1));
        this.addCommands(new setShooterPower(0, 0));
        this.addCommands(new setElevatorPower(0));
        this.addCommands(new angle(0));

    }
}
