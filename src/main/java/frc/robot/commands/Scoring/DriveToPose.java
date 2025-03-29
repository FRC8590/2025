package frc.robot.commands.Scoring;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.*;
import frc.robot.*;

import java.util.Optional;

import org.photonvision.targeting.PhotonPipelineResult;

/**
 * High-precision drive to pose command using AprilTag vision and ProfiledPIDController
 * for smooth motion with controlled acceleration and sub-centimeter accuracy.
 */
public class DriveToPose extends Command {

  private final SwerveSubsystem swerve;
  private final Pose2d targetPose;
  private Vision.Cameras specifiedCamera;
  private int targetTagId = -1;
  private boolean useVision = true;

  // Higher P gain for precision, with controlled acceleration in the constraints
  private final ProfiledPIDController xController =
      new ProfiledPIDController(1.0, 0.0, 0.1, new TrapezoidProfile.Constraints(3.0, 2.0));
  private final ProfiledPIDController yController =
      new ProfiledPIDController(1.0, 0.0, 0.1, new TrapezoidProfile.Constraints(3.0, 2.0));
  private final ProfiledPIDController thetaController =
      new ProfiledPIDController(2.0, 0.0, 0.2, new TrapezoidProfile.Constraints(4.0, 3.0));

  // Precision tolerance - 0.5 cm (0.005 m) for position, 0.5 degrees for rotation
  private static final double POSITION_TOLERANCE_METERS = 0.005;
  private static final double ROTATION_TOLERANCE_DEGREES = 0.5;

  /**
   * Creates a new precision drive-to-pose command using vision from specified camera for pose estimation.
   *
   * @param targetPose The target Pose2d to drive to with high precision
   * @param camera The camera to use for vision-based pose estimation (LEFT_CAM or RIGHT_CAM)
   * @param tagId The specific tag ID to use for vision-based pose estimation
   */
  public DriveToPose(Pose2d targetPose, Vision.Cameras camera, int tagId) {
    this.swerve = Constants.drivebase;
    this.targetPose = targetPose;
    this.specifiedCamera = camera;
    this.targetTagId = tagId;
    this.useVision = true;

    // Set tolerances for high precision
    xController.setTolerance(POSITION_TOLERANCE_METERS);
    yController.setTolerance(POSITION_TOLERANCE_METERS);
    thetaController.setTolerance(Units.degreesToRadians(ROTATION_TOLERANCE_DEGREES));
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    addRequirements(swerve);
  }


  @Override
  public void initialize() {
    if (useVision) {
      // Validate if the specified tag is visible
      validateVisionTarget();
    }
    
    // Initialize with the current position
    Pose2d currentPose = getCurrentPose();
    
    xController.reset(currentPose.getX());
    yController.reset(currentPose.getY());
    thetaController.reset(currentPose.getRotation().getRadians());
    
  }

  /**
   * Validate if the specified tag is visible from the specified camera
   */
  private void validateVisionTarget() {
    if (Constants.vision == null || targetTagId < 0) {
      useVision = false;
      return;
    }
    
    // Check if the specified tag is visible from the specified camera
    boolean tagVisible = false;
    for (PhotonPipelineResult result : specifiedCamera.resultsList) {
      if (result.hasTargets()) {
        for (var target : result.getTargets()) {
          if (target.getFiducialId() == targetTagId) {
            tagVisible = true;
            break;
          }
        }
      }
    }
    
    // If the tag is not visible, fall back to swerve odometry
    if (!tagVisible) {
      useVision = false;
    }
  }

  /**
   * Get the current pose either from vision or fallback to swerve odometry
   */
  private Pose2d getCurrentPose() {
    if (!useVision || Constants.vision == null || targetTagId < 0) {
      return swerve.getPose();
    }
    
    // Try to get pose from vision using the specified camera and tag
    Optional<Pose2d> visionPose = Constants.vision.getSingleTagPoseEstimate(targetTagId, specifiedCamera);
    
    // Use vision pose if available, otherwise fall back to swerve odometry
    return visionPose.orElse(swerve.getPose());
  }

  @Override
  public void execute() {
    System.out.println("workinggg");
    // Check if we still have visual on the tag (if using vision)
    if (useVision) {
      boolean tagVisible = Vision.seesNumber(targetTagId);
      if (!tagVisible) {
        // If we've lost the tag, try again to see if it becomes visible
        validateVisionTarget();
      }
    }
    
    // Log starting and target positions
    SmartDashboard.putNumber("DriveToPose/TargetX", targetPose.getX());
    SmartDashboard.putNumber("DriveToPose/TargetY", targetPose.getY());
    SmartDashboard.putNumber("DriveToPose/TargetAngle", targetPose.getRotation().getDegrees());
    
    if (useVision) {
      SmartDashboard.putBoolean("DriveToPose/UsingVision", true);
      SmartDashboard.putNumber("DriveToPose/TagID", targetTagId);
    }
    
    // Get current pose (either from vision or odometry)
    Pose2d currentPose = getCurrentPose();

    // Calculate velocities using the profiled PID controllers
    double xVelocity = xController.calculate(currentPose.getX(), targetPose.getX());
    double yVelocity = yController.calculate(currentPose.getY(), targetPose.getY());
    double thetaVelocity = thetaController.calculate(
        currentPose.getRotation().getRadians(), 
        targetPose.getRotation().getRadians());



    // Add to dashboard for tuning and debugging
    SmartDashboard.putNumber("DriveToPose/XError", currentPose.getX());
    SmartDashboard.putNumber("DriveToPose/YError", currentPose.getY());
    SmartDashboard.putNumber("DriveToPose/ThetaError", 
        Units.radiansToDegrees(targetPose.getRotation().getRadians() - currentPose.getRotation().getRadians()));
    SmartDashboard.putBoolean("DriveToPose/VisionAvailable", useVision && targetTagId != -1);

    // Stop if we've reached the target
    if (atGoal()) {
      swerve.drive(new ChassisSpeeds(0, 0, 0));
      return;
    }

    SmartDashboard.putNumber("DriveToPose/xVelocity", xVelocity);
    SmartDashboard.putNumber("DriveToPose/yVelocity", yVelocity);
    SmartDashboard.putNumber("DriveToPose/thetaVelocity", thetaVelocity);


    double xDelta = targetPose.getX() - currentPose.getX();
    double yDelta = targetPose.getY() - currentPose.getY();
    double thetaDelta = targetPose.getRotation().getDegrees() - currentPose.getRotation().getRadians();

    // Apply the calculated velocities as field-relative speeds
    swerve.driveFieldOriented(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            -xDelta, -yDelta, thetaVelocity, currentPose.getRotation()));
  }

  @Override
  public void end(boolean interrupted) {
    // Ensure the robot comes to a complete stop
    swerve.drive(new ChassisSpeeds(0, 0, 0));
    SmartDashboard.putBoolean("DriveToPose/AtGoal", true);
    
    if (interrupted) {
      SmartDashboard.putBoolean("DriveToPose/Interrupted", true);
    }
  }

  /**
   * Checks if the robot has reached the target pose within tolerance.
   *
   * @return true if at goal position and orientation
   */
  public boolean atGoal() {
    return xController.atGoal() && 
           yController.atGoal() && 
           thetaController.atGoal();
  }

  @Override
  public boolean isFinished() {
    // Command completes when we're at the goal pose
    return atGoal();
  }
} 