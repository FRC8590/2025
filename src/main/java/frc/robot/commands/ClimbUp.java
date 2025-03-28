// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;

/**
 * Make deepclimb motors pull the robot up
 */
public class ClimbUp extends SequentialCommandGroup {
  /** Creates a new ClimbUp. */
  public ClimbUp() {
    addCommands(
      new ParallelCommandGroup(
        Constants.DEEP_CLIMB.climbUp()  // Set motors to move robot up
      )
    );

    addRequirements(Constants.DEEP_CLIMB);
  }
}
