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
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */


  SparkMaxConfig shooterConfig = new SparkMaxConfig();

  private final SparkMax shooterMotor = new SparkMax(Constants.SHOOTER_CONSTANTS.kShooterMotorID(), MotorType.kBrushless);
  private final AnalogInput firstIntakePhotoElectricSensor = new AnalogInput(Constants.SHOOTER_CONSTANTS.firstIntakePhotoElectricSensorID()); //the first sensor the coral hits
  public final AnalogInput secondIntakePhotoElectricSensor = new AnalogInput(Constants.SHOOTER_CONSTANTS.secondIntakePhotoElectricSensorID()); //the second sensor the coral hits
  public double firstIntakeVoltage, secondIntakeVoltage;

  public int counter;
  int timer = 0;

  public Shooter() {

    shooterConfig
    .inverted(true)
    .idleMode(IdleMode.kCoast)
    .smartCurrentLimit(40)
    .closedLoopRampRate(0.001);
    shooterMotor.configure(shooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void setSpeed(double speed){
    shooterMotor.set(speed);
  }

  public void updateSensorStatus(){
    firstIntakeVoltage = firstIntakePhotoElectricSensor.getVoltage();
    secondIntakeVoltage = secondIntakePhotoElectricSensor.getVoltage();
    
  }

  public void processIntakeCoral() {


      if(firstIntakeVoltage > 3){
        stopShooter(); 
      }
      else if (firstIntakeVoltage < 3 && secondIntakeVoltage > 3) {
        shooterMotor.set(0.35);

      } 
      else if (secondIntakeVoltage < 3) {
        shooterMotor.set(0.2); 


      }
      else {
        shooterMotor.set(0); // Default state - motors off
      }
  }

  public void processIntakeCoralAuto() {


    if(firstIntakeVoltage > 3 && secondIntakeVoltage > 3){
      shooterMotor.set(0.2);
    }
}


  public void ejectCoral() {

    shooterMotor.set(0.6); 
  }
  
  public void reverseCoral() {

    shooterMotor.set(-0.75);
  }

  public void reverseyCoral() {

    shooterMotor.set(-0.7);
  }


    
  public void slowCoral() {

    shooterMotor.set(0.2);
  }


  public void stopShooter() {
    shooterMotor.set(0);
  }

  public Command stop() {
    return run(() -> stopShooter()).withTimeout(0.1);
  }

  
  /**
   * A trigger for when the intake photoElectronic Sensor is triggered.
   * @return {@link Trigger}
   */
  public Trigger hasCoral() {
    return new Trigger(() -> 
        (firstIntakeVoltage > 3 && secondIntakeVoltage < 3));
  }
  

  /**
 * A trigger for when the intake photoElectronic Sensor is triggered.
 * @return {@link Trigger}
 */
  public Trigger noCoral () {
    return new Trigger(() -> 
        (firstIntakeVoltage > 3 && secondIntakeVoltage > 3));
  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run



    SmartDashboard.putNumber("Motor Speed", shooterMotor.get());
    SmartDashboard.putBoolean("Coral Detected", secondIntakePhotoElectricSensor.getVoltage() < 0.5);
    SmartDashboard.putNumber("Photo Electric Sensor", firstIntakePhotoElectricSensor.getVoltage());
    SmartDashboard.putNumber("Photo2 Electric Sensor", secondIntakePhotoElectricSensor.getVoltage());

    SmartDashboard.putBoolean("has coral", hasCoral().getAsBoolean());
    SmartDashboard.putNumber("counter", counter);
  }


  
  /**
   * Run the intake with the following logic:
   * 1. When first sensor detects coral, run intake fast
   * 2. When second sensor detects coral, slow down the intake
   * 3. When first sensor no longer detects coral, stop the intake
   * @return {@link edu.wpi.first.wpilibj2.command.Command}
   */
  public Command intakeCoral() {
    return run(() -> processIntakeCoral())
        .finallyDo(() -> stopShooter());
  }


  public Command scoreCoral(){
    return run(() -> ejectCoral())
        .until(noCoral())
        .andThen(new WaitCommand(0.2))
        .finallyDo(() -> stopShooter());
  }

  
  public Command almostEject(){
    return run(() -> slowCoral())
        .withTimeout(0.2);
  }

  public Command troughCoral(){
    return run(() -> reverseCoral())
    .until(noCoral())
    .andThen(new WaitCommand(0.2))
    .finallyDo(() -> stopShooter());
  }

  
  public Command troughyCoral(){
    return run(() -> slowCoral())
    .until(noCoral())
    .andThen(new WaitCommand(0.2))
    .finallyDo(() -> stopShooter());
  }


}
