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


  SparkMaxConfig shooterConfig = new SparkMaxConfig();

  private final SparkMax shooterMotor = new SparkMax(Constants.SHOOTER_CONSTANTS.kShooterMotorID(), MotorType.kBrushless);
  private final AnalogInput secondIntakePhotoElectricSensor = new AnalogInput(Constants.SHOOTER_CONSTANTS.secondIntakePhotoElectricSensorID()); //the second sensor the coral hits
  private final AnalogInput firstIntakePhotoElectricSensor = new AnalogInput(Constants.SHOOTER_CONSTANTS.firstIntakePhotoElectricSensorID()); //the first sensor the coral hits

  public int counter;

  public Shooter() {

    shooterConfig
    .inverted(false)
    .idleMode(IdleMode.kCoast)
    .smartCurrentLimit(10)
    .closedLoopRampRate(0.1);
    shooterMotor.configure(shooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

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
        (secondIntakePhotoElectricSensor.getVoltage() < 2 && counter > 8));
  }
  



  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if(firstIntakePhotoElectricSensor.getVoltage() > 2){ //if coral is not detected by first sensor
      shooterMotor.set(0);
    }
    else if(firstIntakePhotoElectricSensor.getVoltage() < 2 && secondIntakePhotoElectricSensor.getVoltage() > 2){ //if first sensor but not second sensor
      shooterMotor.set(0.7); //run pretty fast
    }
    else if(firstIntakePhotoElectricSensor.getVoltage() < 2 && secondIntakePhotoElectricSensor.getVoltage() < 2){ //if both sensors detect coral
      shooterMotor.set(0.2); //run slow
    }
    

    SmartDashboard.putNumber("Motor Speed", shooterMotor.get());
    SmartDashboard.putBoolean("Coral Detected", secondIntakePhotoElectricSensor.getVoltage() < 0.5);
    SmartDashboard.putNumber("Photo Electric Sensor", firstIntakePhotoElectricSensor.getVoltage());

    if(secondIntakePhotoElectricSensor.getVoltage() < 2){
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
