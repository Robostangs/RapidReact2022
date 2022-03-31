package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;

public class RunElevator extends CommandBase {

    private final Elevator mElevator = Elevator.getInstance();
    private final double mPower;

    public RunElevator(double power) {
        addRequirements(mElevator);
        setName("Run Elevator");
        mPower = power;
    }

    public RunElevator() {
        this(Constants.Elevator.kDefaultPower);
    }

    @Override
    public void initialize() {
        DataLogManager.log(SmartDashboard.getString("Requested state", "No requested state found"));
        mElevator.setPower(mPower);
    }

    @Override
    public void end(boolean interrupted) {
        mElevator.setPower(0);
    }
}
