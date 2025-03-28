// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.ClosedLoopSlot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.constants.AlgaeRemoverConstants;

public class DeepClimb extends SubsystemBase {
  
  // Define limits in rotations/meters
  
  private final SparkMax frontMotor;
  private final SparkMax backMotor;

  private double setpoint = 0.0;
  
  
  // Feedforward for gravity compensation
  public boolean isExtended = false;

  /** Creates a new AlgaeRemoverSubsystem. */
  public DeepClimb() {
    
    frontMotor = new SparkMax(Constants.DEEP_CLIMB_CONSTANTS.frontMotorID(), MotorType.kBrushless);
    backMotor = new SparkMax(Constants.DEEP_CLIMB_CONSTANTS.backMotorID(), MotorType.kBrushless);
    
    
    
    // Configure pivot motor - update for holding position
    SparkMaxConfig frontConfig = new SparkMaxConfig();
    frontConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake)  // Important for holding position
        .smartCurrentLimit(80);

    // Configure pivot motor - update for holding position
    SparkMaxConfig backConfig = new SparkMaxConfig();
    backConfig
        .inverted(false)
        .idleMode(IdleMode.kBrake)  // Important for holding position
        .smartCurrentLimit(80);
    
    // Apply configurations
    frontMotor.configure(frontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    backMotor.configure(backConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    

  }

  public void frontUp(){
    frontMotor.set(0.3);
    System.out.println("Front Up: " + frontMotor.get());
  }

  public void frontDown(){
    frontMotor.set(-0.3);
    System.out.println("Front Down: " + frontMotor.get());
  }

  public void backUp(){
    backMotor.set(0.3);
    System.out.println("Back Up: " + backMotor.get());
  }

  public void backDown(){
    backMotor.set(-0.3);
    System.out.println("Back Down: " + backMotor.get());
  }

  public void frontStop(){
    frontMotor.set(0);
  }
  public void backStop(){
    backMotor.set(0);
  }


}