package frc.robot.commands.drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends CommandBase{
    
    private Supplier<Double> mLeftSpeed, mRightSpeed;
    private Drivetrain mDrivetrain = Drivetrain.getInstance();

    public TankDrive(Supplier<Double> leftSpeed, Supplier<Double> rightSpeed) {
        this.setName("TankDrive");
        this.addRequirements(mDrivetrain);
        mLeftSpeed = leftSpeed;
        mRightSpeed = rightSpeed;
    }

    @Override
    public void execute() {
        mDrivetrain.drivePower(mLeftSpeed.get(), mRightSpeed.get());
    }

}
