// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.auto.simpleAuto;
import frc.robot.commands.Drivetrain.ArcadeDrive;
import frc.robot.commands.Feeder.controlManual;
import frc.robot.commands.Feeder.moveUp1Ball;
import frc.robot.commands.Intake.Activate;
import frc.robot.commands.Shooter.betterShoot;
import frc.robot.commands.Shooter.setElevator;
import frc.robot.commands.Shooter.shoot;
import frc.robot.commands.Turret.reset;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.auto.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
    private Command m_autonomousCommand;
    private Drivetrain m_Drivetrain;
    private XboxController driver, manip;
    private RobotContainer m_robotContainer;
    private Intake m_Intake;
    private Feeder m_Feeder;
    private Shooter m_Shooter;
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
        m_Shooter = Shooter.getInstance();
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {    
        //Actual CODE
        //m_Drivetrain.setDefaultCommand(new ArcadeDrive( () -> {return -driver.getRightX();}, () -> {return driver.getLeftY();}));
        //m_Intake.setDefaultCommand(new Activate(driver.getXButton()));
        //if(driver.getXButton()) {new moveUp1Ball();}
        //if(m_Limelight.getTV()) {m_Turret.setDefaultCommand(new FollowLimelight());} else {new search()}
        //Shooter.setDefaultCommannd(new shoot(manip.getLeftBumper()));
        
            // m_Drivetrain.driveDistance(10);
            // m_Drivetrain.updateValues();
        new JoystickButton(manip, 2).whenPressed(new betterShoot(0, -0.5, 0.4));

        m_Drivetrain.setDefaultCommand(
            new ArcadeDrive(() -> {return -driver.getLeftX();},
                            () -> {return -(driver.getRightTriggerAxis() - driver.getLeftTriggerAxis());}));

        m_Intake.setDefaultCommand(new Activate(() -> { return driver.getAButton() ? 50.0 : 0.0; }));
        m_Feeder.setDefaultCommand(new moveUp1Ball(driver::getAButton));
        // m_Turret.setDefaultCommand(new reset(driver::getBButton));a
        // m_Feeder.setDefaultCommand(new controlManual(() -> {return driver.getYButton() ? -100.0: 0.0;},
        //                                             () -> {return driver.getYButton() ? -100.0: 0.0;}));



        // Josh's preferred style 
        // m_Drivetrain.drivePower(
        //     Utils.deadzone(driver.getLeftX() - -1*(driver.getRightTriggerAxis() - driver.getLeftTriggerAxis())),
        //     Utils.deadzone(driver.getLeftX() + -1*(driver.getRightTriggerAxis() - driver.getLeftTriggerAxis()))
        // );
        // m_Intake.setSpeed(0);
    
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
