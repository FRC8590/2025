// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.sensors;

import javax.naming.spi.DirStateFactory;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.ExampleSubsystem;
import frc.robot.Subsystems.sensors.DistanceSensor;
import frc.robot.Subsystems.sensors.TestingMotor;

/** An example command that uses an example subsystem. */
public class DistanceSensorCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final TestingMotor motor;
  private final DistanceSensor distanceSensor;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DistanceSensorCommand(TestingMotor subsystem, DistanceSensor sensor) {
    motor = subsystem;
    distanceSensor = sensor;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(sensor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Distance", distanceSensor.getVoltage());
    if(distanceSensor.hasDetection()){
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
