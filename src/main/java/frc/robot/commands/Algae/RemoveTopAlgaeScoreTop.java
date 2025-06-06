// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Algae;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.ActiveRemover;
import frc.robot.commands.InactiveRemover;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.Scoring.ScoreCoral;
import frc.robot.constants.ScoringConstants;

import java.io.Console;
import java.security.AllPermission;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

/** Remove top algae, and scores one coral on L3 */
public class RemoveTopAlgaeScoreTop extends SequentialCommandGroup {

  // Define the AprilTag IDs we're interested in
  
  /**
  * Remove top algae, and scores one coral on L3
   */
  public RemoveTopAlgaeScoreTop() {

    
    
    addCommands(
      new ActiveRemover(),
       moveToScore.withTimeout(2),
       new MoveElevator(0.7),
       new ParallelCommandGroup(
        new ScoreCoral(),
        new InactiveRemover()  
      ));
    addRequirements(Constants.drivebase);

    
    addRequirements(Constants.SHOOTER);
    addRequirements(Constants.ELEVATOR);
  }



  private int getClosestTag() {
    return Constants.drivebase.findClosestAprilTag();
  }

  // An example selectcommand.  Will select from the three commands based on the value returned
  // by the selector method at runtime.  Note that selectcommand works on Object(), so the
  // selector does not have to be an enum; it could be any desired type (string, integer,
  // boolean, double...)
  private final Command moveToScore =
      new SelectCommand<>(
          // Maps selector values to commands
          Map.ofEntries(
              Map.entry(17, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left17())),
              Map.entry(18, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left18())),
              Map.entry(19, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left19())),
              Map.entry(20, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left20())),
              Map.entry(21, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left21())),
              Map.entry(22, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left22())),
              Map.entry(6, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left6())),
              Map.entry(7, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left7())),
              Map.entry(8, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left8())),
              Map.entry(9, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left9())),
              Map.entry(10, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left10())),
              Map.entry(11, Constants.drivebase.driveToPose(Constants.SCORING_CONSTANTS.left11()))),
          this::getClosestTag);

  private final Command moveBack =
      new SelectCommand<>(
          // Maps selector values to commands
          Map.ofEntries(
              Map.entry(17, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left17())),
              Map.entry(18, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left18())),
              Map.entry(19, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left19())),
              Map.entry(20, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left20())),
              Map.entry(21, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left21())),
              Map.entry(22, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left22())),
              Map.entry(6, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left6())),
              Map.entry(7, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left7())),
              Map.entry(8, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left8())),
              Map.entry(9, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left9())),
              Map.entry(10, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left10())),
              Map.entry(11, Constants.drivebase.driveToPose(Constants.ALGAE_CONSTANTS.left11()))),
          this::getClosestTag);
    
}
