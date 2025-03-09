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
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.*;
import java.io.File;

import frc.robot.commands.*;
import frc.robot.commands.Algae.RemoveBotAlgae;
import frc.robot.commands.Algae.RemoveTopAlgae;
import frc.robot.commands.Algae.StateUpdateAlgae;
import frc.robot.commands.Auto.AutoLeftBotRemoveAlgae;
import frc.robot.commands.Auto.AutoLeftTopRemoveAlgae;
import frc.robot.commands.Auto.AutoRightBotRemoveAlgae;
import frc.robot.commands.Auto.AutoRightTopRemoveAlgae;
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

  //auto list object
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  //red or blue side object

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
                                                                () -> driverXbox.getLeftY() * getSide() * Constants.scaleFactor,
                                                                () -> driverXbox.getLeftX() * getSide() * Constants.scaleFactor)
                                                            .withControllerRotationAxis(() -> -driverXbox.getRightX() * 0.6 * Constants.scaleFactor)
                                                            .deadband(Constants.OPERATOR_CONSTANTS.deadband())
                                                            .robotRelative(false)
                                                            .allianceRelativeControl(false);
  
  SwerveInputStream driveRobotOriented = SwerveInputStream.of(Constants.drivebase.getSwerveDrive(),
                                                                () -> -driverXbox.getLeftY() * Constants.scaleFactor,
                                                                () -> -driverXbox.getLeftX() * Constants.scaleFactor)
                                                            .withControllerRotationAxis(() -> -driverXbox.getRightX() * 0.6 * Constants.scaleFactor)
                                                            .deadband(Constants.OPERATOR_CONSTANTS.deadband())
                                                            .robotRelative(true)
                                                            .scaleTranslation(Constants.scaleFactor);  
  
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
                                                                   () -> driverXbox.getLeftY() * getSide(), //getSide will invert if on Red side
                                                                   () -> driverXbox.getLeftX() * getSide())
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

    //set up the shuffleboard tab
    Shuffleboard.getTab("Autonomous")
        .add("Auto Selector", m_chooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser)
        .withPosition(0, 0)
        .withSize(2, 1);
        

    Shuffleboard.getTab("Autonomous")
        .add("On Red Side?", true)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .getEntry();

    //set up auto and add options
    m_chooser.setDefaultOption("21", "21");
    m_chooser.addOption("1", "1");
    m_chooser.addOption("2", "2");
    m_chooser.addOption("3", "3");
    m_chooser.addOption("4", "4");
    m_chooser.addOption("5", "5");
    m_chooser.addOption("6", "6");
    m_chooser.addOption("7", "7");
    m_chooser.addOption("8", "8");
    m_chooser.addOption("9", "9");
    m_chooser.addOption("10", "10");
    m_chooser.addOption("11", "11");
    m_chooser.addOption("12", "12");
    m_chooser.addOption("13", "13");
    m_chooser.addOption("14", "14");
    m_chooser.addOption("15", "15");
    m_chooser.addOption("16", "16");
    m_chooser.addOption("17", "17");
    m_chooser.addOption("18", "18");
    m_chooser.addOption("19", "19");
    m_chooser.addOption("20", "20");
    m_chooser.addOption("22", "22");
    m_chooser.addOption("23", "23");
    m_chooser.addOption("24", "24");
    m_chooser.addOption("25", "25");
    m_chooser.addOption("26", "26");
    m_chooser.addOption("27", "27");
    m_chooser.addOption("28", "28");
    m_chooser.addOption("29", "29");
    m_chooser.addOption("30", "30");


    
    SmartDashboard.putData("Auto choices", m_chooser);
    
  
    
    // Initialize with proper alliance orientation
    Constants.drivebase.zeroGyroWithAlliance();
    
    NamedCommands.registerCommand("UniversalRightBot", new UniversalRightBot());
    NamedCommands.registerCommand("UniversalLeftBot", new UniversalLeftBot());
    NamedCommands.registerCommand("UniversalLeftTop", new UniversalLeftTop());
    NamedCommands.registerCommand("UniversalRightTop", new UniversalRightTop());

    NamedCommands.registerCommand("RemoveBotAlgae", new RemoveBotAlgae());
    NamedCommands.registerCommand("RemoveTopAlgae", new RemoveTopAlgae());

    NamedCommands.registerCommand("UniversalLeftBotRemoveAlgae", new AutoLeftBotRemoveAlgae());
    NamedCommands.registerCommand("UniversalRightTopRemoveAlgae", new AutoRightTopRemoveAlgae());
    NamedCommands.registerCommand("UniversalRightBotRemoveAlgae", new AutoRightBotRemoveAlgae());
    NamedCommands.registerCommand("UniversalLeftTopRemoveAlgae", new AutoLeftTopRemoveAlgae());

    NamedCommands.registerCommand("WaitForCoralIntake", new WaitForCoralIntake());
    NamedCommands.registerCommand("ZeroElevator", new ZeroElevator());


  }

  public int getSide(){

    if(DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red){
      return 1;
    }
    else{
      return -1;
    }
    

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
    } else
    {
      // Create a default command for the shooter to always run the intake logic
      Constants.SHOOTER.setDefaultCommand(new IntakeCoral());
      Constants.LEDSystem.setDefaultCommand(new SetLED());
      Constants.ALGAE_REMOVER.setDefaultCommand(Constants.ALGAE_REMOVER.toggleCommand());

      
      driverXbox.povUp().whileTrue(new UniversalLeftTop()); //score on the left top
      driverXbox.povLeft().whileTrue(new UniversalRightTop()); //score on the right top
      driverXbox.povDown().whileTrue(new UniversalLeftBot()); //score on the left bottom
      driverXbox.povRight().whileTrue(new UniversalRightBot()); //score on the right bottom

      driverXbox.back().onTrue(Commands.runOnce(()-> Constants.drivebase.zeroGyroWithAlliance()));
      driverXbox.leftTrigger().whileTrue(new ScoreCoral());
      driverXbox.rightTrigger().whileTrue(Commands.run(() -> Constants.LEDSystem.setYellow(), Constants.LEDSystem)); //flash leds yellow (to get human player's attention)

      driverXbox.rightBumper().onTrue(new MoveElevator(0));
      driverXbox.leftBumper().whileTrue(Commands.run( () -> Constants.ALGAE_REMOVER.isExtended = true));
      driverXbox.leftBumper().whileFalse(Commands.run( () -> Constants.ALGAE_REMOVER.isExtended = false));

      driverXbox.b().whileTrue(new ScoreCoral());
      driverXbox.a().whileTrue(new StopShooter());
      driverXbox.y().whileTrue(driveRobotOrientedAngular);


      operatorController.rightBumper().whileTrue(new ScoreCoral());
      operatorController.leftBumper().whileTrue(new StopShooter());
      operatorController.leftTrigger().whileTrue(new MoveElevator(Constants.ELEVATOR.getElevatorHeightEncoder() - 0.1));
      operatorController.rightTrigger().whileTrue(new MoveElevator(Constants.ELEVATOR.getElevatorHeightEncoder() + 0.1));

      
  }
}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    String selectedAuto = m_chooser.getSelected();
    return Constants.drivebase.getAutonomousCommand(selectedAuto);

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
