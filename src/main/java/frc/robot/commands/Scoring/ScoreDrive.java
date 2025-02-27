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

/** Command to score a coral by ejecting it */
public class ScoreDrive extends SequentialCommandGroup {

  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public 
  ScoreDrive() {
    addCommands(
      new SequentialCommandGroup(
          Constants.drivebase.driveToPose(new Pose2d(new Translation2d(4.95,5.88), new Rotation2d(Units.degreesToRadians(-108)))),
          Constants.drivebase.driveToDistanceCommand(Units.feetToMeters(2.1), 0.5)
      )
    );

    addRequirements(Constants.drivebase);

  }
}

