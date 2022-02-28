// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.auto.simpleAuto;
import frc.robot.commands.Drivetrain.ArcadeDrive;
import frc.robot.commands.Feeder.moveUp1Ball;
import frc.robot.commands.Intake.Activate;
import frc.robot.commands.Shooter.autoShoot;
import frc.robot.commands.Turret.defaultLimelight;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
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
    private Drivetrain m_Drivetrain;
    private XboxController driver, manip;
    private Intake m_Intake;
    private Feeder m_Feeder;
    private Turret m_Turret;
    
    // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        m_Drivetrain  = Drivetrain.getInstance();
        driver = new XboxController(0);
        manip = new XboxController(1);
        m_Intake = Intake.getInstance();
        m_Feeder = Feeder.getInstance();
        m_Turret = Turret.getInstance();
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {    

        m_Drivetrain.setDefaultCommand(
            new ArcadeDrive(() -> {return -driver.getLeftX();},
                            () -> {return -(driver.getRightTriggerAxis() - driver.getLeftTriggerAxis());}));

        new JoystickButton(driver, XboxController.Button.kA.value).whenPressed(new Activate(50));
        new JoystickButton(driver, XboxController.Button.kA.value).whenPressed(new moveUp1Ball());

        m_Turret.setDefaultCommand(new defaultLimelight(manip::getLeftY));
        new JoystickButton(manip, XboxController.Button.kB.value).whenPressed(new autoShoot(-0.5, 0.4));
    

        //Stabilization stuffff
        // double left = Utils.deadzone(-driver.getLeftY());
        // double rightPwr = Utils.deadzone(driver.getLeftY()) + (m_Drivetrain.getAngle() * m_Drivetrain.getConstant() * (1 - Utils.deadzone(driver.getLeftY())));
        // m_Drivetrain.drivePower(
        //     left, 
        //     rightPwr
        // );
        // System.out.println(m_Drivetrain.getConstant());
        // System.out.println(rightPwr);        
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
  
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new simpleAuto();
    }
}
