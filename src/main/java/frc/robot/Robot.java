// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.climber.AutoClimb;
import frc.robot.commands.climber.ClimbPrep;
import frc.robot.subsystems.Climber;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    // private final RobotContainer mRobotContainer = new RobotContainer();
    private Command mAutonomousCommand;
    @SuppressWarnings("unused")
    private final PowerDistribution mPowerDistributionPanel = new PowerDistribution();

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
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
        CommandScheduler.getInstance().run();
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
        // mAutonomousCommand = mRobotContainer.getAutonomousCommand();
        // schedule the autonomous command (example)
        if (mAutonomousCommand != null) {
            mAutonomousCommand.schedule();
        }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {}

    private final Climber.Hand mHandA;
    private final Climber.Hand mHandB;
    {
        final Climber.Hand[] mHands = Climber.getInstance().getHands();
        mHandA = mHands[0];
        mHandB = mHands[1];
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.

        if (mAutonomousCommand != null) {
            mAutonomousCommand.cancel();
        }
        // mHandA.setLockReference(0.8);
        // mHandB.setLockReference(0.8);
        new JoystickButton(mDriver, XboxController.Button.kB.value)
            .whenPressed(new ClimbPrep());
        new JoystickButton(mDriver, XboxController.Button.kA.value)
            .whenPressed(new AutoClimb());
        new JoystickButton(mDriver, XboxController.Button.kX.value)
            .whenPressed(new InstantCommand(() -> mClimber.getRotator().setEncoderPosition(0)));
    }

    private final XboxController mDriver = new XboxController(0);
    private final Climber mClimber = Climber.getInstance();
    private final Drivetrain mDrivetrain = Drivetrain.getInstance();
    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        // if(mDriver.getLeftTriggerAxis() >= 0.01) {
        //     mClimber.getRotator().setPower(mDriver.getLeftTriggerAxis());
        // } else {
        //     mClimber.getRotator().setPower(-mDriver.getRightTriggerAxis());
        // }
        // mHandA.setClawSpeed(mDriver.getLeftY());
        // mHandB.setClawSpeed(mDriver.getRightY());

        // if (mDriver.getAButton()) {
        //     mHandA.setLockReference(1);
        // }
        // if (mDriver.getXButton()) {
        //     mHandA.setLockReference(0.8);
        // }
        // if (mDriver.getBButton()) {
        //     mHandB.setLockReference(1);
        // }
        // if (mDriver.getYButton()) {
        //     mHandB.setLockReference(0.8);
        // }
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
