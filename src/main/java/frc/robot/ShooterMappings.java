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

    static {
        kShooterStates = new HashMap<Double, Shooter.State>();
        addEntry(0, 320.0, 5300.0); 
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
            if(distance > below) {
                return below;
            }
        }
        // for(int i = 0; i < kDistances.size(); i++) {
        //     if(distance <= kDistances.get(i)) {
        //         return (i-1);
        //     }
        // }
        return 0;
    }

    public static Shooter.State getShooterState(double distance) {
        return kShooterStates.get(getNearestKey(distance));
    }
}
