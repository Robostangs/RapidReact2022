// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.climber.AutoClimb;
import frc.robot.commands.climber.ClimbPrep;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.feeder.DefaultFeeder;
import frc.robot.commands.intake.Activate;
import frc.robot.commands.intake.Deactivate;
import frc.robot.commands.shooter.AutoShoot;
import frc.robot.commands.shooter.SetElevatorPower;
import frc.robot.commands.shooter.SetShooterPower;
import frc.robot.commands.turret.DefaultLimelight;
import frc.robot.subsystems.Climber;
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
    private Drivetrain mDrivetrain = Drivetrain.getInstance();
    private static final XboxController driver = new XboxController(0);
    private static final XboxController manip = new XboxController(1);
    private Turret mTurret = Turret.getInstance();
    private Feeder mFeeder = Feeder.getInstance();
    private Climber mClimber = Climber.getInstance();

    // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
  
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {    
        mDrivetrain.setDefaultCommand(
            new ArcadeDrive(() -> {return -driver.getLeftX();},
                            () -> {return -(driver.getRightTriggerAxis() - driver.getLeftTriggerAxis());}));
        mFeeder.setDefaultCommand(new DefaultFeeder());
        mTurret.setDefaultCommand(new DefaultLimelight(manip::getLeftY, manip::getXButton));

        new JoystickButton(driver, XboxController.Button.kA.value)
            .whenPressed(new Activate())
            .whenInactive(new Deactivate());

        new JoystickButton(manip, XboxController.Button.kB.value)
            .whenPressed(new AutoShoot(4800, 2500, 0))
            .whenInactive(() -> {new SetShooterPower(0, 0); new SetElevatorPower(0);});

        new JoystickButton(manip, XboxController.Button.kRightBumper.value)
        .and(new JoystickButton(manip, XboxController.Button.kLeftBumper.value))
            .toggleWhenActive(new InstantCommand(this::advanceClimbSequence));
    }

    public static boolean getClimbProceed() {
        return manip.getBButton();
    }

    private void advanceClimbSequence() {
        switch (mClimber.getState()) {
            case kWaiting:
                new ClimbPrep().schedule();
                break;
            case kPriming:
                break;
            case kPrimed:
                new AutoClimb().schedule();
                break;
            case kClimbing:
                break;
            case kClimbed:
                break;
        }
    }
}
