package frc.robot.commands.drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.Drivetrain;

public class CustomArcade extends CommandBase {

    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    private final Supplier<Double> mForwardSupplier;
    private final Supplier<Double> mTurnSupplier;
    SlewRateLimiter limiter = new SlewRateLimiter(Constants.Drivetrain.kSlewRate);

    // We're inputting a function here so that we can put in the function of get value from joysticks
    // ADD DEADZONE!!!
    public CustomArcade(Supplier<Double> funcForward, Supplier<Double> funcTurn) {
        addRequirements(mDrivetrain);
        setName("Arcade Drive");
        mForwardSupplier = funcTurn;
        mTurnSupplier = funcForward;
        // mDrivetrain.resetRotation();
    }

    @Override
    public void execute() {
       double forward = customDeadzone(limiter.calculate(mForwardSupplier.get()));
       double turn = -customDeadzone(mTurnSupplier.get());
       mDrivetrain.drivePower(forward - turn, forward + turn);

    }

    public double customDeadzone(double input) {
        if(Math.abs(input) >= 0.1 && Math.abs(input) < 0.5) {
            return Math.signum(input) * ((0.25) * Math.pow((12.5), Math.abs(input)) - 0.3218); 
        } else if(Math.abs(input) >= 0.5 && Math.abs(input) <= 1) {
            return Math.signum(input) * ((0.876) * (Math.abs(input - 0.5) + 0.562));
        }
        return 0;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
