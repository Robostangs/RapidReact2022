package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class Rotate extends CommandBase {
    private final Climber mClimber = Climber.getInstance();
    private final double mSpeed;

    public Rotate(double speed) {
        addRequirements(mClimber);
        setName("Rotate Climber");
        mSpeed = speed;
    }

    public Rotate() {
        this(Constants.Climber.kDefaultClimberRotationSpeed);
    }

    @Override
    public void initialize() {
        mClimber.setRotationMotorSpeed(mSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        mClimber.setRotationMotorSpeed(0);
    }
}
