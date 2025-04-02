// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import org.ironmaple.simulation.drivesims.COTS;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.Units.*;
import frc.robot.Constants;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */
  
  //LED Objects
  private AddressableLED m_LED = new AddressableLED(Constants.LED_CONSTANTS.LEDPort());
  private AddressableLEDBuffer m_LEDBuffer = new AddressableLEDBuffer(Constants.LED_CONSTANTS.length());
  private LEDPattern desiredPattern = LEDPattern.solid(Color.kRed);

  public String currentColor;

  //LED Configurations
  private final Distance kLedSpacing = Meters.of(1 / 120.0);


  //LED States
  private final LEDPattern fullRed = LEDPattern.solid(Color.kRed);
  private final LEDPattern fullGreen = LEDPattern.solid(Color.kGreen);
  private final LEDPattern fullYellow = LEDPattern.solid(Color.kYellow);
  private final LEDPattern rainbow = LEDPattern.rainbow(255, 128);
  private final LEDPattern scrollingRainbow = rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), kLedSpacing);
  private LEDPattern yellowPurple = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kYellow, Color.kPurple);
  private final LEDPattern lebron = yellowPurple.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), kLedSpacing);

  public LEDSubsystem(){
    m_LED.setLength(m_LEDBuffer.getLength());
    m_LED.setData(m_LEDBuffer);
    m_LED.start();
    currentColor = "black";
  
  }

  public void setRed(){
    desiredPattern = fullRed;
    currentColor = "red";
  }
  public void setGreen(){
    desiredPattern = fullGreen;
    currentColor = "green";
  }
  public void setYellow(){
    desiredPattern = fullYellow;
    currentColor = "yellow";
  }
  public void setRainbow(){
    desiredPattern = scrollingRainbow;
  }

  public void setGOAT(){
    desiredPattern = lebron;
    currentColor = "lebron";
  }

  public LEDPattern getPattern(){
    return desiredPattern;
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    if(desiredPattern.equals(fullYellow)){

      fullYellow.applyTo(m_LEDBuffer);
      m_LED.setData(m_LEDBuffer);

    }
    else if(desiredPattern.equals(scrollingRainbow)){

      scrollingRainbow.applyTo(m_LEDBuffer);
      m_LED.setData(m_LEDBuffer);

    }
    else if(desiredPattern.equals(fullRed)){

      fullRed.applyTo(m_LEDBuffer);
      m_LED.setData(m_LEDBuffer);

    }
    else if(desiredPattern.equals(fullGreen)){

      fullGreen.applyTo(m_LEDBuffer);
      m_LED.setData(m_LEDBuffer);

    }
    else if(desiredPattern.equals(lebron)){

      lebron.applyTo(m_LEDBuffer);
      m_LED.setData(m_LEDBuffer);

    }

  }
}
