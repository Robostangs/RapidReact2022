package frc.robot.commands.drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {

    private final Drivetrain mDrivetrain = Drivetrain.getInstance();

    private final Supplier<Double> mForwardSupplier;
    private final Supplier<Double> mTurnSupplier;

    // We're inputting a function here so that we can put in the function of get value from joysticks
    // ADD DEADZONE!!!
    public ArcadeDrive(Supplier<Double> funcForward, Supplier<Double> funcTurn) {
        addRequirements(mDrivetrain);
        setName("Arcade Drive");
        mForwardSupplier = funcForward;
        mTurnSupplier = funcTurn;
        // mDrivetrain.resetRotation();
    }

    @Override
    public void execute() {
        WheelSpeeds speeds = DifferentialDrive.arcadeDriveIK(
            Utils.deadzone(mForwardSupplier.get()),
            Utils.deadzone(mTurnSupplier.get()),
            false);
        mDrivetrain.drivePower(speeds.left, speeds.right);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
