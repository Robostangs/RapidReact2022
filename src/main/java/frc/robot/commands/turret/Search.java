package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class Search extends CommandBase {

    private Turret mTurret = Turret.getInstance();

    public Search() {
        this.addRequirements(mTurret);
        this.setName("I lost my target");
    }

    @Override
    public void initialize() {
        mTurret.setPercentSpeed(-1);
    }

    @Override
    public void execute() {
        if(mTurret.getAngle() < -80) {
            mTurret.setPercentSpeed(1);
        } else if(mTurret.getAngle() > 80) {
            mTurret.setPercentSpeed(-1);
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
