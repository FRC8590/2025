package frc.robot.subsystems.sensors;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DistanceSensor extends SubsystemBase{
    private AnalogInput distanceSensor;

    public DistanceSensor (int channel){
        distanceSensor = new AnalogInput(channel);

    }

    /**
        Get a scaled sample straight from this channel. The value is scaled to units of Volts using the calibrated scaling data from getLSBWeight() and getOffset().
        @return A scaled sample straight from this channel.
     */
    public double getVoltage(){
        return distanceSensor.getVoltage();
    }

     /**
        Get a distance scaled value converted from getVoltage();
        @return A scaled value for the distance from the closest object in meters
     */
    public double getDistanceInMeters(){
        double distance = (distanceSensor.getVoltage() - 0.293)/0.977;
        return distance;
    }

    
     /**
        Get a distance scaled value converted from getVoltage();
        @return A scaled value for the distance from the closest object in feet
     */
    public double getDistanceInFeet(){
        return Units.metersToFeet(getDistanceInMeters());
    }

    /**
        Returns whether the sensor has an object in front of it.
        @return Whether the sensor voltage is above 0.5.
     */

    public boolean hasDetection(){
        return getDistanceInFeet() < 0.5;
    }

}
