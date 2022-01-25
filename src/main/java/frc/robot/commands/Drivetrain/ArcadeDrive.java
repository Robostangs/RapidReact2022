package frc.robot.commands.Drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
    
    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    private final Supplier<Double> mFuncForward;
    private final Supplier<Double> mFuncTurn;

    //We're inputting a function here so that we can put in the function of get value from joysticks
    //ADD DEADZONE!!!
    public ArcadeDrive(Supplier<Double> funcForward, Supplier<Double> funcTurn) {
        this.addRequirements(mDrivetrain);
        this.setName("Arcade Drive");
        mFuncForward = funcForward;
        mFuncTurn = funcTurn;
    }

    @Override
    public void execute() {
        mDrivetrain.drivePower(
            Utils.deadzone(mFuncForward.get() - mFuncTurn.get()),
            Utils.deadzone(mFuncForward.get() + mFuncTurn.get())
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
