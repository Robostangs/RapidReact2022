package frc.robot.commands.Drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
    
    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    //We're inputting a function here so that we can put in the function of get value from joysticks
    //ADD DEADZONE!!!
    public ArcadeDrive(Supplier <Double> funcForward, Supplier <Double> funcTurn) {
        mDrivetrain.drivePower(Utils.deadzone(funcForward.get() - funcTurn.get()), Utils.deadzone(funcForward.get() + funcTurn.get()));
    }

}
