package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class Rotate extends CommandBase {
    protected static final Climber.Rotator mRotator = Climber.getInstance().getRotator();
    protected double mSpeed;

    public Rotate(double speed) {
        addRequirements(mRotator);
        setName("Rotate Climber");
        mSpeed = speed;
    }

    public Rotate() {
        this(Constants.Climber.Rotator.kClimbRotationSpeed);
    }

    @Override
    public void execute() {
        mRotator.setPower(mSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            DataLogManager.log("Rotate finished");
        } else {
            DataLogManager.log("Rotate interrupted");
        }
        mRotator.setPower(0);
    }
}
