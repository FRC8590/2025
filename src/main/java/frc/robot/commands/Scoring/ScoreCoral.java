// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

/** Ejects coral from intake for 5 seconds */
public class ScoreCoral extends SequentialCommandGroup {

  /**
   * Ejects coral from intake for 5 seconds
   */
  public ScoreCoral() {
    addCommands(
      new ParallelCommandGroup(
        new WaitCommand(1.25),
        Constants.SHOOTER.scoreCoral()  // Eject coral for 5 seconds
      )
    );

    addRequirements(Constants.SHOOTER);
  }
}