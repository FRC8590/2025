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

/** Intakes coral*/
public class IntakeCoral extends SequentialCommandGroup {

  /**
   * Activates coral intake
   */
  public IntakeCoral() {
    addCommands(
      new ParallelCommandGroup(
        Constants.SHOOTER.intakeCoral()
      )
    );

    addRequirements(Constants.SHOOTER);
  }
}