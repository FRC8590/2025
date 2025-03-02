// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.MoveElevator;
import frc.robot.constants.ScoringConstants;

import java.io.Console;
import java.security.AllPermission;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

/** Command to score a coral by ejecting it */
public class SetLED extends SequentialCommandGroup {

  // Define the AprilTag IDs we're interested in
  
  /**
   * Creates a new ScoreCoral command that ejects the coral
   */
  public SetLED() {
    
    addCommands(
      setLED
    );
    addRequirements(Constants.LEDSystem);
  }



  private boolean hasCoral() {
    return Constants.SHOOTER.hasCoral().getAsBoolean();
  }

  // An example selectcommand.  Will select from the three commands based on the value returned
  // by the selector method at runtime.  Note that selectcommand works on Object(), so the
  // selector does not have to be an enum; it could be any desired type (string, integer,
  // boolean, double...)
  private final Command setLED =
      new SelectCommand<>(
          // Maps selector values to commands
          Map.ofEntries(
              Map.entry(true, Commands.runOnce(() -> Constants.LEDSystem.setGreen())),
              Map.entry(false, Commands.runOnce(() -> Constants.LEDSystem.setRed()))),
          this::hasCoral);
}

