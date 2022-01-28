package frc.robot.commands.Feeder;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class moveUp1Ball extends CommandBase {

    public final Feeder mFeeder = Feeder.getInstance();

    public moveUp1Ball() {
        this.addRequirements(mFeeder);
        this.setName("Move Feeder");
    }

    @Override
    public void execute() {
        while(!mFeeder.getShooterSensor()) {
            mFeeder.moveBelt(Constants.Feeder.slowBeltSpeed);
            mFeeder.moveElevator(Constants.Feeder.slowElevatorSpeed);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}