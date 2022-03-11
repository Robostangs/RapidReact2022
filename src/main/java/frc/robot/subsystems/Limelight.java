package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    public static Limelight instance;

    private static final NetworkTable LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    private static double tx = 0;
    private static double ty = 0;
    private static double tv = 0;
    private static double ta = 0;

    public Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }
        return instance;
    }

    public Limelight() {
        LimelightTable.getEntry("pipeline").setNumber(0);
    }

    public static void refresh() {
        // LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        tx = LimelightTable.getEntry("tx").getDouble(0);
        ty = LimelightTable.getEntry("ty").getDouble(0);
        tv = LimelightTable.getEntry("tv").getDouble(0);
        ta = LimelightTable.getEntry("ta").getDouble(0);
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
        LimelightTable.getEntry("ledMode").setNumber(0);
    }

    public static void ledOff() {
        LimelightTable.getEntry("ledMode").setNumber(1);
    }

    public static void ledBlink() {
        LimelightTable.getEntry("ledMode").setNumber(2);
    }
}
