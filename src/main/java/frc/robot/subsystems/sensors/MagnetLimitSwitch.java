package frc.robot.Subsystems.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MagnetLimitSwitch extends SubsystemBase{
    private DigitalInput  magSwitch;

    public MagnetLimitSwitch (int channel){
        magSwitch = new DigitalInput(channel);

    }


  /**
   * Get the value from a digital input channel. Retrieve the value of a single digital input
   * channel from the FPGA.
   *
   * @return the status of the digital input
   */
  public boolean getOpen(){
        return !magSwitch.get();
    }

}
