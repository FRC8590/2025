// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.math.util.Units;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Millimeters;
import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.MAXMotionConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import au.grapplerobotics.LaserCan;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.measure.MutAngle;
import edu.wpi.first.units.measure.MutDistance;
import edu.wpi.first.units.measure.MutLinearVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorState;
import com.revrobotics.spark.ClosedLoopSlot;
import java.util.function.BooleanSupplier;

public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */
  
  // Define limits in meters
  private static final double MAX_HEIGHT_METERS = 0.69;  // 10cm up
  private static final double MIN_HEIGHT_METERS = 0.01; // 10cm down
  private static final double MAX_VELOCITY_METERS = 1.6; // ~0.5 inches per second

  // Fix trigger definitions with small tolerance
  public final Trigger atMin = new Trigger(() -> 
      getElevatorHeightEncoder() <= MIN_HEIGHT_METERS + 0.001);

  public final Trigger atMax = new Trigger(() -> 
      getElevatorHeightEncoder() >= MAX_HEIGHT_METERS - 0.001);

  private final SparkMax elevatorMaster;
  private final SparkMax elevatorFollower;
  private final RelativeEncoder tbEncoder;
  private final SparkClosedLoopController closedLoopController;
  private ElevatorState currState = ElevatorState.DISABLED;
  private double setpoint = 0.0;

  ShuffleboardTab tab;

  double[] measurementStdDevs = {0.0, 0.0};

    ElevatorFeedforward m_feedforward = new ElevatorFeedforward(
        Constants.feedforward.kS,
        Constants.feedforward.kG,
        Constants.feedforward.kV,
        Constants.feedforward.kA
    );




  

  public Elevator() {
    elevatorMaster = new SparkMax(Constants.masterMotorID, MotorType.kBrushless);
    elevatorFollower = new SparkMax(Constants.followerMotorID, MotorType.kBrushless);
    
    closedLoopController = elevatorMaster.getClosedLoopController();
    tbEncoder = elevatorMaster.getAlternateEncoder();

    SparkMaxConfig masterConfig = new SparkMaxConfig();
    masterConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(35)
        .closedLoopRampRate(Constants.rampRate);

    masterConfig.alternateEncoder
        .setSparkMaxDataPortConfig()
        .positionConversionFactor(Constants.distancePerRotation);

    masterConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kAlternateOrExternalEncoder)
        .p(Constants.pid.kP)
        .i(Constants.pid.kI)
        .d(Constants.pid.kD)
        .outputRange(-1, 1);

    SparkMaxConfig followerConfig = new SparkMaxConfig();
    followerConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(35)
        .closedLoopRampRate(Constants.rampRate)
        .follow(elevatorMaster, true);

    // Apply configurations
    elevatorMaster.configure(masterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    elevatorFollower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    

    System.out.println(getElevatorHeightLaser());
    // Zero encoder
    tbEncoder.setPosition(0);
  }

  
    /**
   * Run control loop to reach and maintain goal.
   * @param goalMeters the position to maintain in meters
   */
  public void reachGoal(double goalMeters) {
    double currentPos = getElevatorHeightEncoder();
    if(currentPos < -1){
      System.out.println("ERROR, LESS THAN 0");
      return;
    }

    // Bound goal to valid range
    goalMeters = MathUtil.clamp(goalMeters, MIN_HEIGHT_METERS, MAX_HEIGHT_METERS);
    
    setpoint = goalMeters;
    setState(ElevatorState.SETPOINT);

    // Add velocity limiting
    double error = goalMeters - currentPos;
    double maxDelta = MAX_VELOCITY_METERS; // Max change per 20ms cycle
    
    double limitedGoal = currentPos + MathUtil.clamp(error, -maxDelta, maxDelta);

    closedLoopController.setReference(
        limitedGoal, // Convert meters to encoder units
        ControlType.kPosition,
        ClosedLoopSlot.kSlot0,
        Constants.feedforward.kG // Gravity compensation
    );

    SmartDashboard.putNumber("error", closedLoopController.getIAccum());
  }



  /**
   * Returns {@code true} if the limit switch is pressed, based on the selected polarity.
   *
   * <p>This method works even if the limit switch is not enabled for controller shutdown.
   *
   * @return {@code true} if the limit switch is pressed
   */




  /**
   * Returns the height of the elevator currently based on the laser can
   * @return the rotations of the alternate encoder multiplied by the {@link Constants.Elevator} kDistancePeraTick
   */

  public double getElevatorHeightLaser(){

    LaserCan.Measurement measurement = Constants.laserCan.getMeasurement();
    if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
      return (measurement.distance_mm/1000.0) - 0.055;
    } else {
      return -1;
    }
  
  }

  /**
   * Returns the height of the elevator currently based on the rotations of the elevator shaft
   * @return the rotations of the alternate encoder multiplied by the {@link Constants.Elevator} kDistancePeraTick
   */
  public double getElevatorHeightEncoder(){
    return tbEncoder.getPosition();
  }

  /**
 * Returns the height of the elevator currently based on the rotations of the elevator shaft
 * @return the rotations of the alternate encoder multiplied by the {@link Constants.Elevator} kDistancePeraTick
 */
  public double getElevatorVelocity(){
    return tbEncoder.getVelocity();
  }



  /**
   * Set the goal of the elevator. Can be used to directly set goal of the elevator as a command
   * @param goalMeters Goal in meters
   * @return {@link edu.wpi.first.wpilibj2.command.Command}
   */
  public Command setGoal(double goalMeters) {
    return run(() -> reachGoal(goalMeters))
        .until(() -> atHeight(goalMeters, 0.002).getAsBoolean()); // Stop when at target with 2mm tolerance
  }

  

  public void setState(ElevatorState newState){
    currState = newState;
  }



  /**
   * A trigger for when the height is at an acceptable tolerance.
   * @param heightMeters Height in meters
   * @param toleranceMeters Tolerance in meters
   * @return {@link Trigger}
   */
  public Trigger atHeight(double heightMeters, double toleranceMeters) {
    return new Trigger(() -> 
        MathUtil.isNear(heightMeters,
                       getElevatorHeightEncoder(),
                       toleranceMeters));
  }

    /**
   * @return current state of the elevator
   */

  private String getState(){
    switch(currState){
      case SETPOINT:
        return "SETPOINT";
      case ZEROED:
        return "ZEROED";
      default:
        return "DISABLED";
    }
  }

  


  /**
   * Prints data to Smart Dashboard with values in meters
   */
  public void log() {
    SmartDashboard.putNumber("Elevator Position (m)", getElevatorHeightEncoder());
    SmartDashboard.putNumber("Elevator Velocity (m/s)", getElevatorVelocity());
    SmartDashboard.putNumber("Elevator Setpoint (m)", setpoint);
    SmartDashboard.putNumber("Elevator Error (m)", setpoint - getElevatorHeightEncoder());
    SmartDashboard.putString("Elevator State", getState());
    SmartDashboard.putNumber("Motor Output", elevatorMaster.getAppliedOutput());
    SmartDashboard.putBoolean("At Min Limit", atMin.getAsBoolean());
    SmartDashboard.putBoolean("At Max Limit", atMax.getAsBoolean());
    SmartDashboard.putNumber("Current Pulled", elevatorMaster.getOutputCurrent());
    SmartDashboard.putNumber("distance in meters", getElevatorHeightLaser());

  }

  
  @Override
  public void periodic() {
    // This will continuously run the control loop
    log();
  }


}