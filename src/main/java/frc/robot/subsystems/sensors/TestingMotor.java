package frc.robot.Subsystems.sensors;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.AlternateEncoderConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestingMotor extends SubsystemBase{

    public SparkMax motor;
    private RelativeEncoder tbEncoder;

    public TestingMotor (int id){
        motor = new SparkMax(id, MotorType.kBrushless);

        configureMotor();

    }

    private void configureMotor(){
        SparkMaxConfig config = new SparkMaxConfig();
        // config.limitSwitch.forwardLimitSwitchEnabled(true);
        // config.limitSwitch.reverseLimitSwitchEnabled(false);
        // config.limitSwitch.forwardLimitSwitchType(Type.kNormallyClosed);

        config.idleMode(IdleMode.kBrake);
        
        config.smartCurrentLimit(20);
        config.alternateEncoder.setSparkMaxDataPortConfig();
        
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        tbEncoder = motor.getAlternateEncoder(); //this is for the shaft encoder we are going to put on the spark



    }

    /**
   * Common interface for getting the current set speed of a speed controller.
   *
   * @return The current set speed. Value is between -1.0 and 1.0.
   */

    public double getSpeed(){
        return motor.get();
    }


  /** ** Speed Controller Interface *** */
  /**
   * Common interface for setting the speed of a speed controller.
   *
   * @param speed The speed to set. Value should be between -1.0 and 1.0.
   */
    public void runAt(double speed){
        motor.set(speed);
    }

    public void stop(){
        motor.set(0);
    }
    

    /**
     *
     *
     * @return The counts of the relative through bore encoder since startup.
     */
    public double getRotations(){
        return tbEncoder.getPosition();
    }


    /**
     * Returns {@code true} if the limit switch is pressed, based on the selected polarity.
     *
     * <p>This method works even if the limit switch is not enabled for controller shutdown.
     *
     * @return {@code true} if the limit switch is pressed
     */
    public boolean limitSwitchPressed(){
        return motor.getForwardLimitSwitch().isPressed();
    }


    @Override
    public void periodic(){
        SmartDashboard.putNumber("rotations", getRotations());
    }

}
