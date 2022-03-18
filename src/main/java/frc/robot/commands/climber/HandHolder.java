package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;

final class HandHolder {
    public Climber.Hand hand;

    public HandHolder(Climber.Hand hand) {
        this.hand = hand;
    }

    public HandHolder() {
        this(null);
    }
}
