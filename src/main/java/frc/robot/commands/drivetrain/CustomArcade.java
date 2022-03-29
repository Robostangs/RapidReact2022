package frc.robot.commands.drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.Drivetrain;

public class CustomArcade extends CommandBase {

    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    private final Supplier<Double> mTurnSupplier;
    private final Supplier<Double> mForwardSupplier;
    SlewRateLimiter limiter = new SlewRateLimiter(Constants.Drivetrain.kSlewRate);

    // We're inputting a function here so that we can put in the function of get value from joysticks
    // ADD DEADZONE!!!
    public CustomArcade(Supplier<Double> funcForward, Supplier<Double> funcTurn) {
        addRequirements(mDrivetrain);
        setName("Custom Arcade Drive");
        mForwardSupplier = funcForward;
        mTurnSupplier = funcTurn;
        // mDrivetrain.resetRotation();
    }

    @Override
    public void execute() {
       double forward = limiter.calculate(customDeadzone(mForwardSupplier.get()));
       double turn = -0.8 * Utils.deadzone(mTurnSupplier.get(), 2);
       mDrivetrain.drivePower((forward - turn) * Constants.Drivetrain.kPowerOffsetMultiplier, -(forward + turn));
    }

    public static double customDeadzone(double input) {
        if(Math.abs(input) >= 0.1 && Math.abs(input) < 0.5) {
            return Math.signum(input) * ((0.25) * Math.pow((12.5), Math.abs(input)) - 0.3218); 
        } else if(Math.abs(input) >= 0.5 && Math.abs(input) <= 1) {
            return Math.signum(input) * ((0.876) * (Math.abs(input) - 0.5) + (0.562));
        }
        return 0;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
