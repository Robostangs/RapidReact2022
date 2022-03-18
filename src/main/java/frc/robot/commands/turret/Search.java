package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;

public class Search extends CommandBase {

    private Turret mTurret = Turret.getInstance();
    private double mLastTX = -1;

    
    public Search() {
        this.addRequirements(mTurret);
        this.setName("I lost my target");
    }

    @Override
    public void initialize() {
        mTurret.setPercentSpeed(Constants.Turret.kRotationMotorSearchSpeed * Math.signum(mLastTX));
    }

    public void setLastTx(double lastTX) {
        if(lastTX != 0) {
            mLastTX = lastTX;
        }
    }

    @Override
    public void execute() {
        Limelight.enableLEDs();
        if(mTurret.getAngle() < -80) {
            mTurret.setPercentSpeed(Constants.Turret.kRotationMotorSearchSpeed);
        } else if(mTurret.getAngle() > 80) {
            mTurret.setPercentSpeed(-Constants.Turret.kRotationMotorSearchSpeed);
        }
        // mTurret.setAngularVelocitySetpoint(lastTx * 0.6, Constants.Turret.kTurningFeedForward);
        // if ((mTurret.getAngle() >= Constants.Turret.kRotationMotorMax - Constants.Turret.kRotationMotorSoftLimitOffset) && (lastTx > 0)) {
        //     lastTx = -lastTx;
        // } else if ((mTurret.getAngle() <= Constants.Turret.kRotationMotorSoftLimitOffset) && (lastTx < 0)) {
        //     lastTx = -lastTx;
        // }
    }

    @Override
    public void end(boolean interrupted) {
        mTurret.setPercentSpeed(0);
        super.end(interrupted);
    }
}
