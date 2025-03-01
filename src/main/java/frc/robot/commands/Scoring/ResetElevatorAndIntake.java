// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.StopShooter;

/** Command to score a coral by ejecting it */
public class ResetElevatorAndIntake extends ParallelCommandGroup {

  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public ResetElevatorAndIntake() {
    addCommands(
      new MoveElevator(0)
    );

    addRequirements(Constants.SHOOTER);
    addRequirements(Constants.ELEVATOR);

  }
}