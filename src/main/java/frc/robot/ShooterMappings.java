package frc.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import frc.robot.subsystems.Shooter;

public class ShooterMappings {
    public static final HashMap<Double, Shooter.State> kShooterStates;
    private static void addEntry(double distance, double topShooterPower, double bottomShooterPower) {
        kShooterStates.put(distance, new Shooter.State(topShooterPower, bottomShooterPower);
    }
    // distance =           {0,     56,     61,     68,     80,     84,     87,     92,     104,    108,    116,    128,    142,    177,    165},
    // topShooterPower =    {500,   2000,   2100,   2000,   2000,   2100,   2200,   2175,   2100,   2300,   2250,   2600,   2750,   2761,   2700},
    // bottomShooterPower = {500,   2000,   2000,   2300,   2350,   2400,   2400,   2459,   2500,   2586,   2600,   2990,   3163,   3000,   2900},
    // // angle =              {10,    0,      0,      0,      0,      0,      0,      0,      0,      0,      0,      0,      0,      10,     10};
    static {
        kShooterStates = new HashMap<Double, Shooter.State>();
        addEntry(-10, 0, 0);
        addEntry(0, 800, 800);
        addEntry(56, 2000, 2000);
        addEntry(61, 2100, 2000);
        addEntry(68, 2000, 2300);
        addEntry(80, 2000, 2350);
        addEntry(84, 2100, 2400);
        addEntry(87, 2200, 2400);
        addEntry(92, 2175, 2459);
        addEntry(104, 2100, 2500);
        addEntry(108, 2300, 2586);
        addEntry(116, 2250, 2600);
        addEntry(128, 2600, 2990);
        addEntry(142, 2750, 3163);
        // addEntry(177, 2761, 3000, 10);
        // addEntry(165, 2700, 2900, 10);
    };
    private static final ArrayList<Double> kDistances = new ArrayList<Double>();
    static {
        kDistances.addAll(kShooterStates.keySet());
        Collections.sort(kDistances);
    }

    public static double getNearestKey(double distance) {
        int index = Collections.binarySearch(kDistances, distance);
        if (index >= 0) {
            return kDistances.get(index);
        } else {
            index = -index - 1;
            if(index < 1) {
                return kDistances.get(0);
            } else if(index >= kDistances.size()) {
                return kDistances.get(kDistances.size() - 1);
            }
            double below = kDistances.get(index - 1);
            double above = kDistances.get(index);
            return (distance - below) < (above - distance) ? below : above;
        }
    }

    public static Shooter.State getShooterState(double distance) {
        return kShooterStates.get(getNearestKey(distance));
    }
}
