package frc.robot.commands.shooter;

import java.util.function.Supplier;

import frc.robot.ShooterMappings;

public class SetDistanceShooterState extends SetVariableShooterState {
    public SetDistanceShooterState(Supplier<Double> distanceSupplier) {
        super(() -> ShooterMappings.getShooterState(distanceSupplier.get()));
        setName("Set Distance-based Shooter State");
    }
}
