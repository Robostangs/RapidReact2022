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

public class CurvatureDrive extends CommandBase {

    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    private final Supplier<Double> mForwardSupplier;
    private final Supplier<Double> mTurnSupplier;
    SlewRateLimiter limiter = new SlewRateLimiter(Constants.Drivetrain.kSlewRate);

    //TODO: REVERSE TURNING WHEN REVERSING, 
    public CurvatureDrive(Supplier<Double> funcForward, Supplier<Double> funcTurn) {
        addRequirements(mDrivetrain);
        setName("Curvature Drive");
        mForwardSupplier = funcForward;
        mTurnSupplier = funcTurn;
        // mDrivetrain.resetRotation();
    }

    @Override
    public void execute() {
        WheelSpeeds speeds = DifferentialDrive.curvatureDriveIK(
            mForwardSupplier.get(),
            mTurnSupplier.get(),
            // mForwardSupplier.get() < 0 ? -mTurnSupplier.get() : mTurnSupplier.get(),
            !(Math.abs(mTurnSupplier.get()) < 0.05));
        mDrivetrain.drivePower(speeds.left, -speeds.right);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
