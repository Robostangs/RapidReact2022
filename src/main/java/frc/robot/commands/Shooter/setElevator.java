package frc.robot.commands.Shooter;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class setElevator extends CommandBase{
    
    private final Shooter mShooter = Shooter.getInstance();
    private final Supplier<Double> m_speed;

    public setElevator(Supplier<Double> speed) {
        this.addRequirements(mShooter);
        this.setName("Set Elevator Speed");
        m_speed = speed;
    }

    @Override
    public void execute() {
        mShooter.setElevatorPower(m_speed.get());             
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

