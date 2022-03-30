package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class AddSmartdashboard extends InstantCommand{
    private String mName;

    public AddSmartdashboard(String name) {
        mName = name;
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("PIT Test", mName);
        super.initialize();
    }

    
}
