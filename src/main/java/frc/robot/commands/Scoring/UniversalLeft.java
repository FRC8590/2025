// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.MoveElevator;
import frc.robot.constants.ScoringConstants;

import java.security.AllPermission;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

/** Command to score a coral by ejecting it */
public class UniversalLeft extends SequentialCommandGroup {

  // Define the AprilTag IDs we're interested in
  private static final int[] APRILTAG_IDS = {17, 18, 19, 20, 21, 22};
  
  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public UniversalLeft() {
    // Find the closest AprilTag based on current robot position
    int closestTag = findClosestAprilTag();
    
    addCommands(
        Constants.drivebase.driveToPose(getScoringLocation(closestTag))
    );

    addRequirements(Constants.drivebase);
    addRequirements(Constants.SHOOTER);
    addRequirements(Constants.ELEVATOR);
  }

  /**
   * Finds the closest AprilTag to the robot's current position
   * @return The ID of the closest AprilTag
   */
  private int findClosestAprilTag() {
    Pose2d currentPose = Constants.drivebase.getPose();
    Translation2d currentPosition = currentPose.getTranslation();
    
    int closestTag = APRILTAG_IDS[0]; // Default to first tag
    double minDistance = Double.MAX_VALUE;
    
    for (int tagId : APRILTAG_IDS) {
      Translation2d tagPosition = getScoringLocation(tagId).getTranslation();
      if (tagPosition != null) {
        double distance = currentPosition.getDistance(tagPosition);
        if (distance < minDistance) {
          minDistance = distance;
          closestTag = tagId;
        }
      }
    }
    
    // Log the chosen tag to SmartDashboard for debugging
    SmartDashboard.putNumber("Selected AprilTag", closestTag);
    SmartDashboard.putNumber("Distance to Tag", minDistance);
    
    return closestTag;
  }


  private Pose2d getScoringLocation(int apriltag){
    switch (apriltag){
      case 19:
        return Constants.SCORING_CONSTANTS.locationTwo();
      case 20:
        return Constants.SCORING_CONSTANTS.locationFour();
      default:
        return new Pose2d();
    }

  }
}

