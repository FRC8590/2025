package frc.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotoelectricSensor extends SubsystemBase {
    private AnalogInput peSensor;

    public PhotoelectricSensor (int channel){
        peSensor = new AnalogInput(channel);

    }

    /**
        Get a scaled sample straight from this channel. The value is scaled to units of Volts using the calibrated scaling data from getLSBWeight() and getOffset().
        @return A scaled sample straight from this channel.
     */
    public double getVoltage(){
        return peSensor.getVoltage();
    }

    /**
        Returns whether the sensor has an object in front of it.
        @return Whether the sensor voltage is above 0.5.
     */

    public boolean hasDetection(){
        return peSensor.getVoltage() < 1;
    }

}
