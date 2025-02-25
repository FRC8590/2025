// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */


  SparkMaxConfig masterConfig = new SparkMaxConfig();

  private final SparkMax shooterMotor = new SparkMax(Constants.SHOOTER_CONSTANTS.kShooterMotorID(), MotorType.kBrushless);
  private final AnalogInput intakePhotoElectricSensor = new AnalogInput(Constants.SHOOTER_CONSTANTS.intakePhotoElectricSensorID());

  public int counter;
  public Shooter() {

    masterConfig
    .inverted(false)
    .idleMode(IdleMode.kCoast)
    .smartCurrentLimit(10)
    .closedLoopRampRate(0.1);
    shooterMotor.configure(masterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void setSpeed(double speed){
    shooterMotor.set(speed);
  }

  public void runShooter() {

    shooterMotor.set(0.2);

  }

  public void stopShooter() {
    shooterMotor.set(0);
    counter = 0;
  }


  
  /**
   * A trigger for when the intake photoElectronic Sensor is triggered.
   * @return {@link Trigger}
   */
  public Trigger hasCoral() {
    return new Trigger(() -> 
        (intakePhotoElectricSensor.getVoltage() < 2 && counter > 8));
  }
  



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Motor Speed", shooterMotor.get());
    SmartDashboard.putBoolean("Coral Detected", intakePhotoElectricSensor.getVoltage() < 0.5);
    SmartDashboard.putNumber("Photo Electric Sensor", intakePhotoElectricSensor.getVoltage());

    if(intakePhotoElectricSensor.getVoltage() < 2){
      counter++;
    }
  }


  
  /**
   * Run the intake until stopped
   * @return {@link edu.wpi.first.wpilibj2.command.Command}
   */
  public Command intakeCoral() {
    return run(() -> runShooter())
        .until(() -> hasCoral().getAsBoolean())
        .andThen(() -> stopShooter());
  }


  public Command scoreCoral(){
    return run(() -> runShooter());
  }


}
