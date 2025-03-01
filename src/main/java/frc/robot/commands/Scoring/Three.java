// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.MoveElevator;

/** Command to score a coral by ejecting it */
public class Three extends SequentialCommandGroup {

  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public 
  Three() {
    addCommands(
        // Constants.drivebase.driveToPose(new Pose2d(new Translation2d(4.92,5.9), new Rotation2d(Units.degreesToRadians(-107)))),        // Constants.drivebase.driveToPose(new Pose2d(new Translation2d(4.92,5.9), new Rotation2d(Units.degreesToRadians(-107)))),
        // Constants.drivebase.driveToPose(new Pose2d(new Translation2d(3.42,5.49), new Rotation2d(Units.degreesToRadians(-47)))),
        // Constants.drivebase.driveToDistanceCommand(Units.feetToMeters(2.56), 0.8),
        Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.locationThree()),
        new MoveElevator(0.37),
        new ScoreCoral()
      //TRY DISSTAMCE SENSOR
    );

    addRequirements(Constants.drivebase);
    addRequirements(Constants.SHOOTER);
    addRequirements(Constants.ELEVATOR);

  }
}

