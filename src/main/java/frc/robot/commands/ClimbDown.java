// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;

/**
 * Make deepclimb motors move the robot down
 */
public class ClimbDown extends SequentialCommandGroup {
  /** Creates a new ClimbDown. */
  public ClimbDown() {
    addCommands(
      new ParallelCommandGroup(
        Constants.DEEP_CLIMB.climbDown()  // Set motors to move robot down
      )
    );

    addRequirements(Constants.DEEP_CLIMB);
  }
}
