// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.StopShooter;

/** Sets elevator position to 0 and stops intake motors */
public class ResetElevatorAndIntake extends ParallelCommandGroup {

  /**
   * Sets elevator position to 0 and stops intake motors
   */
  public ResetElevatorAndIntake() {
    addCommands(
        new MoveElevator(0)
   );

    addRequirements(Constants.ELEVATOR);

  }
}