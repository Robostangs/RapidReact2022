package frc.robot.commands.shooter;

import java.util.function.Supplier;

import frc.robot.ShooterMappings;
import frc.robot.subsystems.Shooter;

public class SetDistanceShooterState extends SetVariableShooterState {
    public SetDistanceShooterState(Supplier<Double> distanceSupplier) {
        super(() -> ShooterMappings.getShooterState(distanceSupplier.get()));
    }
}
