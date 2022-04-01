package frc.robot.commands.turret;

import frc.robot.Constants;

public class Protect extends ToRobotAngle {

    public Protect() {
        super(Constants.Turret.kProtectAngle, 0);
        setName("Protect Turret");
    }
}
