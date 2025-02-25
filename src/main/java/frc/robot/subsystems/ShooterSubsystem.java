// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

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

  public int delay;
  public ShooterSubsystem() {}

  public void setSpeed(double speed){
    shooterMotor.set(speed);
  }

  public void runShooter() {

    if(intakePhotoElectricSensor.getVoltage() < 2){
      delay++;
      System.out.println(delay);
      if(delay > 8){
        shooterMotor.set(0);
        System.out.println("STOPPING");
        return;
      }
    }

    shooterMotor.set(0.2);


    

  }

  public void stopShooter() {
    shooterMotor.set(0);
  }

  public boolean getFinished(){
    return delay > 8;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Motor Speed", shooterMotor.get());
    SmartDashboard.putBoolean("Coral Detected", intakePhotoElectricSensor.getVoltage() < 0.5);
    SmartDashboard.putNumber("Photo Electric Sensor", intakePhotoElectricSensor.getVoltage());
  }

}
