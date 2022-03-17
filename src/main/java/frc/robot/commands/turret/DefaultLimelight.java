package frc.robot.commands.Turret;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Limelight;

public class defaultLimelight extends CommandBase {

    private Turret m_Turret;
    private double lastTx;
    private final Debouncer mTopFeederDebouncer = new Debouncer(Constants.Turret.kDoubleDebouncerTime);

    public defaultLimelight() {
        m_Turret = Turret.getInstance();
        this.addRequirements(m_Turret);
        this.setName("Full Auto");
        lastTx = 1;
    }
    
    @Override
    public void execute() {
            if (mTopFeederDebouncer.calculate(Feeder.getInstance().getShooterSensorLight())) {
                Limelight.enableLEDs();
                if (Limelight.getTv() == 1) {
                    // double Vx = dt.getGyroVelocityX() * Math.sin(180 -
                    // m_Turret.getTurrentAngle());
                    // lastTx = Limelight.getTx();
                    // double theta = Math.atan2((Constants.Turret.drivingOffset * Vx),
                    // (Utils.dist(Limelight.getTy())));
                    // // theta + Limelight.getTx()
                    m_Turret.limelightSetAngle(Limelight.getTx());
                } else {
                        double m_position = 200 * Math.signum(lastTx);
                        m_Turret.setAngle(m_position);
                        if ((m_Turret.getTurrentPosition() >= Constants.Turret.rotationMotorMax - Constants.Turret.kRotationMotorSoftLimitOffset)
                                && (Math.signum(m_position) > 0)) {
                            lastTx = -lastTx;
                        } else if ((m_Turret.getTurrentPosition() <= Constants.Turret.kRotationMotorSoftLimitOffset) && (Math.signum(m_position) < 0)) {
                            lastTx = -lastTx;
                        }
                        SmartDashboard.putNumber("m_position", m_position);
                        SmartDashboard.putNumber("m_tx", lastTx);
                }

            }
        }

        @Override
        public void end(boolean interrupted) {
            new protect();
            super.end(interrupted);
        }
    }