package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class Rotate extends CommandBase {
    private final Climber.Rotator mRotator = Climber.getInstance().getRotator();
    private final double mSpeed;

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
        mRotator.setPower(0);
    }
}
