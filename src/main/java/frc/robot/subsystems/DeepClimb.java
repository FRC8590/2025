// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.AbsoluteEncoder;
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
  
  
  private final SparkMax frontMotor;
  private final SparkMax backMotor;
  private double encoderOffset = 0;

  private double setpoint = 0.0;
  
  // State tracking
  private enum AlgaeRemoverState {
    ACTIVE, INACTIVE, DISABLED
  }

  private final SparkMaxConfig frontMotorConfig = new SparkMaxConfig();
  private final SparkMaxConfig backMotorConfig = new SparkMaxConfig();

  
  // Feedforward for gravity compensation
  public boolean isExtended = false;

  /** Creates a new AlgaeRemoverSubsystem. */
  public DeepClimb() {
    
    frontMotor = new SparkMax(Constants.DEEP_CLIMB_CONSTANTS.kFrontMotorID(), MotorType.kBrushless);
    backMotor = new SparkMax(Constants.DEEP_CLIMB_CONSTANTS.kBackMotorID(), MotorType.kBrushless);

    frontMotorConfig
      .idleMode(IdleMode.kBrake)
      .smartCurrentLimit(30)
      .closedLoopRampRate(0.1)
      .inverted(true);
    
    backMotorConfig
      .idleMode(IdleMode.kBrake)
      .smartCurrentLimit(30)
      .closedLoopRampRate(0.1)
      .inverted(false);


    // Apply configurations
    frontMotor.configure(frontMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    backMotor.configure(backMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }


  public void runFrontUp(){
    frontMotor.set(0.4);
  }
  
  public void runBackUp(){
    backMotor.set(0.4);

  }



  public void runFrontDown(){
    frontMotor.set(-0.4);
  }
  
  public void runBackDown(){
    backMotor.set(-0.4);
  }

  public void climbUp(){
    runFrontUp();
    runBackUp();

  }

  public void climbDown(){
    runFrontDown();
    runBackDown();

  }

  public void stop(){
    frontMotor.set(0);
    backMotor.set(0);
  }

}