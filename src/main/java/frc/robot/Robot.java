// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.Console;

import javax.swing.text.StyleContext.SmallAttributeSet;

import org.dyn4j.geometry.decompose.SweepLine;
import org.photonvision.targeting.PhotonPipelineMetadata;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.InactiveRemover;
import frc.robot.commands.Scoring.WaitForCoralIntake;
import frc.robot.commands.swervedrive.AutoAlignment;
import frc.robot.subsystems.swervedrive.Vision;
import frc.robot.subsystems.swervedrive.AutoDetection.Cameras;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.ElevatorState;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the TimedRobot documentation. If you change the name of this class or the package after creating this
 * project, you must also update the build.gradle file in the project.
 */
public class Robot extends TimedRobot
{

  private static Robot   instance;
  private        Command m_autonomousCommand;

  public RobotContainer m_robotContainer;

  private Timer disabledTimer;

  public Robot()
  {
    instance = this;

    // CameraServer.startAutomaticCapture();
  }

  public static Robot getInstance()
  {
    return instance;
  }

  /**
   * This function is run when the robot is first started up and should be used for any initialization code.
   */
  @Override
  public void robotInit()
  {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    

    m_robotContainer = new RobotContainer();

    // Create a timer to disable motor brake a few seconds after disable.  This will let the robot stop
    // immediately when disabled, but then also let it be pushed more 
    disabledTimer = new Timer();

    // Constants.visionTimerOffset = Vision.Cameras.LEFT_CAM.resultsList.get(0).getTimestampSeconds();

    

    if (isSimulation())
    {
      DriverStation.silenceJoystickConnectionWarning(true);
    }

    m_robotContainer.setDriveFeedForward(.0002, 2.8, 0);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics that you want ran
   * during disabled, autonomous, teleoperated and test.
   *  
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    //SmartDashboard.putBoolean("right camrea status", Constants.vision.getEnabled(1));

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit()
  {

    Commands.run(() -> Constants.LEDSystem.setGOAT(), Constants.LEDSystem);
    disabledTimer.reset();
    disabledTimer.start();
    
  }

  @Override
  public void disabledPeriodic()
  {

    if (disabledTimer.hasElapsed(Constants.DRIVE_CONSTANTS.wheelLockTime()))
    {
      disabledTimer.stop();
    }
    m_robotContainer.resetAndStop();
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit()
  {
    Constants.drivebase.zeroGyroWithAlliance();
    
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.schedule();
    }


  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
    // Constants.SHOOTER.processIntakeCoralAuto();
  }

  @Override
  public void teleopInit()
  {

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.cancel();
    } else
    {
      CommandScheduler.getInstance().cancelAll();
    }
    m_robotContainer.setDriveMode();
    Constants.ELEVATOR.setGoal(Constants.ELEVATOR.getElevatorHeightEncoder());
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {

    m_robotContainer.autoLock();
    m_robotContainer.slowMode();


    SmartDashboard.putBoolean("algaeee", Constants.ALGAE_REMOVER.isExtended);

    SmartDashboard.putString("LED COLOR", Constants.LEDSystem.currentColor);
    //SmartDashboard.putBoolean("Right Camera Enabled", Constants.vision.getEnabled(1));
    


  }


  /**
   * This function is called once when the robot is first started up.
   */
  @Override
  public void simulationInit()
  {
  }

  /**
   * This function is called periodically whilst in simulation.
   */
  @Override
  public void simulationPeriodic()
  {
  }
}
