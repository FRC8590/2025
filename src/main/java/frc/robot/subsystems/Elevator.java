// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import au.grapplerobotics.LaserCan;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorState;
import frc.robot.Constants.feedforward;
import frc.robot.Constants.pid;

/**
 * Elevator subsystem that controls vertical motion using profiled PID with feedforward.
 */
public class Elevator extends SubsystemBase {
  
  // ------------------- Constants -------------------
  
  // Physical limits
  private static final double MAX_HEIGHT_METERS = 0.7100;
  private static final double MIN_HEIGHT_METERS = 0.01;
  private static final double MAX_VELOCITY_METERS = 6; // m/s
  private static final double MAX_ACCELERATION = 6; // m/s²
  
  // ------------------- Hardware Components -------------------
  
  private final SparkMax elevatorMaster;
  private final SparkMax elevatorFollower;
  private final RelativeEncoder tbEncoder;
  private final SparkClosedLoopController closedLoopController;
  
  // ------------------- Control Components -------------------
  
  private final ProfiledPIDController elevatorController;
  private final ElevatorFeedforward m_feedforward;
  
  // ------------------- State Variables -------------------
  
  private ElevatorState currState = ElevatorState.DISABLED;
  private double setpoint = 0.0;
  private double goalMeters = 0.0;
  
  // ------------------- Public Triggers -------------------
  
  public final Trigger atMin = new Trigger(() -> 
      getElevatorHeightEncoder() <= MIN_HEIGHT_METERS + 0.001);

  public final Trigger atMax = new Trigger(() -> 
      getElevatorHeightEncoder() >= MAX_HEIGHT_METERS - 0.001);
  
  /**
   * Creates a new Elevator subsystem.
   */
  public Elevator() {
    // Initialize hardware
    elevatorMaster = new SparkMax(Constants.masterMotorID, MotorType.kBrushless);
    elevatorFollower = new SparkMax(Constants.followerMotorID, MotorType.kBrushless);
    closedLoopController = elevatorMaster.getClosedLoopController();
    tbEncoder = elevatorMaster.getAlternateEncoder();
    
    // Initialize controllers
    Constraints constraints = new Constraints(MAX_VELOCITY_METERS, MAX_ACCELERATION);
    elevatorController = new ProfiledPIDController(
        Constants.pid.kP,
        Constants.pid.kI,
        Constants.pid.kD, 
        constraints
    );
    
    m_feedforward = new ElevatorFeedforward(
        Constants.feedforward.kS,
        Constants.feedforward.kG,
        Constants.feedforward.kV,
        Constants.feedforward.kA
    );

    // Configure master motor
    SparkMaxConfig masterConfig = new SparkMaxConfig();
    masterConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(50)
        .closedLoopRampRate(Constants.rampRate);

    masterConfig.alternateEncoder
        .setSparkMaxDataPortConfig()
        .positionConversionFactor(Constants.distancePerRotation);

    // Configure follower motor
    SparkMaxConfig followerConfig = new SparkMaxConfig();
    followerConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(50)
        .closedLoopRampRate(Constants.rampRate)
        .follow(elevatorMaster, true);

    // Apply configurations
    elevatorMaster.configure(masterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    elevatorFollower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
    // Zero encoder
    tbEncoder.setPosition(0);

  }


  // ------------------- Control Methods -------------------
  
  /**
   * Run control loop to reach and maintain goal position.
   * @param goalMeters the position to maintain in meters
   */
  public void reachGoal(double goalMeters) {
    double currentPos = getElevatorHeightEncoder();
    if(currentPos < -1) {
      System.out.println("ERROR: Position reading invalid (less than -1)");
      return;
    }


    // Bound goal to valid range
    goalMeters = MathUtil.clamp(goalMeters, MIN_HEIGHT_METERS, MAX_HEIGHT_METERS);
    
    setpoint = goalMeters;
    setState(ElevatorState.SETPOINT);

    // Calculate next profile step
    double pidOutput = elevatorController.calculate(currentPos, goalMeters);
    
    // Calculate feedforward based on setpoint velocity
    double velocityDesired = elevatorController.getSetpoint().velocity;
    double accelerationDesired = 0.0; // Can be calculated if needed
    
    // Add feedforward to PID output (including gravity compensation)
    double output = pidOutput;
    output = MathUtil.clamp(output, -1, 1);

    if(goalMeters < currentPos){
      output += 0.04;
    }
    
    // Apply control output to motor
    elevatorMaster.set(output);
  }

  /**
   * Set the goal of the elevator and run a command to reach that position.
   * @param goalMeters Goal in meters
   * @return Command that runs until the elevator reaches the goal
   */
  public Command setGoal(double goalMeters) {

    return Commands.runOnce(() -> this.goalMeters = goalMeters);
  }

  /**
   * Sets the current state of the elevator subsystem.
   * @param newState the new state to set
   */
  public void setState(ElevatorState newState) {
    currState = newState;
  }

  /**
   * Creates a trigger that activates when the elevator is at the specified height.
   * @param heightMeters Target height in meters
   * @param toleranceMeters Acceptable tolerance in meters
   * @return Trigger that activates when within tolerance of the target height
   */
  public Trigger atHeight(double heightMeters, double toleranceMeters) {
    return new Trigger(() -> 
        MathUtil.isNear(heightMeters, getElevatorHeightEncoder(), toleranceMeters));
  }

  // ------------------- Sensor Methods -------------------
  
  /**
   * Returns the height of the elevator based on the laser distance sensor.
   * @return Height in meters from the laser sensor, or -1 if reading is invalid
   */
  public double getElevatorHeightLaser() {
    LaserCan.Measurement measurement = Constants.laserCan.getMeasurement();
    if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT) {
      return (measurement.distance_mm/1000.0) - 0.055;
    } else {
      return -1;
    }
  }

  /**
   * Returns the height of the elevator based on encoder readings.
   * @return Height in meters from the encoder
   */
  public double getElevatorHeightEncoder() {
    return tbEncoder.getPosition();
  }

  /**
   * Returns the current velocity of the elevator.
   * @return Velocity in meters per second
   */
  public double getElevatorVelocity() {
    return tbEncoder.getVelocity();
  }

  // ------------------- Utility Methods -------------------
  
  /**
   * Gets the string representation of the current state.
   * @return String representation of the current state
   */
  private String getState() {
    switch(currState) {
      case SETPOINT:
        return "SETPOINT";
      case ZEROED:
        return "ZEROED";
      default:
        return "DISABLED";
    }
  }

  /**
   * Logs data to SmartDashboard for debugging.
   */
  private void log() {
    // Position and velocity data
    SmartDashboard.putNumber("Elevator Position (m)", getElevatorHeightEncoder());
    SmartDashboard.putNumber("Elevator Velocity (m/s)", getElevatorVelocity());
    
    // Control data
    SmartDashboard.putNumber("Elevator Setpoint (m)", setpoint);
    SmartDashboard.putNumber("Elevator Error (m)", setpoint - getElevatorHeightEncoder());
    SmartDashboard.putString("Elevator State", getState());
    
    // Motor data
    SmartDashboard.putNumber("Elevator Motor Output", elevatorMaster.getAppliedOutput());
    SmartDashboard.putNumber("Elevator Current Pulled", elevatorMaster.getOutputCurrent());
    
    // Limit data
    SmartDashboard.putBoolean("At Min Limit", atMin.getAsBoolean());
    SmartDashboard.putBoolean("At Max Limit", atMax.getAsBoolean());
    
    // Sensor data
    SmartDashboard.putNumber("LaserCan Distance (m)", getElevatorHeightLaser());
    
    // Goal tracking
    SmartDashboard.putNumber("Goal Position (m)", goalMeters);
    SmartDashboard.putBoolean("At Target Height", atHeight(goalMeters, 0.1).getAsBoolean());
    
    // ProfiledPID debugging
    if (elevatorController != null) {
      SmartDashboard.putNumber("ProfiledPID Goal", elevatorController.getGoal().position);
      SmartDashboard.putNumber("ProfiledPID Setpoint Position", elevatorController.getSetpoint().position);
      SmartDashboard.putNumber("ProfiledPID Setpoint Velocity", elevatorController.getSetpoint().velocity);
    }
  
  }

  
  @Override
  public void periodic() {
    if(RobotState.isEnabled()){
      reachGoal(goalMeters);
    }



    // Update telemetry
    log();

  }
}