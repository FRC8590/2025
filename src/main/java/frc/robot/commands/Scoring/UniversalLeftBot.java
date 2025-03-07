// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.Scoring;
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
import frc.robot.commands.MoveElevator;
import frc.robot.constants.ScoringConstants;
import frc.robot.utils.ScoringPositionTransformer;
import java.io.Console;
import java.security.AllPermission;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
/** Command to score a coral by ejecting it */
public class UniversalLeftBot extends SequentialCommandGroup {
  // Define working positions for tag 20 (reference positions)
  private static final Pose2d TAG_20_LEFT_POSITION = 
      new Pose2d(new Translation2d(4.70, 5.31), new Rotation2d(Units.degreesToRadians(-124.9)));
  private static final Pose2d TAG_20_RIGHT_POSITION = 
      new Pose2d(new Translation2d(5.11, 5.01), new Rotation2d(Units.degreesToRadians(-123.3)));
  private static final int REFERENCE_TAG_ID = 20;
  
  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public UniversalLeftBot() {
   
    addCommands(
      new ParallelCommandGroup(
        moveToScore,
        new MoveElevator(0.37)
      ),
      new ScoreCoral()
    );
    addRequirements(Constants.drivebase);
    addRequirements(Constants.SHOOTER);
    addRequirements(Constants.ELEVATOR);
    addRequirements(Constants.ALGAE_REMOVER);
  }
  
  private int getClosestTag() {
    return Constants.drivebase.findClosestAprilTag();
  }
  
  // Transform scoring positions from reference tag (20) to any other tag
  private Pose2d getLeftScoringPosition(int tagId) {
    return ScoringPositionTransformer.transformScoringPosition(
        TAG_20_LEFT_POSITION,  // Source left position 
        TAG_20_RIGHT_POSITION, // Source right position
        REFERENCE_TAG_ID,      // Source tag ID (20)
        tagId,                 // Target tag ID
        true                   // Target is left position
    );
  }
  
  // An example selectcommand. Will select from the commands based on the value returned
  // by the selector method at runtime.
  private final Command moveToScore =
      new SelectCommand<>(
          // Maps selector values to commands
          Map.ofEntries(
              Map.entry(17, Constants.drivebase.driveToPose(getLeftScoringPosition(17))),
              Map.entry(18, Constants.drivebase.driveToPose(getLeftScoringPosition(18))),
              Map.entry(19, Constants.drivebase.driveToPose(getLeftScoringPosition(19))),
              Map.entry(20, Constants.drivebase.driveToPose(getLeftScoringPosition(20))),
              Map.entry(21, Constants.drivebase.driveToPose(getLeftScoringPosition(21))),
              Map.entry(22, Constants.drivebase.driveToPose(getLeftScoringPosition(22))),
              Map.entry(6, Constants.drivebase.driveToPose(getLeftScoringPosition(6))),
              Map.entry(7, Constants.drivebase.driveToPose(getLeftScoringPosition(7))),
              Map.entry(8, Constants.drivebase.driveToPose(getLeftScoringPosition(8))),
              Map.entry(9, Constants.drivebase.driveToPose(getLeftScoringPosition(9))),
              Map.entry(10, Constants.drivebase.driveToPose(getLeftScoringPosition(10))),
              Map.entry(11, Constants.drivebase.driveToPose(getLeftScoringPosition(11)))),
          this::getClosestTag);
}