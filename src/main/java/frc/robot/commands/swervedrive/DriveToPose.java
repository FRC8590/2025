package frc.robot.commands.swervedrive;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.*;
import frc.robot.*;

public class DriveToPose extends Command {

  private final SwerveSubsystem swerve;
  private Pose2d desiredPose;

  private final ProfiledPIDController xController =
      new ProfiledPIDController(4, 0.0, 0.0, new TrapezoidProfile.Constraints(4.0, 5.0));
  private final ProfiledPIDController yController =
      new ProfiledPIDController(4, 0.0, 0.0, new TrapezoidProfile.Constraints(4.0, 5.0));
  private final ProfiledPIDController thetaController =
      new ProfiledPIDController(3, 0.0, 0.0, new TrapezoidProfile.Constraints(3.0, 3.0));

  public DriveToPose(Pose2d pose) {
    swerve = Constants.drivebase;
    this.desiredPose = pose;

    xController.setTolerance(0.05);
    yController.setTolerance(0.05);
    thetaController.setTolerance(Units.degreesToRadians(1.0));
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    addRequirements(swerve);
  }

  @Override
  public void initialize() {
    var currPose = swerve.getPose();
    xController.reset(currPose.getX());
    yController.reset(currPose.getY());
    thetaController.reset(currPose.getRotation().getRadians());
  }

  @Override
  public void execute() {
    var currPose = swerve.getPose();
    var targetPose = desiredPose;

    double xvelocity = xController.calculate(currPose.getX(), targetPose.getX());
    double yvelocity = yController.calculate(currPose.getY(), targetPose.getY());
    double thetaVelocity =
        thetaController.calculate(
            currPose.getRotation().getRadians(), targetPose.getRotation().getRadians());

    if (atGoal()) {
      xvelocity = 0.0;
      yvelocity = 0.0;
      thetaVelocity = 0.0;
    }

    swerve.setChassisSpeeds(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            xvelocity, yvelocity, thetaVelocity, currPose.getRotation()));
  }

  public boolean atGoal() {
    return (xController.atGoal() && yController.atGoal() && thetaController.atGoal());
  }

  @Override
  public boolean isFinished(){
    return atGoal();
  }
}