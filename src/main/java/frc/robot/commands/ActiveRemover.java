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

/** Command to run the coral intake system */
public class ActiveRemover extends SequentialCommandGroup {

  /**
   * Creates a new IntakeCoral command that runs the intake system
   */
  public ActiveRemover() {
    addCommands(
        Constants.ALGAE_REMOVER.reachGoalDownCommand().withTimeout(3)
    );

    addRequirements(Constants.ALGAE_REMOVER);
  }
}