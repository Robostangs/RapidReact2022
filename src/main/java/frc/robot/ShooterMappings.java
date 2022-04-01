package frc.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import frc.robot.subsystems.Shooter;

public class ShooterMappings {

    public static final HashMap<Double, Shooter.State> kShooterStates;

    private static void addEntry(double distance, double topShooterPower, double bottomShooterPower) {
        kShooterStates.put(distance, new Shooter.State(topShooterPower, bottomShooterPower));
    }
    //distance =           {0,     56,     61,     68,     80,     84,     87,     92,     104,    108,    116,    128,    142,    177,    165},
    //topShooterPower =    {500,   2000,   2100,   2000,   2000,   2100,   2200,   2175,   2100,   2300,   2250,   2600,   2750,   2761,   2700},
    //bottomShooterPower = {500,   2000,   2000,   2300,   2350,   2400,   2400,   2459,   2500,   2586,   2600,   2990,   3163,   3000,   2900},
    // angle =             {10,    0,      0,      0,      0,      0,      0,      0,      0,      0,      0,      0,      0,      10,     10};
    static {
        kShooterStates = new HashMap<Double, Shooter.State>();
        // addEntry(0, 320.0, 5300.0);
        addEntry(0, 1000, 1000);
        addEntry(94, 2200.0, 2100.0);
        addEntry(102, 2450.0, 2350.0);
        addEntry(118, 2700.0, 2400.0);
        addEntry(130, 3100, 2450);
        addEntry(142, 3750.0, 475.0);
        addEntry(154, 4162.0, 487.0);
        addEntry(166, 4255.0, 492.0);
        addEntry(173, 4600.0, 250.0);
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
            return (distance - below) > (above - distance) ? above : below;
            // return kDistances.get(index - 1);
        }
    }

    public static Shooter.State getShooterState(double distance) {
        return kShooterStates.get(getNearestKey(distance));
    }
}
