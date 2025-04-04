// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.ScoreLocation;

/** Start the algae remover */
public class MoreActiveRemover extends SequentialCommandGroup {

  /**
   * Sets algae remover to its down state and activates it
   */
  public MoreActiveRemover() {
    addCommands(
        Constants.ALGAE_REMOVER.reachGoalMoreDownCommand()
    );

    addRequirements(Constants.ALGAE_REMOVER);
  }
}