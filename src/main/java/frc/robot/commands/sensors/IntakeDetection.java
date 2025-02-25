// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.sensors;

import frc.robot.Constants;
import frc.robot.Subsystems.ExampleSubsystem;
import frc.robot.Subsystems.sensors.PhotoelectricSensor;
import frc.robot.Subsystems.sensors.TestingMotor;

import org.opencv.photo.Photo;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** An example command that uses an example subsystem. */
public class IntakeDetection extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final TestingMotor motor;
  private final PhotoelectricSensor peSensor;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakeDetection(TestingMotor subsystem, PhotoelectricSensor sensor) {
    motor = subsystem;
    peSensor = sensor;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(peSensor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("pe value", peSensor.getVoltage());
    if(peSensor.hasDetection()){
      motor.stop();
    }
    else{
      motor.runAt(0.2);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    motor.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
