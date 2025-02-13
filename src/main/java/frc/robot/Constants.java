// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.sensors.TestingMotor;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;
import swervelib.math.Matter;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.OperatorConstants;

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

    public static final AprilTagFieldLayout layout = new AprilTagFieldLayout(
        tagList, 
        7.62,    // Field length (meters)
        3.6068   // Field width (meters)
    );

    // Robot Physical Properties
    public static final double ROBOT_MASS = Units.lbsToKilograms(32);
    public static final Matter CHASSIS = new Matter(
        new Translation3d(0, 0, Units.inchesToMeters(14)), 
        ROBOT_MASS
    );
    
    // Control Loop Timing
    public static final double LOOP_TIME = 0.05; // seconds
    public static final double MAX_SPEED = 4.0;  // meters per second
    public static double visionTimerOffset = 0;

    // Subsystem Instances
    public static TestingMotor testingMotor;
    public static final Elevator elevator = new Elevator();
    public static final Intake intake = new Intake();
    public static final SwerveSubsystem drivebase = new SwerveSubsystem(
        new File(Filesystem.getDeployDirectory(), "swerve/neo")
    );
    public static Vision vision;

    // Constants Records
    public static final IntakeConstants INTAKE = IntakeConstants.DEFAULT;
    public static final ElevatorConstants ELEVATOR = ElevatorConstants.DEFAULT;
    public static final DriveConstants DRIVE = DriveConstants.DEFAULT;
    public static final OperatorConstants OPERATOR = OperatorConstants.DEFAULT;

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

    //Elevator Constants Default
    public final class ElevatorConstantsDefault{

        public static double gearRatio = 10.0; // gearRatio
        public static double pulleyRadiusMeters = Units.inchesToMeters(2.0); // pulleyRadiusMeters
        public static double carriageMassKg = 6.803; // carriageMassKg
        public static double minHeightMeters = 0.0; // minHeightMeters
        public static double maxHeightMeters = 3.35; // maxHeightMeters
        public static int ticksPerRevolution = 2048; // ticksPerRevolution
        public static double distancePerTick = 2 * Math.PI * Units.inchesToMeters(2) / 2048 / 10.0; // distancePerTick
        public static double kP = 1.0; // kP
        public static double kI = 0.5; // kI
        public static double kD = 0.2; // kD
        public static double bottomSetpoint = 0.0; // bottomSetpoint
        public static double midSetpoint = 2.0; // midSetpoint
        public static double topSetpoint = 3.0; // topSetpoint
        public static int masterMotorID = 1; // masterMotorID
        public static int followerMotorID = 2; // followerMotorID
        public static double kS = 0.0; // kS
        public static double kG = 0.0; // kG
        public static double kV = 0.0; // kV
        public static double kA = 0.0; // kA
        public static double maxVelocity = 0.0; // maxVelocity
        public static double maxAcceleration = 0.0; // maxAcceleration
        public static double rampRate = 0.0; // rampRate
        public static int SLOT_A = 0;
        public static int SLOT_B = 1;
        public static int SLOT_I = 3;
        public static final Encoder encoder = new Encoder(SLOT_A, SLOT_B, SLOT_I);
        

    }
}
