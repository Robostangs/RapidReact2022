package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimbSequenceManager {
    enum ClimbState {
    }

    private static ClimbSequenceManager instance;
    private ClimbState mCurrentState = ClimbState.kStarting;

    public static ClimbSequenceManager getInstance() {
        if(instance == null) {
            instance = new ClimbSequenceManager();
        }
        return instance;
    }

    private ClimbSequenceManager() {}

    public CommandBase getNextCommand() {
        return null;
    }
}
