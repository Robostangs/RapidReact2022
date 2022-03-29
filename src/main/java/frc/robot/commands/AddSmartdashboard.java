package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class AddSmartdashboard extends InstantCommand{

    public AddSmartdashboard(String name, String value) {
        SmartDashboard.putString(name, value);
    }

    public AddSmartdashboard(String name) {
        SmartDashboard.putString("PIT Test", name);
    }
    
}
