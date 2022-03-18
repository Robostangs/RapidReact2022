package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;

public class RunElevator extends CommandBase {

    private final Elevator mElevator = Elevator.getInstance();
    private final double mPower;

    public RunElevator(double power) {
        addRequirements(mElevator);
        mPower = power;
    }

    public RunElevator() {
        this(Constants.Elevator.kDefaultPower);
    }

    @Override
    public void initialize() {
        mElevator.setPower(mPower);
    }

    @Override
    public void end(boolean interrupted) {
        mElevator.setPower(0);
    }
}
