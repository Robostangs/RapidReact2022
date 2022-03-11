// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Limelight;

// import com.revrobotics.ColorSensorV3;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private final RobotContainer mRobotContainer = new RobotContainer();
    private Command mAutonomousCommand;
    @SuppressWarnings("unused")
    private final PowerDistribution mPowerDistributionPanel = new PowerDistribution();
    // private Drivetrain m_Drivetrain;
    // private XboxController driver, manip;
    // private Feeder m_Feeder;
    // private Intake m_Intake;
    // private Shooter m_Shooter;
    // private Turret m_Turret;
    
    // private ColorSensorV3 color;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        // m_Drivetrain  = Drivetrain.getInstance();
        // driver = new XboxController(0);
        // m_Feeder = Feeder.getInstance();
        // m_Turret = Turret.getInstance();
        // // manip = new XboxController(1);
        // m_Intake = Intake.getInstance();
        
        // m_Shooter = Shooter.getInstance();
        // NetworkTableInstance.reportWarning(warning, printTrace);
        CommandScheduler.getInstance().onCommandInitialize((Command c) -> {System.out.print("INITIALIZED: " + c.getName());});
        CommandScheduler.getInstance().onCommandFinish((Command c) -> {System.out.print("FINISHED: " + c.getName());});
        CommandScheduler.getInstance().onCommandInterrupt((Command c) -> {System.out.print("INTERUPTED: " + c.getName());});
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        Limelight.refresh(); // Use limelight periodic
        // SmartDashboard.putData("PDP", pdp);
        CommandScheduler.getInstance().run();
        // CommandScheduler.getInstance().onCommandInitialize(System.out.println());
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
        // Limelight.ledOff();
    }

    @Override
    public void disabledPeriodic() {}

    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit() {
        mAutonomousCommand = mRobotContainer.getAutonomousCommand();
        // schedule the autonomous command (example)
        if (mAutonomousCommand != null) {
            mAutonomousCommand.schedule();
        }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        // Subsystem periodic methods are automatically called by the Command Scheduler periodically
        // Turret.getInstance().periodic();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        // Limelight.ledOn();

        // color = new ColorSensorV3(I2C.Port.kOnboard);
        if (mAutonomousCommand != null) {
            mAutonomousCommand.cancel();
        }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() { 
        // m_Feeder.update();
        // m_Shooter.test();
        
        // SmartDashboard.putNumber("Color Red", color.getRed());
        // SmartDashboard.putNumber("Color Blue", color.getBlue());
        // SmartDashboard.putNumber("Color Green", color.getGreen());
        // m_Shooter.setRightShooterPower(driver.getRightX());
    }


    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
}
