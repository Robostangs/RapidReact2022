// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.climber.AutoClimb;
import frc.robot.commands.climber.ClimbPrep;
import frc.robot.auto.SimpleAuto;
import frc.robot.commands.PrimeShooting;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.CurvatureDrive;
import frc.robot.commands.drivetrain.CustomArcade;
import frc.robot.commands.drivetrain.CustomArcade;
import frc.robot.commands.elevator.RunElevator;
import frc.robot.commands.feeder.DefaultFeeder;
import frc.robot.commands.intake.Active;
import frc.robot.commands.shooter.SetShooterState;
import frc.robot.commands.turret.DefaultTurret;
import frc.robot.commands.turret.Protect;
import frc.robot.commands.turret.ToRobotAngle;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Turret;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
    private final Drivetrain mDrivetrain = Drivetrain.getInstance();
    private final Feeder mFeeder = Feeder.getInstance();
    private final Turret mTurret = Turret.getInstance();
    private static final XboxController mDriver = new XboxController(0);
    private static final XboxController mManip = new XboxController(1);

    // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {    
        
        mDrivetrain.setDefaultCommand(
            new CustomArcade(
                () -> mDriver.getLeftTriggerAxis() > 0.01 ? -mDriver.getLeftTriggerAxis() : mDriver.getRightTriggerAxis(),
                mDriver::getLeftX)
        );
        mFeeder.setDefaultCommand(new DefaultFeeder());
        // mTurret.setDefaultCommand(new DefaultTurret());

        new JoystickButton(mDriver, XboxController.Button.kA.value)
            .whileHeld(new Active());

        new JoystickButton(mManip, XboxController.Button.kLeftBumper.value)
            .whenPressed(new ClimbPrep());
        new JoystickButton(mManip, XboxController.Button.kRightBumper.value)
            .whenPressed(new AutoClimb(mManip::getLeftY, mManip::getYButton));

        // new Button(() -> mManip.getLeftTriggerAxis() >= 0.5)
        //     .whileHeld(new PrimeShooting())
        //     .whenReleased(new Protect());
        // new JoystickButton(mManip, XboxController.Button.kA.value)
        //     .whileHeld(new RunElevator());
        // new Button(() -> mManip.getRightTriggerAxis() >= 0.5)
        //     .whileHeld(new ParallelCommandGroup(
        //         new ToRobotAngle(0),
        //         new SetShooterState(ShooterMappings.getShooterState(0))));

        // new JoystickButton(manip, XboxController.Button.kB.value)
        //     .whenPressed(new AutoShoot(4800, 2500, 0))
        //     .whenInactive(() -> {new SetVariableShooterState(0, 0); new SetElevatorPower(0);});
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
  
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new SimpleAuto();
    }

    public static boolean getClimbProceed() {
        return mManip.getXButton();
    }
}
