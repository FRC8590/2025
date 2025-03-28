// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DeepClimb;

import java.util.function.BooleanSupplier;

/* Use for autos, wait for coral before leaving station */
public class DeepClimbCommand extends Command {
  /** Creates a new WaitForCoralIntake.
   * Used in autos. Checks if the intake has a coral.
   * In autos, used to make the bot not leave untill is reseives a coral
  */

  private RobotContainer container;
  private DeepClimb deepClimb;

  public DeepClimbCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    container = Robot.getInstance().m_robotContainer;
    deepClimb = Constants.DEEP_CLIMB;
    addRequirements(deepClimb);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean leftBumper = container.operatorController.leftBumper().getAsBoolean();
    boolean leftTrigger = container.operatorController.leftTrigger().getAsBoolean();
    boolean rightBumper = container.operatorController.rightBumper().getAsBoolean();
    boolean rightTrigger = container.operatorController.rightTrigger().getAsBoolean();
    
    // Debug which buttons are pressed
    if (leftBumper || leftTrigger || rightBumper || rightTrigger) {
        System.out.println("Buttons: LB=" + leftBumper + " LT=" + leftTrigger + 
                          " RB=" + rightBumper + " RT=" + rightTrigger);
    }
    
    if(leftBumper){
      deepClimb.frontUp();
    }
    else if(leftTrigger){
      deepClimb.frontDown();
    }
    else{
      deepClimb.frontStop();
    }
    
    if(rightTrigger){
      deepClimb.backDown();
    }
    else if(rightBumper){
      deepClimb.backUp();
    }
    else{
      deepClimb.backStop();
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
