// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;
import java.util.ArrayList;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.TestingMotor;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
//36.75 inches, 26.5 inches
//300 inches
  private static AprilTag tag1 = new AprilTag(1, new Pose3d(0,Units.inchesToMeters(13.5),Units.inchesToMeters(9),new Rotation3d(new Rotation2d(0))));
  // private static AprilTag tag2 = new AprilTag(2, new Pose3d(1,1,1,new Rotation3d(new Rotation2d(0))));
  // private static AprilTag tag3 = new AprilTag(3, new Pose3d(1,1,1,new Rotation3d(new Rotation2d(0))));
  // private static AprilTag tag4 = new AprilTag(4, new Pose3d(1,1,1,new Rotation3d(new Rotation2d(0))));
  private static List<AprilTag> tagList = new ArrayList<AprilTag>() {{
    add(tag1);
  }};

  public static final AprilTagFieldLayout layout = new AprilTagFieldLayout(tagList, 7.62,3.6068);
  public static final double ROBOT_MASS = 10; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(14)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.05; //s, 20ms + 110ms sprk max velocity lag
  public static final double MAX_SPEED  = 4;

  public static double visionTimerOffset = 0;

  public static TestingMotor testingMotor;
  // Maximum speed of the robot in meters per second, used to limit acceleration.

//  public static final class AutonConstants
//  {
//
//    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
//    public static final PIDConstants ANGLE_PID       = new PIDConstants(0.4, 0, 0.01);
//  }

  public static final class DrivebaseConstants
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants
  {

    // Joystick Deadband
    public static final double DEADBAND        = 0.1;
    public static final double LEFT_Y_DEADBAND = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
  }
}
