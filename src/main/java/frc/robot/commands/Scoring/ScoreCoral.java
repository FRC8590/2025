// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;

/** Command to score a coral by ejecting it */
public class ScoreCoral extends SequentialCommandGroup {

  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public ScoreCoral() {
    addCommands(
      new ParallelCommandGroup(
        Constants.SHOOTER.scoreCoral()  // Eject coral for 5 seconds
      )
    );

    addRequirements(Constants.SHOOTER);
  }
}