package frc.robot.auto;

import edu.wpi.first.hal.simulation.SpiReadAutoReceiveBufferCallback;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Utils;
import frc.robot.Constants.Drivetrain;
import frc.robot.commands.Drivetrain.ArcadeDrive;
import frc.robot.commands.Drivetrain.DriveDistance;
import frc.robot.commands.Feeder.moveUp1Ball;
import frc.robot.commands.Intake.Activate;
import frc.robot.commands.Intake.Default;
import frc.robot.commands.Shooter.betterShoot;
import frc.robot.commands.Shooter.shoot;
import frc.robot.subsystems.*;

public class simpleAuto extends SequentialCommandGroup {

    // private SequentialCommandGroup name;

    public simpleAuto() {
        this.setName("auto");
        this.addRequirements(frc.robot.subsystems.Drivetrain.getInstance());
        this.addRequirements(Intake.getInstance());
        this.addRequirements(Feeder.getInstance());
        this.addRequirements(Shooter.getInstance());

        super.addCommands(new InstantCommand(() -> {
            Intake.getInstance().setSpeed(0.5);
        }, Intake.getInstance()));
        super.addCommands(new InstantCommand(() -> {
            frc.robot.subsystems.Drivetrain.getInstance().drivePower(
                0.6, -0.6);
        }, frc.robot.subsystems.Drivetrain.getInstance()));
        super.addCommands(new WaitCommand(1.5));
        super.addCommands(new InstantCommand(() ->{
            Feeder.getInstance().moveBelt(-1);
        }, Feeder.getInstance()));
        super.addCommands(new WaitCommand(1.5));
        super.addCommands(new betterShoot(0, -0.55, 0.4));
        // name = new SequentialCommandGroup(
        // new InstantCommand(() -> {
        // Intake.getInstance().setSpeed(0.5);
        // },Intake.getInstance()),
        // new InstantCommand(() -> {
        // frc.robot.subsystems.Drivetrain.getInstance().drivePower(1.0, 1.0);
        // },frc.robot.subsystems.Drivetrain.getInstance()),
        // new WaitCommand(1.5)
        // );
    }

    @Override
    public void initialize() {
        System.out.println("hi");
        super.initialize();
    }

    // @Override
    // public boolean isFinished() {
    // return name.isFinished();
    // }
    // private double dist;

    // @Override
    // public void init() {
    // //TODO: Calculated Distance 9.68 ft
    // new ParallelCommandGroup(new DriveDistance(9.68), new Default());
    // new TurnToAngle(180);
    // dist = Utils.dist(Limelight.getTx(), Limelight.getTy());
    // //TODO: Add Function to get shooter power from distance
    // new shoot(Utils.getAllignmentPower(dist),
    // Utils.getRightPower(dist),
    // Utils.getleftPower(dist),
    // true);
    // }

}