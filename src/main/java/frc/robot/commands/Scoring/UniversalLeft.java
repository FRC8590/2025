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

/** Command to score a coral by ejecting it */
public class UniversalLeft extends SequentialCommandGroup {

  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public 
  UniversalLeft() {
    addCommands(
        // Constants.drivebase.driveToPose(new Pose2d(new Translation2d(4.92,5.9), new Rotation2d(Units.degreesToRadians(-107)))),        // Constants.drivebase.driveToPose(new Pose2d(new Translation2d(4.92,5.9), new Rotation2d(Units.degreesToRadians(-107)))),
        // Constants.drivebase.driveToPose(new Pose2d(new Translation2d(3.42,5.49), new Rotation2d(Units.degreesToRadians(-47)))),
        // Constants.drivebase.driveToDistanceCommand(Units.feetToMeters(2.56), 0.8),
        Constants.drivebase.driveToPose(new Pose2d(getApriltagTranslation2d(Constants.vision.getClosestTag()), getApriltagRotation2d(Constants.vision.getClosestTag()))),
        new MoveElevator(0.37),
        new ScoreCoral()

        // Constants.drivebase.driveToDistanceCommand(Units.feetToMeters(2), 0.8),
        // new PrintCommand("DONE"),
        // new MoveElevator(0.37),
        // new ScoreCoral()
      //TRY DISSTAMCE SENSOR
    );

    addRequirements(Constants.drivebase);
    addRequirements(Constants.SHOOTER);
    addRequirements(Constants.ELEVATOR);

  }

  private Rotation2d getApriltagRotation2d(int apriltag)
  {
    if(apriltag == 17){
      return new Rotation2d(Units.degreesToRadians(60));
    }
    if(apriltag == 18){
      return new Rotation2d(Units.degreesToRadians(0));
    }
    if(apriltag == 19){
      return new Rotation2d(Units.degreesToRadians(-58));
    }
    if(apriltag == 20){
      return new Rotation2d(Units.degreesToRadians(-117));
    }
    if(apriltag == 21){
      return new Rotation2d(Units.degreesToRadians(180));
    }
    if(apriltag == 22 ){
      return new Rotation2d(Units.degreesToRadians(120));
    }
    
    return null;
  }

  public Translation2d getApriltagTranslation2d(int apriltag)
  {
    if(apriltag == 17){
      return new Translation2d(3.648, 2.444);
    }
    if(apriltag == 18){
      return new Translation2d(2.789, 4.000);
    }
    if(apriltag == 19){
      return new Translation2d(3.91, 5.06);
    }
    if(apriltag == 20){
      return new Translation2d(5.13, 5.04);
    }
    if(apriltag == 21){
      return new Translation2d(6.191, 4.000);
    }
    if(apriltag == 22){
      return new Translation2d(5.353, 2.444);
    }
    return null;
  }


}

