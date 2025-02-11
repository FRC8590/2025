// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.ScoreLocation;
import frc.robot.utils.Conversions;

/** An example command that uses an example subsystem. */
public class Scoring extends SequentialCommandGroup {
  private final ScoreLocation scoreLocation;
  private final int closestID;

  /**
   * Creates a new CoralScore command that drives to the scoring position and moves the elevator.
   *
   * @param scoreLocation The desired scoring location (LEFT, CENTER, RIGHT)
   */
  public Scoring(ScoreLocation scoreLocation) {
    this.scoreLocation = scoreLocation;
    this.closestID = Constants.vision.getClosestTag();

    if (closestID < 0) {
      // Handle error case
      addRequirements(Constants.drivebase, Constants.elevator);
      return;
    }

    addCommands(
      // Drive to position and move elevator - using deadline() to end when drive is complete
      new ParallelDeadlineGroup(
        Constants.drivebase.driveToPose(Conversions.scoringGoalsToPose(closestID, scoreLocation)),
        Constants.elevator.setGoal(Conversions.getElevatorGoal(scoreLocation))
        // Constants.led.setScoring()
      ),
        
      // Score game piece
      new WaitCommand(0.5),
      Constants.intake.ejectCommand(),
      
      // Return to safe position - using deadline() again
      new ParallelDeadlineGroup(
        Constants.drivebase.driveToDistanceCommand(-0.5, 1),
        Constants.elevator.setGoal(Constants.ELEVATOR.bottomSetpoint())
      )
      
      // Reset LED
      // new InstantCommand(() -> Constants.led.setDefault())
    );

    addRequirements(Constants.drivebase, Constants.elevator, Constants.intake);
  }
}
