package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Turret;

public class Search extends CommandBase{
    
    private Turret mTurret = Turret.getInstance();
    private double lastTx = 1;

    @Override
    public void initialize() {
        this.addRequirements(mTurret);
        this.setName("I lost my target");
    }

    @Override
    public void execute() {
        mTurret.setAngularVelocitySetpoint(lastTx *0.6, Constants.Turret.kTurningFeedForward);
        if ((mTurret.getAngle() >= Constants.Turret.kRotationMotorMax - Constants.Turret.kRotationMotorSoftLimitOffset) && (lastTx > 0)) {
            lastTx = -lastTx;
        } else if ((mTurret.getAngle() <= Constants.Turret.kRotationMotorSoftLimitOffset) && (lastTx < 0)) {
            lastTx = -lastTx;
        }
    }

}
