// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.MoveElevator;

/**  Attempts to score a coral on L1. */
public class TroughyCoral extends SequentialCommandGroup {

  /**
   * Attempts to score a coral on L1.
   * Move selevator to 0.22, and ejects coral before moving elevator back to 0.
   */
  public TroughyCoral() {
    addCommands(
      Constants.SHOOTER.troughCoral() // Eject coral for 5 seconds
    );

    addRequirements(Constants.SHOOTER);
  }
}