// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Elevator;
import frc.robot.utils.Conversions;
import frc.robot.Constants;
import frc.robot.Constants.ScoreLocation;

/** An example command that uses an example subsystem. */
public class CoralScore extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ScoreLocation scoreLocation;
  private final int closestID;
  /**
   * Creates a new ExampleCommand. 
   *
   * @param subsystem The subsystem used by this command.
   */
  public CoralScore(ScoreLocation scoreLocation){
    this.scoreLocation = scoreLocation;
    closestID = Constants.vision.getClosestTag();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    /**
     * set the height point of the elevator
     * change light color eventualy
     * change x/y position depending on if it's left or right
     * CURRENTLY PLACEHOLDERS FOR TESTING, WILL CHANGE TO DO REAL STUFF LATER (hopefully)
     */

    new SequentialCommandGroup(
      Constants.drivebase.driveToPose(Conversions.scoringGoalsToPose(closestID, scoreLocation)),
      Constants.elevator.setGoal(Conversions.getElevatorGoal(scoreLocation))
    ).schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    /**
    * Either reset to default pos, or just stay the same
    * Will change later
    * PLEASE
    */
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
