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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.constants.AlgaeRemoverConstants;

public class AlgaeRemover extends SubsystemBase {
  
  // Define limits in rotations/meters
  private static final double MAX_POSITION = 5.5;  
  private static final double MIN_POSITION = 0.0;
  private static final double MAX_VELOCITY = 0.1;
  
  private final SparkMax pivotMotor;
  private final SparkMax removerMotor;
  private final RelativeEncoder pivotEncoder;
  private final SparkClosedLoopController closedLoopController;
  
  private double setpoint = 0.0;
  
  // State tracking
  private enum AlgaeRemoverState {
    ACTIVE, INACTIVE, DISABLED
  }
  
  private AlgaeRemoverState currentState = AlgaeRemoverState.INACTIVE;
  
  // Triggers for position limits
  public final Trigger atMin = new Trigger(() -> 
      getPivotPosition() <= MIN_POSITION + 0.001);

  public final Trigger atMax = new Trigger(() -> 
      getPivotPosition() >= MAX_POSITION - 0.001);
      
  // Feedforward for gravity compensation
  private final ElevatorFeedforward feedforward;

  /** Creates a new AlgaeRemoverSubsystem. */
  public AlgaeRemover() {
    
    pivotMotor = new SparkMax(Constants.ALGAE_REMOVER_CONSTANTS.pivotMotorID(), MotorType.kBrushless);
    removerMotor = new SparkMax(Constants.ALGAE_REMOVER_CONSTANTS.removerID(), MotorType.kBrushless);
    
    pivotEncoder = pivotMotor.getEncoder();
    closedLoopController = pivotMotor.getClosedLoopController();
    
    feedforward = new ElevatorFeedforward(
        AlgaeRemoverConstants.FeedforwardConstants.DEFAULT.kS(),
        AlgaeRemoverConstants.FeedforwardConstants.DEFAULT.kG(),
        AlgaeRemoverConstants.FeedforwardConstants.DEFAULT.kV(),
        AlgaeRemoverConstants.FeedforwardConstants.DEFAULT.kA()
    );
    
    // Configure pivot motor
    SparkMaxConfig pivotConfig = new SparkMaxConfig();
    pivotConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(35)
        .closedLoopRampRate(Constants.ALGAE_REMOVER_CONSTANTS.rampRate());
    
    pivotConfig.encoder
        .positionConversionFactor(Constants.ALGAE_REMOVER_CONSTANTS.distancePerRotation());
    
    pivotConfig.closedLoop
        .p(Constants.ALGAE_REMOVER_CONSTANTS.kP())
        .i(Constants.ALGAE_REMOVER_CONSTANTS.kI())
        .d(Constants.ALGAE_REMOVER_CONSTANTS.kD())
        .outputRange(-1, 1);
    
    // Configure remover motor
    SparkMaxConfig removerConfig = new SparkMaxConfig();
    removerConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(35);
    
    // Apply configurations
    pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    removerMotor.configure(removerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    // Zero encoder
    pivotEncoder.setPosition(0);
  }
  

  /**
   * Run control loop to reach and maintain goal position
   * @param goalPosition the position to maintain
   */
  public void reachGoal(double goalPosition) {
    double currentPos = getPivotPosition();
    
    // Bound goal to valid range
    goalPosition = MathUtil.clamp(goalPosition, MIN_POSITION, MAX_POSITION);
    
    setpoint = goalPosition;
    
    // Add velocity limiting
    double error = goalPosition - currentPos;
    double maxDelta = MAX_VELOCITY; // Max change per cycle
    
    double limitedGoal = currentPos + MathUtil.clamp(error, -maxDelta, maxDelta);
    
    closedLoopController.setReference(
        limitedGoal,
        ControlType.kPosition,
        ClosedLoopSlot.kSlot0,
        AlgaeRemoverConstants.FeedforwardConstants.DEFAULT.kG() // Gravity compensation
    );
  }
  
  /**
   * Returns the position of the pivot
   * @return the position in rotations/meters
   */
  public double getPivotPosition() {
    return pivotEncoder.getPosition();
  }
  
  /**
   * Returns the velocity of the pivot
   * @return the velocity in rotations per second or meters per second
   */
  public double getPivotVelocity() {
    return pivotEncoder.getVelocity();
  }
  

  /**
   * Set the algae remover to active position
   */
  private void setActive() {
    currentState = AlgaeRemoverState.ACTIVE;
    reachGoal(Constants.ALGAE_REMOVER_CONSTANTS.activeGoal());
    runRemover();
  }
  
  /**
   * Set the algae remover to inactive position
   */
  private void setInactive() {
    currentState = AlgaeRemoverState.INACTIVE;
    reachGoal(Constants.ALGAE_REMOVER_CONSTANTS.inactiveGoal());
    stopRemover();
  }
  
  /**
   * Run the remover motor
   */
  public void runRemover() {
    removerMotor.set(0.5);
  }
  
  /**
   * Stop the remover motor
   */
  public void stopRemover() {
    removerMotor.set(0);
  }
  
  /**
   * Command to set the algae remover to active position
   * @return Command
   */
  public Command setActiveCommand() {
    return run(this::setActive)
        .until(() -> atPosition(Constants.ALGAE_REMOVER_CONSTANTS.activeGoal(), 0.1).getAsBoolean());
  }
  
  /**
   * Command to set the algae remover to inactive position
   * @return Command
   */
  public Command setInactiveCommand() {
    return run(this::setInactive)
        .until(() -> atPosition(Constants.ALGAE_REMOVER_CONSTANTS.inactiveGoal(), 0.1).getAsBoolean());
  }
  
  /**
   * Command to run the remover motor
   * @return Command
   */
  public Command runRemoverCommand() {
    return runOnce(this::runRemover);
  }
  
  /**
   * Command to stop the remover motor
   * @return Command
   */
  public Command stopRemoverCommand() {
    return runOnce(this::stopRemover);
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
    // This method will be called once per scheduler run
    log();
  }
}