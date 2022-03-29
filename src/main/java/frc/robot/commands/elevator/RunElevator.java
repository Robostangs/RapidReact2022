package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Limelight;

public class RunElevator extends CommandBase {

    private final Elevator mElevator = Elevator.getInstance();
    private final double mPower;

    public RunElevator(double power) {
        addRequirements(mElevator);
        setName("Moving Elevator");
        mPower = power;
    }

    public RunElevator() {
        this(Constants.Elevator.kDefaultPower);
    }

    @Override
    public void initialize() {
        Limelight.printValues();
        System.out.println(SmartDashboard.getString("Requested state", "No requested state found"));
        mElevator.setPower(mPower);
    }

    @Override
    public void end(boolean interrupted) {
        mElevator.setPower(0);
    }
}
