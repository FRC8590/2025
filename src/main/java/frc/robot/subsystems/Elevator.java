// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

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
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.MAXMotionConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
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
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.MutAngle;
import edu.wpi.first.units.measure.MutDistance;
import edu.wpi.first.units.measure.MutLinearVelocity;
import edu.wpi.first.units.measure.MutVoltage;

import frc.robot.Constants.ElevatorState;

import java.util.function.BooleanSupplier;

public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */
  

  public final Trigger atMin = new Trigger((BooleanSupplier) () -> 
      getElevatorHeight() <= Constants.ElevatorConstants.kMinHeight + 3.0);

  public final Trigger atMax = new Trigger((BooleanSupplier) () -> 
      getElevatorHeight() >= Constants.ElevatorConstants.kMinHeight - 3.0);

  private SparkMax elevatorMaster, elevatorFollower;
  private RelativeEncoder tbEncoder;

  private double motorOutput;
  private double setpoint;

  ShuffleboardTab tab;

  double[] measurementStdDevs = {0.0, 0.0};

    ElevatorFeedforward m_feedforward =
    new ElevatorFeedforward(
        ElevatorConstants.kElevatorkS,
        ElevatorConstants.kElevatorkG,
        ElevatorConstants.kElevatorkV,
        ElevatorConstants.kElevatorkA);

    private final ProfiledPIDController m_controller = new ProfiledPIDController(ElevatorConstants.kElevatorKp,
                                                                                ElevatorConstants.kElevatorKi,
                                                                                ElevatorConstants.kElevatorKd,
                                                                                new Constraints(ElevatorConstants.kMaxVelocity,
                                                                                                ElevatorConstants.kMaxAcceleration));



                                                                                                  // SysId Routine and seutp
  // Mutable holder for unit-safe voltage values, persisted to avoid reallocation.
  private final MutVoltage        m_appliedVoltage = Volts.mutable(0);
  // Mutable holder for unit-safe linear distance values, persisted to avoid reallocation.
  private final MutDistance       m_distance       = Meters.mutable(0);
  private final MutAngle          m_rotations      = Rotations.mutable(0);
  // Mutable holder for unit-safe linear velocity values, persisted to avoid reallocation.
  private final MutLinearVelocity m_velocity       = MetersPerSecond.mutable(0);
  // SysID Routine
  private final SysIdRoutine      m_sysIdRoutine   =
      new SysIdRoutine(
          // Empty config defaults to 1 volt/second ramp rate and 7 volt step voltage.
          new SysIdRoutine.Config(Volts.per(Second).of(1),
                                  Volts.of(7),
                                  Seconds.of(10)),
          new SysIdRoutine.Mechanism(
              // Tell SysId how to plumb the driving voltage to the motor(s).
              elevatorFollower::setVoltage,
              // Tell SysId how to record a frame of data for each motor on the mechanism being
              // characterized.
              log -> {
                // Record a frame for the shooter motor.
                log.motor("elevator")
                   .voltage(
                       m_appliedVoltage.mut_replace(
                        elevatorFollower.getAppliedOutput() * RobotController.getBatteryVoltage(), Volts))
                   .linearPosition(m_distance.mut_replace(getElevatorHeight(),
                                                          Meters)) // Records Height in Meters via SysIdRoutineLog.linearPosition
                   .linearVelocity(m_velocity.mut_replace(getElevatorVelocity(),
                                                          MetersPerSecond)); // Records velocity in MetersPerSecond via SysIdRoutineLog.linearVelocity
              },
              this));

  

  private ElevatorState currState;
 

  public Elevator() {

    //create both sparkmaxes
    elevatorMaster = new SparkMax(Constants.ElevatorConstants.kElevatorMasterID, MotorType.kBrushless);
    elevatorFollower = new SparkMax(Constants.ElevatorConstants.kElevatorFollowerID, MotorType.kBrushless);

    SparkMaxConfig elevatorMasterConfig  = new SparkMaxConfig();
    SparkMaxConfig elevatorFollowerConfig  = new SparkMaxConfig();

    // SparkClosedLoopController pidController = elevatorMaster.getClosedLoopController();

    //create configs for both. The master spark is running with the external alternate encoder.
    elevatorMasterConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(10)
        .closedLoopRampRate(ElevatorConstants.kElevatorRampRate);
    elevatorMasterConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kAlternateOrExternalEncoder);
    elevatorMasterConfig.limitSwitch
        .forwardLimitSwitchEnabled(true)
        .reverseLimitSwitchEnabled(false)
        .forwardLimitSwitchType(Type.kNormallyClosed);

    //explore smart motion stuff tomorrow
    // elevatorMasterConfig.closedLoop.smartMotion
    elevatorFollowerConfig.follow(elevatorMaster);





    //config both elevator sparks with configs we just created
    elevatorMaster.configure(elevatorMasterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    elevatorFollower.configure(elevatorFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    //setup the external encoder
    tbEncoder = elevatorMaster.getAlternateEncoder();



    setState(ElevatorState.DISABLED);
  }

  
    /**
   * Run control loop to reach and maintain goal.
   *
   * @param goal the position to maintain
   */
  public void reachGoal(double goal) {
    // Safety checks
    if (getLimitSwitchStatus() || 
        getElevatorHeight() <= Constants.ElevatorConstants.kBottomSetpoint || 
        goal <= Constants.ElevatorConstants.kBottomSetpoint) {
      setState(ElevatorState.ZEROED);
      return;
    }

    if (goal < Constants.ElevatorConstants.kBottomSetpoint || 
        goal > Constants.ElevatorConstants.kTopSetpoint) {
      setState(ElevatorState.DISABLED);
      System.out.println("SOMETHING IS WRONG. CHECK CODE NOW");
      return;
    }

    setState(ElevatorState.SETPOINT);

    // Original control logic
    double voltsOut = MathUtil.clamp(
        m_controller.calculate(getElevatorHeight(), goal) +
        m_feedforward.calculateWithVelocities(getElevatorVelocity(),
                                              m_controller.getSetpoint().velocity), -7, 7);
    elevatorMaster.setVoltage(voltsOut);
  }



  /**
   * Returns {@code true} if the limit switch is pressed, based on the selected polarity.
   *
   * <p>This method works even if the limit switch is not enabled for controller shutdown.
   *
   * @return {@code true} if the limit switch is pressed
   */
  public boolean getLimitSwitchStatus(){
    return elevatorMaster.getForwardLimitSwitch().isPressed();
  }






  /**
   * Returns the height of the elevator currently based on the rotations of the elevator shaft
   * @return the rotations of the alternate encoder multiplied by the {@link Constants.Elevator} kDistancePeraTick
   */
  public double getElevatorHeight(){
    return tbEncoder.getPosition() * Constants.ElevatorConstants.kDistancePerTick;
  }

  /**
 * Returns the height of the elevator currently based on the rotations of the elevator shaft
 * @return the rotations of the alternate encoder multiplied by the {@link Constants.Elevator} kDistancePeraTick
 */
  public double getElevatorVelocity(){
    return tbEncoder.getVelocity() * Constants.ElevatorConstants.kDistancePerTick;
  }



  /**
   * Set the goal of the elevator. Can be used to directly set goal of the elevator as a command
   *
   * @param goal Goal in meters
   * @return {@link edu.wpi.first.wpilibj2.command.Command}
   */
  public Command setGoal(double goal)
  {
    return run(() -> reachGoal(goal));
  }

  

  public void setState(ElevatorState newState){
    currState = newState;
  }



  /**
   * A trigger for when the height is at an acceptable tolerance.
   *
   * @param height    Height in Meters
   * @param tolerance Tolerance in meters.
   * @return {@link Trigger}
   */
  public Trigger atHeight(double height, double tolerance)
  {
    return new Trigger((BooleanSupplier) () -> MathUtil.isNear(height,
                                             getElevatorHeight(),
                                             tolerance));
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
   * Prints data to Smart Dashboard
   */
  public void log(){
    SmartDashboard.putNumber("Motor Output [-1, 1]", motorOutput);
    SmartDashboard.putNumber("Elevator Position", getElevatorHeight());
    SmartDashboard.putNumber("Elevator Velocity", getElevatorVelocity());
    SmartDashboard.putNumber("Setpoint", setpoint);
    SmartDashboard.putNumber("Error", setpoint - getElevatorHeight());
    SmartDashboard.putBoolean("Bottom Limit Switch", getLimitSwitchStatus());
    SmartDashboard.putString("Current State", getState());

    
  }

}
