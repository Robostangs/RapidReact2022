
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.climber.ClimbSequenceManager;
import frc.robot.auto.SimpleAuto;
import frc.robot.commands.PrimeShooting;
import frc.robot.commands.drivetrain.CustomArcade;
import frc.robot.commands.elevator.RunElevator;
import frc.robot.commands.feeder.ControlManual;
import frc.robot.commands.feeder.DefaultFeeder;
import frc.robot.commands.intake.Active;
import frc.robot.commands.shooter.SetShooterState;
import frc.robot.commands.turret.DefaultTurret;
import frc.robot.commands.turret.Protect;
import frc.robot.commands.turret.ToRobotAngle;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Music;

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
    private static final XboxController mDriver = new XboxController(0);
    private static final XboxController mManip = new XboxController(1);
    private final ClimbSequenceManager mSequenceManager = ClimbSequenceManager.getInstance();
    private static Spark blinken = new Spark(11);
    private static Music music = Music.getInstance();
    

    // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        Limelight.doNothing();
        blinken.set(-0.99);
        music.addMotors(new TalonFX(Constants.Drivetrain.kLeftBackID), new TalonFX(Constants.Drivetrain.kLeftTopID), new TalonFX(Constants.Drivetrain.kRightBackID), new TalonFX(Constants.Drivetrain.kRightTopID));
        music.loadMusic("src\\main\\deploy\\Dynamite.chrp");
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() { 
        // DEFAULT COMMANDS
        mDrivetrain.setDefaultCommand(
            new CustomArcade(
                () -> mDriver.getLeftTriggerAxis() > 0.01 ? -mDriver.getLeftTriggerAxis() : mDriver.getRightTriggerAxis(),
                mDriver::getLeftX));

        mFeeder.setDefaultCommand(new DefaultFeeder());

        // DRIVER CONTROLS
        new JoystickButton(mDriver, XboxController.Button.kA.value)
            .whileHeld(new Active());

        new JoystickButton(mDriver, XboxController.Button.kY.value)
            .whileHeld(
                new Active(-Constants.IntakeConstants.kDefaultSpeed)
                    .alongWith(new ControlManual(() -> -Constants.Feeder.kDefaultSpeed))
                    .withName("Exgest"));

        // MANIP CONTROLS
        new Button(() -> mManip.getLeftTriggerAxis() >= 0.5)
            .whileHeld(new PrimeShooting())
            .whenReleased(new Protect().withTimeout(1).withName("Protect"));

        new Button(() -> mManip.
        getRightTriggerAxis() >= 0.5)
            .whileHeld(
                new DefaultTurret()
                    .alongWith(new SetShooterState(ShooterMappings.getShooterState(94))))
            .whenReleased(new Protect().withTimeout(1).withName("Protect"));

        new JoystickButton(mManip, XboxController.Button.kA.value)
            .whileHeld(new RunElevator());

        new JoystickButton(mManip, XboxController.Button.kB.value)
            .whileHeld(new SetShooterState(ShooterMappings.getShooterState(0)).alongWith(new ToRobotAngle(0)))
            .whenReleased(new Protect().withTimeout(1).withName("Protect"));

        // MANIP CLIMBER CONTROLS
        final Set<ClimbSequenceManager.ClimbState> keyStates = new HashSet<>(Arrays.asList(
            ClimbSequenceManager.ClimbState.kStarting,
            ClimbSequenceManager.ClimbState.kCallibrated));

        new Button(new JoystickButton(mManip, XboxController.Button.kLeftBumper.value).and(new JoystickButton(mManip, XboxController.Button.kRightBumper.value)))
            .whenPressed(
                new ConditionalCommand(
                    new InstantCommand(mSequenceManager::proceed),
                    new InstantCommand(),
                    () -> keyStates.contains(mSequenceManager.getState()))
                    .withName("Conditional proceed L1+R1"));

        new JoystickButton(mManip, XboxController.Button.kLeftBumper.value)
            .whenPressed(
                new ConditionalCommand(
                    new InstantCommand(mSequenceManager::proceed),
                    new InstantCommand(),
                    () -> !keyStates.contains(mSequenceManager.getState()))
                    .withName("Conditional proceed L1"));

        new JoystickButton(mManip, XboxController.Button.kX.value)
            .whenPressed(
                new InstantCommand(mSequenceManager::interrupt)
                    .withName("Climb Sequence Interrupt"));

        mSequenceManager.setWiggleSupplier(mManip::getLeftY);
        mSequenceManager.setGrabbedBarSupplier(mManip::getYButton);
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
}
