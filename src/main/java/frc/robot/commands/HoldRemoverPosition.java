// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;

import java.util.function.BooleanSupplier;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class HoldRemoverPosition extends Command {
  /** Creates a new WaitForCoralIntake. */


  public HoldRemoverPosition() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Constants.ALGAE_REMOVER);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      Constants.ALGAE_REMOVER.runUp();
      Constants.ALGAE_REMOVER.stopRemover();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Constants.ALGAE_REMOVER.stopRemover();
    Constants.ALGAE_REMOVER.stopPivot();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Constants.ALGAE_REMOVER.getPivotPosition() < 0.1;
  }
}
