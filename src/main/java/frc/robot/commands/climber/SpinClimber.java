package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class SpinClimber extends CommandBase {

    private final double mSpeed;
    private final Climber mClimber = Climber.getInstance();

    public SpinClimber(double speed) {
        addRequirements(mClimber);
        setName("Spinning Climber");
        mSpeed = speed;
    }

    @Override
    public void initialize() {
        mClimber.setRotationMotorSpeed(mSpeed);
    }
}
