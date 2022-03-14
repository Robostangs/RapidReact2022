package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Elevator;

public class EnableElevator extends InstantCommand {

    private final Elevator mElevator = Elevator.getInstance();
    private final double mPower;

    public EnableElevator(double power) {
        addRequirements(mElevator);
        mPower = power;
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