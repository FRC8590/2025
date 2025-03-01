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

/** An example command that uses an example subsystem. */
public class StopShooter extends SequentialCommandGroup {

  /**
   * Creates a new CoralScore command that drives to the scoring position and moves the elevator.
   *
   * @param scoreLocation The desired scoring location (LEFT, CENTER, RIGHT)
   */
  public StopShooter() {
    addCommands(
      new ParallelCommandGroup(
        Constants.SHOOTER.stop()  // Set to specific height in meters
      )
    );

    addRequirements(Constants.SHOOTER);
  }

  
}