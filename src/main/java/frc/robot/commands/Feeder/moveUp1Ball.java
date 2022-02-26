package frc.robot.commands.Feeder;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;

public class moveUp1Ball extends CommandBase {

    public final Feeder mFeeder = Feeder.getInstance();
    private Supplier<Boolean> m_moveFeeder;

    public moveUp1Ball(Supplier<Boolean> moveFeeder) {
        this.addRequirements(mFeeder);
        this.setName("Move Feeder");
        m_moveFeeder = moveFeeder;
    }

    @Override
    public void execute() {
        if(m_moveFeeder.get()) {
        mFeeder.moveBelt(Constants.Feeder.slowBeltSpeed);
        }
        if (mFeeder.getShooterSensorLight()) {
            mFeeder.moveBelt(0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}