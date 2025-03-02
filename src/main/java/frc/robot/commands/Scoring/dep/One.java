// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring.dep;

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
import frc.robot.commands.Scoring.ScoreCoral;
import frc.robot.commands.swervedrive.*;

/** Command to score a coral by ejecting it */
public class One extends SequentialCommandGroup {

  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public 
  One() {
    addCommands(
        new ParallelCommandGroup(
            Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.locationOne()),
            new MoveElevator(0.37)
          ),
        new ScoreCoral()
    );

    addRequirements(Constants.drivebase);
    addRequirements(Constants.SHOOTER);
    addRequirements(Constants.ELEVATOR);

  }
}

