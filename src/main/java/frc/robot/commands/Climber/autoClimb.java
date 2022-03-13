package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.Drivetrain.DriveDistance;
import frc.robot.subsystems.Climber;

public class autoClimb extends SequentialCommandGroup{
    
    private Climber m_Climber;
    private frc.robot.subsystems.Drivetrain m_Drivetrain;

    public autoClimb() {
        this.setName("Auto Climb");
        
        m_Climber = Climber.getInstance();
        m_Drivetrain = frc.robot.subsystems.Drivetrain.getInstance();

        this.addRequirements(m_Climber);
        this.addRequirements(m_Drivetrain);

        this.addCommands(new DriveDistance(500));
        this.addCommands(new spinClimber(Constants.Climber.moveClimberSpeed));
        this.addCommands(new WaitCommand(2));
        
        if(m_Climber.getRightClawSensor()) {
            this.addCommands(new spinClimber(0));
            this.addCommands(new clawRight(Constants.Climber.clawMoveConstant));
            // notHeld = new DigitalInput(Constants.Climber.leftClawSensorID);
            // held = new DigitalInput(Constants.Climber.rightClawSensorID);
            this.addCommands(new WaitCommand(0.5));
        } else if(m_Climber.getLeftClawSensor()) {
            this.addCommands(new spinClimber(0));
            this.addCommands(new clawLeft(Constants.Climber.clawMoveConstant));
            // notHeld = new DigitalInput(Constants.Climber.rightClawSensorID);
            // held = new DigitalInput(Constants.Climber.leftClawSensorID);
            this.addCommands(new WaitCommand(0.5));
        }
        this.addCommands(new spinClimber(Constants.Climber.clawMoveConstant));
        // if(notHeld.get()) {
        //     this.addCommands(new spinClimber(0));
        //     // this.addCommands(new clawRight(position));
        // }

        this.addCommands();
    }


}
