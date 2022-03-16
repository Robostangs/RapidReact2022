package frc.robot.commands.climber;

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
    public void initialize() {
        mRotator.setPower(mSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Rotate finished");
        } else {
            System.out.println("Rotate interrupted");
        }
        mRotator.setPower(0);
    }
}
