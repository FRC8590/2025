
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.*;
import frc.robot.commands.Scoring.*;
import frc.robot.commands.swervedrive.*;
import frc.robot.commands.swervedrive.auto.AutoBalanceCommand;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.constants.OperatorConstants;
import frc.robot.commands.swervedrive.AlignToAprilTag;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;
import frc.robot.subsystems.Shooter.*;
import java.io.File;

import frc.robot.commands.*;

import org.photonvision.targeting.PhotonPipelineResult;

import swervelib.SwerveInputStream;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  // Replace with CommandPS4Controller or CommandJoystick if needed
  final         CommandXboxController driverXbox = new CommandXboxController(0);
  final         CommandXboxController operatorController = new CommandXboxController(1);
  // The robot's subsystems and commands are defined here...
  // Applies deadbands and inverts controls because joysticks
  // are back-right positive while robot
  // controls are front-left positive
  // left stick controls translation
  // right stick controls the rotational velocity 
  // buttons are quick rotation positions to different ways to face
  // WARNING: default buttons are on the same buttons as the ones defined in configureBindings
  AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(Constants.drivebase,
                                                                 () -> -MathUtil.applyDeadband(driverXbox.getLeftY(),
                                                                                               Constants.OPERATOR_CONSTANTS.leftYDeadband()),
                                                                 () -> -MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                                               Constants.OPERATOR_CONSTANTS.leftYDeadband()),
                                                                 () -> -MathUtil.applyDeadband(driverXbox.getRightX(),
                                                                                               Constants.OPERATOR_CONSTANTS.leftYDeadband()),
                                                                 driverXbox.getHID()::getYButtonPressed,
                                                                 driverXbox.getHID()::getAButtonPressed,
                                                                 driverXbox.getHID()::getXButtonPressed,
                                                                 driverXbox.getHID()::getBButtonPressed);

  /**
   * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular velocity.
   */
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(Constants.drivebase.getSwerveDrive(),
                                                                () -> driverXbox.getLeftY() * Constants.scaleFactor,
                                                                () -> driverXbox.getLeftX() * Constants.scaleFactor)
                                                            .withControllerRotationAxis(() -> -driverXbox.getRightX() * 0.6 * Constants.scaleFactor)
                                                            .deadband(Constants.OPERATOR_CONSTANTS.deadband())
                                                            .allianceRelativeControl(false);

  SwerveInputStream driveRobotOriented = SwerveInputStream.of(Constants.drivebase.getSwerveDrive(),
                                                                () -> -driverXbox.getLeftY() * Constants.scaleFactor,
                                                                () -> -driverXbox.getLeftX() * Constants.scaleFactor)
                                                            .withControllerRotationAxis(() -> -driverXbox.getRightX() * 0.6 * Constants.scaleFactor)
                                                            .deadband(Constants.OPERATOR_CONSTANTS.deadband())
                                                            .robotRelative(true)
                                                            .scaleTranslation(Constants.scaleFactor)
                                                            .allianceRelativeControl(false);
  
  
   /**
   * Clone's the angular velocity input stream and converts it to a fieldRelative input stream.
   */
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(() -> -driverXbox.getRightX(),
                                                                                             () -> -driverXbox.getRightY())
                                                           .headingWhile(true);


  // Applies deadbands and inverts controls because joysticks
  // are back-right positive while robot
  // controls are front-left positive
  // left stick controls translation
  // right stick controls the desired angle NOT angular rotation
  Command driveFieldOrientedDirectAngle = Constants.drivebase.driveFieldOriented(driveDirectAngle);
  Command driveRobotOrientedAngular = Constants.drivebase.driveRobotRelative(driveRobotOriented);

  // Applies deadbands and inverts controls because joysticks
  // are back-right positive while robot
  // controls are front-left positive
  // left stick controls translation
  // right stick controls the angular velocity of the robot
  Command driveFieldOrientedAnglularVelocity = Constants.drivebase.driveFieldOriented(driveAngularVelocity);


  Command driveSetpointGen = Constants.drivebase.driveWithSetpointGeneratorFieldRelative(driveDirectAngle);

  SwerveInputStream driveAngularVelocitySim = SwerveInputStream.of(Constants.drivebase.getSwerveDrive(),
                                                                   () -> driverXbox.getLeftY(),
                                                                   () -> driverXbox.getLeftX())
                                                               .withControllerRotationAxis(() -> -driverXbox.getRightX())
                                                               .deadband(Constants.OPERATOR_CONSTANTS.deadband())
                                                               .scaleTranslation(0.8)
                                                               .allianceRelativeControl(true);
  // Derive the heading axis with math!
  SwerveInputStream driveDirectAngleSim     = driveAngularVelocitySim.copy()
                                                                     .withControllerHeadingAxis(() -> Math.sin(
                                                                                                    driverXbox.getRawAxis(
                                                                                                        2) * Math.PI) * (Math.PI * 2),
                                                                                                () -> Math.cos(
                                                                                                    driverXbox.getRawAxis(
                                                                                                        2) * Math.PI) *
                                                                                                      (Math.PI * 2))
                                                                     .headingWhile(true);

  Command driveFieldOrientedDirectAngleSim = Constants.drivebase.driveFieldOriented(driveDirectAngleSim);
  Command driveFieldOrientedAngularVelocitySim = Constants.drivebase.driveFieldOriented(driveAngularVelocitySim);

  Command driveSetpointGenSim = Constants.drivebase.driveWithSetpointGeneratorFieldRelative(driveDirectAngleSim);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
    
    // Initialize with proper alliance orientation
    Constants.drivebase.zeroGyroWithAlliance();
    
    NamedCommands.registerCommand("UniversalRightBot", new UniversalRightBot());
    NamedCommands.registerCommand("UniversalLeftBot", new UniversalLeftBot());
    NamedCommands.registerCommand("UniversalLeftTop", new UniversalLeftTop());
    NamedCommands.registerCommand("UniversalRightTop", new UniversalRightTop());
    NamedCommands.registerCommand("WaitForCoralIntake", new WaitForCoralIntake());
    NamedCommands.registerCommand("ZeroElevator", new ZeroElevator());


  }

  

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    // (Condition) ? Return-On-True : Return-on-False
    Constants.drivebase.setDefaultCommand(!RobotBase.isSimulation() ?
                                driveFieldOrientedAnglularVelocity :
                                driveFieldOrientedAngularVelocitySim);

    if (Robot.isSimulation())
    {
      driverXbox.start().onTrue(Commands.runOnce(() -> Constants.drivebase.resetOdometry(new Pose2d(2, 2, new Rotation2d()))));
    }
    if (DriverStation.isTest())
    {
      Constants.drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity); // Overrides drive command above!

      driverXbox.b().whileTrue(Constants.drivebase.sysIdDriveMotorCommand());
      driverXbox.x().whileTrue(new AlignToAprilTag(Constants.drivebase, driveAngularVelocity));
      driverXbox.y().whileTrue(Constants.drivebase.driveToDistanceCommand(1.0, 0.2));
      driverXbox.start().onTrue((Commands.runOnce(Constants.drivebase::zeroGyro)));
      driverXbox.back().whileTrue(Constants.drivebase.centerModulesCommand());
      driverXbox.leftBumper().onTrue(Commands.none());
      driverXbox.rightBumper().onTrue(Commands.none());
    } else
    {
      // Create a default command for the shooter to always run the intake logic
      Constants.SHOOTER.setDefaultCommand(new IntakeCoral());
      Constants.LEDSystem.setDefaultCommand(new SetLED());

      // Update the scoring button to temporarily interrupt the default command
      // driverXbox.a().whileTrue(new ScoreCoral());

      // //change to arbitrary heights and press again to return down to base
      // driverXbox.rightBumper().onTrue(new MoveElevator(0.330)); //L2
      // driverXbox.leftBumper().onTrue(new MoveElevator(0.330)); //L2

      // driverXbox.x().onTrue(new MoveElevator(0));

      // driverXbox.rightTrigger().onTrue(new MoveElevator(0.69420));
      // driverXbox.leftTrigger().onTrue(new MoveElevator(0.69420));
      

      // driverXbox.back().onTrue(Commands.runOnce(Constants.drivebase::zeroGyro));
      // driverXbox.start().onTrue((Commands.runOnce(Constants.drivebase::zeroGyro)));
      // driverXbox.a().whileTrue(new One());
      // driverXbox.b().whileTrue(new Two());
      // driverXbox.x().whileTrue(new Three());
      // driverXbox.y().whileTrue(new Four());


      //driver controls
      driverXbox.leftTrigger().whileTrue(new UniversalLeftTop()); //score on the left top
      driverXbox.rightTrigger().whileTrue(new UniversalRightTop()); //score on the right top
      driverXbox.leftBumper().whileTrue(new UniversalLeftBot()); //score on the left bottom
      driverXbox.rightBumper().whileTrue(new UniversalRightBot()); //score on the right bottom
      driverXbox.x().onTrue(new MoveElevator(0)); // reset elevator
      driverXbox.a().whileTrue(Commands.run(() -> Constants.LEDSystem.setYellow(), Constants.LEDSystem)); //flash leds yellow (to get human player's attention)
      driverXbox.povUp().onTrue(Commands.runOnce(() -> Constants.scaleFactor = 0.2)); //slow mode on back left top button (or up d-pad)
      driverXbox.povUp().onFalse(Commands.runOnce(() -> Constants.scaleFactor = 1)); //slow mode on back left top button (or up d-pad)
    
      //operator controls
      operatorController.a().onTrue(new MoveElevator(0)); //operator can zero the elevator
      operatorController.b().onTrue(new MoveElevator(0.37)); //operator can lift up the elevator
      operatorController.x().whileTrue(Commands.run(() -> Constants.LEDSystem.setYellow(), Constants.LEDSystem)); //operator can flash the lights too


      driverXbox.start().whileTrue(driveRobotOrientedAngular);
      // driverXbox.leftBumper().onTrue(Commands.runOnce(() -> Constants.scaleFactor = 0.2));
      // driverXbox.leftBumper().onFalse(Commands.runOnce(() -> Constants.scaleFactor = 1));

      // driverXbox.leftTrigger().onTrue(new UniversalLeft());
      // driverXbox.rightTrigger().onTrue(new UniversalRight());


   // For tag-based accuracy test, you'll need to create this command manually with measurements
      
      // driverXbox.rightBumper().onTrue(new MoveElevator(0));
      // driverXbox.a().onTrue(new CameraCalibrationTest(Constants.drivebase, Constants.vision, driverXbox.getHID()));


      // driverXbox.y().onTrue(new DriveTest(Constants.drivebase));
      // driverXbox.a().whileTrue(new MoveElevator(0.330));
      // driverXbox.b().whileTrue(new MoveElevator(0.69));
      // driverXbox.x().whileTrue(new MoveElevator(0));
      // driverXbox.leftTrigger().whileTrue(Commands.runOnce(Constants.drivebase::lock, Constants.drivebase).repeatedly());
      // driverXbox.rightBumper().whileTrue(new IntakeCoral());
      // driverXbox.leftBumper().whileTrue(new ScoreCoral());
    }

    // Update the X button binding to use the existing driveAngularVelocity input stream
    // driverXbox.rightTrigger(0.3).whileTrue(new AlignToAprilTag(Constants.drivebase, driveAngularVelocity));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return Constants.drivebase.getAutonomousCommand("1");
  }

  public void setDriveMode()
  {
    configureBindings();
  }

  public void setMotorBrake(boolean brake)
  {
    Constants.drivebase.setMotorBrake(false);
  }

  public void zeroEverything(){
    Constants.drivebase.zeroGyro();
  }
    /**
   * Replaces the swerve module feedforward with a new SimpleMotorFeedforward object.
   *
   * @param kS the static gain of the feedforward
   * @param kV the velocity gain of the feedforward
   * @param kA the acceleration gain of the feedforward
   */

  public void setDriveFeedForward(double kS, double kV, double kA){
    Constants.drivebase.replaceSwerveModuleFeedforward(kS, kV, kA);
  }

  public void resetAndStop(){
    Constants.drivebase.drive(new Translation2d(), 0, false);
  }
  
}
