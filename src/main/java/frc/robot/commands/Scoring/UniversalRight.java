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
import java.util.HashMap;
import java.util.Map;

/** Command to score a coral by ejecting it */
public class UniversalRight extends SequentialCommandGroup {

  // Define the AprilTag IDs we're interested in
  private static final int[] APRILTAG_IDS = {17, 18, 19, 20, 21, 22};
  
  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public UniversalRight() {
    // Find the closest AprilTag based on current robot position
    int closestTag = findClosestAprilTag();
    
    addCommands(
        Constants.drivebase.driveToPose(new Pose2d(getApriltagTranslation2d(closestTag), getApriltagRotation2d(closestTag)))
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
      Translation2d tagPosition = getApriltagTranslation2d(tagId);
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

  private Rotation2d getApriltagRotation2d(int apriltag) {
    switch (apriltag) {
      case 17:
        return new Rotation2d(Units.degreesToRadians(60));
      case 18:
        return new Rotation2d(Units.degreesToRadians(0));
      case 19:
        return new Rotation2d(Units.degreesToRadians(-55));
      case 20:
        return new Rotation2d(Units.degreesToRadians(-117));
      case 21:
        return new Rotation2d(Units.degreesToRadians(180));
      case 22:
        return new Rotation2d(Units.degreesToRadians(120));
      default:
        return new Rotation2d(); // Default to 0 degrees
    }
  }

  public Translation2d getApriltagTranslation2d(int apriltag) {
    switch (apriltag) {
      case 17:
        return new Translation2d(3.648, 2.444);
      case 18:
        return new Translation2d(2.789, 4.000);
      case 19:
        return new Translation2d(3.86, 5.11);
      case 20:
        return new Translation2d(5.13, 5.04);
      case 21:
        return new Translation2d(6.191, 4.000);
      case 22:
        return new Translation2d(5.353, 2.444);
      default:
        return null;
    }
  }
}

