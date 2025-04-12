// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Scoring;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;

import java.util.function.BooleanSupplier;

/* Use for autos, wait for coral before leaving station */
public class WaitForCoralIntake extends Command {
  /** Creates a new WaitForCoralIntake.
   * Used in autos. Checks if the intake has a coral.
   * In autos, used to make the bot not leave untill is reseives a coral
  */

  public WaitForCoralIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Constants.SHOOTER.processIntakeCoral();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Constants.SHOOTER.hasCoral().getAsBoolean();
  }
}
