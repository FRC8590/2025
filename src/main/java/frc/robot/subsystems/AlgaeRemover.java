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

public class AlgaeRemover extends SubsystemBase {
  
  // Define limits in rotations/meters
  private static final double EXTENDED_POSITION = 5.0;  // Down position
  private static final double RETRACTED_POSITION = 0.0; // Up position
  private static final double MAX_VELOCITY = 0.1;
  
  // Motor speeds for manual control
  private static final double UP_POWER = 0.4;    // More power needed against gravity
  private static final double DOWN_POWER = 0.1;  // Less power needed with gravity
  private static final double HOLD_POWER = 0.15; // Power to hold position
  
  private final SparkMax pivotMotor;
  private final SparkMax removerMotor;
  private final PIDController pidController;
  private final AbsoluteEncoder tbEncoder;
  private double encoderOffset = 0;

  private double setpoint = 0.0;
  
  // State tracking
  private enum AlgaeRemoverState {
    ACTIVE, INACTIVE, DISABLED
  }
  
  private AlgaeRemoverState currentState = AlgaeRemoverState.INACTIVE;
  
  // Triggers for position limits
  public final Trigger atMin = new Trigger(() -> 
      getPivotPosition() <= RETRACTED_POSITION + 0.001);

  public final Trigger atMax = new Trigger(() -> 
      getPivotPosition() >= EXTENDED_POSITION - 0.001);
      
  // Feedforward for gravity compensation
  public boolean isExtended = false;

  /** Creates a new AlgaeRemoverSubsystem. */
  public AlgaeRemover() {
    
    pivotMotor = new SparkMax(Constants.ALGAE_REMOVER_CONSTANTS.pivotMotorID(), MotorType.kBrushless);
    removerMotor = new SparkMax(Constants.ALGAE_REMOVER_CONSTANTS.removerID(), MotorType.kBrushless);
    
    tbEncoder = removerMotor.getAbsoluteEncoder();
    
    
    // Configure pivot motor - update for holding position
    SparkMaxConfig pivotConfig = new SparkMaxConfig();
    pivotConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake)  // Important for holding position
        .smartCurrentLimit(10)
        .closedLoopRampRate(Constants.ALGAE_REMOVER_CONSTANTS.rampRate());
  
        
    // Configure remover motor
    SparkMaxConfig removerConfig = new SparkMaxConfig();
    removerConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(5);
    
    removerConfig.absoluteEncoder
      .setSparkMaxDataPortConfig()
      .positionConversionFactor(1);
  
    // Apply configurations
    pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    removerMotor.configure(removerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    

    pidController = new PIDController(2,0, 0);
    pidController.enableContinuousInput(0,1);
  }
  

  /**
   * Run control loop to reach and maintain goal position
   * @param goalPosition the position to maintain
   */
  public void reachGoalUp() {
    pivotMotor.set(pidController.calculate(getPivotPosition(), 0));
    stopRemover();
  }

  public void reachGoalDown() {
    pivotMotor.set(pidController.calculate(getPivotPosition(), 0.16));   
    runRemover();
 
    
  }
  
  /**
   * Returns the position of the pivot
   * @return the position in rotations/meters
   */
  public double getPivotPosition() {
    return tbEncoder.getPosition();
  }
  
  /**
   * Returns the velocity of the pivot
   * @return the velocity in rotations per second or meters per second
   */
  public double getPivotVelocity() {
    return tbEncoder.getVelocity();
  }
  

 
  
  /**
   * Run the remover motor
   */
  public void runRemover() {
    removerMotor.set(-0.9);
  }
  
  /**
   * Stop the remover motor
   */
  public void stopRemover() {
    if(getPivotPosition() > 0.08 && getPivotPosition() < 0.2){
      removerMotor.set(-0.4);
      return;
    }
    removerMotor.set(0);
  }
  

  
  /**
   * A trigger for when the position is at an acceptable tolerance
   * @param position Target position
   * @param tolerance Tolerance
   * @return Trigger
   */
  public Trigger atPosition(double position, double tolerance) {
    return new Trigger(() -> 
        MathUtil.isNear(position, getPivotPosition(), tolerance));
  }
  
  /**
   * Get the current state as a string
   * @return State string
   */
  private String getStateString() {
    return currentState.toString();
  }
  
  /**
   * Log data to SmartDashboard
   */
  public void log() {
    SmartDashboard.putNumber("Algae Remover Position", getPivotPosition());
    SmartDashboard.putNumber("Algae Remover Velocity", getPivotVelocity());
    SmartDashboard.putNumber("Algae Remover Setpoint", setpoint);
    SmartDashboard.putNumber("Algae Remover Error", setpoint - getPivotPosition());
    SmartDashboard.putString("Algae Remover State", getStateString());
    SmartDashboard.putNumber("Algae Remover Motor Output", pivotMotor.getAppliedOutput());
    SmartDashboard.putBoolean("Algae Remover At Min", atMin.getAsBoolean());
    SmartDashboard.putBoolean("Algae Remover At Max", atMax.getAsBoolean());
    SmartDashboard.putNumber("Algae Remover Current", pivotMotor.getOutputCurrent());
  }

  @Override
  public void periodic() {
    log();
  }

  /**
   * Toggle the algae remover between extended and retracted positions
   */
  public void toggle() {
    if (isExtended) {
      reachGoalDown();
    } else {
      reachGoalUp();
    }
  }
  /**
   * Command to toggle the algae remover
   * @return Command
   */
  public Command reachGoalDownCommand() {
    return run(this::reachGoalDown);
  }
  /**
   * Command to toggle the algae remover
   * @return Command
   */
  public Command reachGoalUpCommand() {
    return run(this::reachGoalUp).until(atPosition(0, 0.01));
  }
  /**
   * Command to toggle the algae remover
   * @return Command
   */
  public Command toggleCommand() {
    return run(this::toggle);
  }

  public void updateState(){
    System.out.println("UPDATED");
    if(isExtended){
      isExtended = false;
    }
    else{
      isExtended = true;
    }

  }
}