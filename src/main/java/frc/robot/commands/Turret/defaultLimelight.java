package frc.robot.commands.turret;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Limelight;

public class DefaultLimelight extends CommandBase {

    private final Turret mTurret = Turret.getInstance();
    private final Supplier<Double> m_manip; // XXX: Value is unused
    private final Supplier<Boolean> mManipButton;
    private double lastTx = 1;
    private int counter = 50;

    public DefaultLimelight(Supplier<Double> manip, Supplier<Boolean> manipButton) {
        addRequirements(mTurret);
        setName("Full Auto");
        m_manip = manip;
        mManipButton = manipButton;
    }

    @Override
    public void execute() {
        if (Feeder.getInstance().getShooterSensorLight()) {
            counter = 50;
        }
        if (Feeder.getInstance().getShooterSensorLight() || counter > 0) {
            Limelight.ledOn();
            if (Limelight.getTv() == 1) {
                // double Vx = dt.getGyroVelocityX() * Math.sin(180 -
                // m_Turret.getTurrentAngle());
                // lastTx = Limelight.getTx();
                // double theta = Math.atan2((Constants.Turret.drivingOffset * Vx),
                // (Utils.dist(Limelight.getTy())));
                // // theta + Limelight.getTx()
                mTurret.limelightSetAngle(Limelight.getTx());
            } else {
                if (mManipButton.get()) {
                    double m_position = 200 * Math.signum(lastTx);
                    mTurret.setAngle(m_position);
                    if ((mTurret.getTurrentPosition() >= Constants.Turret.kRotationMotorMax - 4000.0)
                            && (Math.signum(m_position) > 0)) {
                        lastTx = -lastTx;
                    } else if ((mTurret.getTurrentPosition() <= 4000.0) && (Math.signum(m_position) < 0)) {
                        lastTx = -lastTx;
                    }
                    SmartDashboard.putNumber("m_position", m_position);
                    SmartDashboard.putNumber("m_tx", lastTx);
                }
            }

        } else {
            mTurret.setAngle(-90);
            Limelight.ledOff();
        }
        --counter;
    }
}
