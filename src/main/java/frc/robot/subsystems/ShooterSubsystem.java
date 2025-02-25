
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */

  private final SparkMax shooterMotor = new SparkMax(Constants.ShooterConstants.kShooterMotorID, MotorType.kBrushless);
  private final AnalogInput intakePhotoElectricSensor = new AnalogInput(Constants.ShooterConstants.intakePhotoElectricSensorID);

  public ShooterSubsystem() {}

  public void setSpeed(double speed){
    shooterMotor.set(speed);
  }

  public void runShooter() {

    System.out.println("Running runShooter");

    if(intakePhotoElectricSensor.getVoltage() > 0.5){
      System.out.println("Inning on my take");
      shooterMotor.set(0.4);
    }

    else{
      System.out.println("STOP STOP STOP");
      shooterMotor.set(0);
      
    }
    

  }

  public void stopShooter() {
    shooterMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Motor Speed", shooterMotor.get());
    SmartDashboard.putBoolean("Coral Detected", intakePhotoElectricSensor.getVoltage() < 0.5);
  }
}