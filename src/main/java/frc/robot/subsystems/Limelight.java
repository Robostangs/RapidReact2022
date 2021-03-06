package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;

public class Limelight extends SubsystemBase {

    private static final Limelight instance = new Limelight();
    private final NetworkTable LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    private static double tx = 0;
    private static double ty = 0;
    private static double tv = 0;
    private static double ta = 0;

    private Limelight() {
        if(LimelightTable == null) {
            DataLogManager.log("LLtable is Null"); 
        }
        LimelightTable.getEntry("pipeline").setNumber(0);
    }

    public static void doNothing() {}

    @Override
    public void periodic() {
        tx = LimelightTable.getEntry("tx").getDouble(0);
        ty = LimelightTable.getEntry("ty").getDouble(0);
        tv = LimelightTable.getEntry("tv").getDouble(0);
        ta = LimelightTable.getEntry("ta").getDouble(0);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("tx", () -> tx, null);
        builder.addDoubleProperty("ty", () -> ty, null);
        builder.addDoubleProperty("tv", () -> tv, null);
        builder.addDoubleProperty("ta", () -> ta, null);
        builder.addDoubleProperty("distance", Limelight::getDistance, null);
    }

    public static double getDistance() {
        return Constants.Limelight.kTargetHeightDelta / (Math.tan(Utils.degToRad(ty + Constants.Limelight.kLimelightAngle)));
    }
    //Actual Distance = difH / (tan(ty+?)) 106-6.96  46.7 83.25  0.77

    public static double getTx() {
        return tx;
    }

    public static double getTy() {
        return ty;
    }

    public static int getTv() {
        return (int) Math.round(tv);
    }

    public static double getTa() {
        return ta;
    }

    public static void enableLEDs() {
        instance.LimelightTable.getEntry("ledMode").setNumber(0);
    }

    public static void disableLEDs() {
        instance.LimelightTable.getEntry("ledMode").setNumber(1);
    }

    public static void blinkLEDs() {
        instance.LimelightTable.getEntry("ledMode").setNumber(2);
    }
}
