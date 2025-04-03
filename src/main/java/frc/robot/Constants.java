// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import au.grapplerobotics.LaserCan;

import java.io.File;
import java.util.ArrayList;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import swervelib.math.Matter;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.LEDConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.AlgaeConstants;
import frc.robot.constants.AlgaeRemoverConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.OperatorConstants;
import frc.robot.constants.ScoringConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.AlgaeRemover;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

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
    private Constants() {} // Prevent instantiation

    // Vision & Field Constants
    private static final AprilTag tag1 = new AprilTag(
        1, 
        new Pose3d(
            0,
            Units.inchesToMeters(13.5),
            Units.inchesToMeters(9),
            new Rotation3d(new Rotation2d(0))
        )
    );
    private static final List<AprilTag> tagList = new ArrayList<AprilTag>() {{
        add(tag1);
    }};

    // public static final AprilTagFieldLayout layout = new AprilTagFieldLayout(
    //     tagList, 
    //     7.62,    // Field length (meters)
    //     3.6068   // Field width (meters)
    // );

    public static final AprilTagFieldLayout layout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeAndyMark);

    // Robot Physical Properties
    public static final double ROBOT_MASS = Units.lbsToKilograms(85);
    public static final Matter CHASSIS = new Matter(
        new Translation3d(0, 0, Units.inchesToMeters(14)), 
        ROBOT_MASS
    );
    
    // Control Loop Timing
    public static final double LOOP_TIME = 0.05; // seconds
    public static final double MAX_SPEED = 5.0;  // meters per second
    public static double visionTimerOffset = 0;

    // Subsystem Instances
    public static final SwerveSubsystem drivebase = new SwerveSubsystem(
        new File(Filesystem.getDeployDirectory(), "swerve/neo")
    );
    public static Vision vision;



    public static double scaleFactor = 1;

    public static int[] SCORING_IDS = {17, 18, 19, 20, 21, 22, 6, 7, 8, 9, 10, 11};

    // Constants Records
    public static final IntakeConstants INTAKE_CONSTANTS = IntakeConstants.DEFAULT;
    public static final ElevatorConstants ELEVATOR_CONSTANTS = ElevatorConstants.DEFAULT;
    public static final DriveConstants DRIVE_CONSTANTS = DriveConstants.DEFAULT;
    public static final OperatorConstants OPERATOR_CONSTANTS = OperatorConstants.DEFAULT;
    public static final ShooterConstants SHOOTER_CONSTANTS = ShooterConstants.DEFAULT;
    public static final ScoringConstants SCORING_CONSTANTS = ScoringConstants.DEFAULT;
    public static final AlgaeConstants ALGAE_CONSTANTS = AlgaeConstants.DEFAULT;
    public static final AlgaeRemoverConstants ALGAE_REMOVER_CONSTANTS = AlgaeRemoverConstants.DEFAULT;
    public static final LEDConstants LED_CONSTANTS = LEDConstants.DEFAULT;

    public static double distancePerRotation = 1/14.36; //meters per rotations

    public static final LaserCan laserCan = new LaserCan(8);
    public static int lockTimer = 0;


    // Enums
    public enum ElevatorState {
        ZEROED, 
        SETPOINT, 
        DISABLED
    }

    public enum ScoreLocation {
        LEFT2, 
        LEFT3, 
        RIGHT2, 
        RIGHT3
    }


    public static final double minHeightMeters = 0.0;
    public static final double maxHeightMeters = 3.35;
    public static final int masterMotorID = 9;
    public static final int followerMotorID = 10;

    public final class feedforward{
        public static final double kS = 0;
        public static final double kG = 0.02;
        public static final double kV = 0;
        public static final double kA = 0.0;
    }

    public final class pid{
        public static final double kP = 3.8;
        public static final double kI = 0;
        public static final double kD = 0.04;
    }   

    public static double maxVelocity = 0.0;
    public static double maxAcceleration = 0.0;

    public static double rampRate = 0.01;



    public static final Elevator ELEVATOR = new Elevator();
    public static final Shooter SHOOTER = new Shooter();
    public static final AlgaeRemover ALGAE_REMOVER = new AlgaeRemover();
    public static final LEDSubsystem LEDSystem = new LEDSubsystem();

}
