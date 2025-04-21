// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.reflect.Constructor;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DeepClimb;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class RunDeepClimb extends Command {
  
  DeepClimb deepClimb = Constants.DeepClimb;

  /** Creates a new RunDeepClimb. */
  public RunDeepClimb() {
    addRequirements(deepClimb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    boolean rightTrigger = Robot.getInstance().m_robotContainer.driverXbox.start().getAsBoolean();
    boolean leftTrigger = Robot.getInstance().m_robotContainer.driverXbox.back().getAsBoolean();

    if(rightTrigger){
      
      deepClimb.climbUp();

    }

    else if(leftTrigger){
      
      deepClimb.climbDown();

    }

    else{
      deepClimb.stop();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}