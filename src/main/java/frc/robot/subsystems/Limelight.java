package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    public static Limelight instance;

    public static NetworkTable nt;

    public static double tx = 0;
    public static double ty = 0;
    public static double tv = 0;
    public static double ta = 0;

    public Limelight getInstance() {
        if(instance == null) {
            instance = new Limelight();
        }
        return instance;
    }

    public Limelight(){
        nt = NetworkTableInstance.getDefault().getTable("limelight");
        nt.getEntry("pipeline").setNumber(0);
    }

    public static void refresh() {
        nt = NetworkTableInstance.getDefault().getTable("limelight");
        tx = nt.getEntry("tx").getDouble(0);
        ty = nt.getEntry("ty").getDouble(0);
        tv = nt.getEntry("tv").getDouble(0);
        ta = nt.getEntry("ta").getDouble(0);
    }

    public static double getTx() {
        return tx;
    }

    public static double getTy() {
        return ty;
    }

    public static double getTv() {
        return tv;
    }

    public static double getTa() {
        return ta;
    }

    public static void ledOn() {
        nt.getEntry("ledMode").setNumber(0);
    }

    public static void ledOff() {
        nt.getEntry("ledMode").setNumber(1);
    }

    public static void ledBlink() {
        nt.getEntry("ledMode").setNumber(2);
    }
}
