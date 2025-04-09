// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;

/** Pulls the robot up untill the gyro says it is 90 degrees;
 *  Also changes the colors of the LEDs as it turns.
 */
public class DeepClimbAutoClimbUp extends SequentialCommandGroup {
  /** Pulls the robot up untill the gyro says it is 90 degrees;
   * Also changes the colors of the LEDs as it turns.
   */
  public DeepClimbAutoClimbUp() {
    addCommands(
      new ParallelCommandGroup(
        Constants.DEEP_CLIMB.climbUp()

      ).until(null) // Runs the commands in the ParallelCommandGroup until the robot is 90 degrees
    );

    addRequirements(Constants.DEEP_CLIMB);
  }
}
