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

/**
 * Deep climb command
* used to activate and deactivate the deep climb motors,
* as well as change their direction
*/
public class DeepClimb extends SubsystemBase
{
    // Initiates the motors' config options
    private final SparkMaxConfig motorConfig = new SparkMaxConfig();

    /**
     * Creates 2 new motors,
     * with the ID's from DeepClimbConstants.java,
     * Not configured yet
     */
    private final SparkMax leftMotor = new SparkMax(Constants.DEEP_CLIMB_CONSTANTS.kLeftMotorID(), MotorType.kBrushless);
    private final SparkMax rightMotor = new SparkMax(Constants.DEEP_CLIMB_CONSTANTS.kRightMotorID(), MotorType.kBrushless);

    public DeepClimb ()
    {
        // Configure for the left motor
        motorConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(10)
            .closedLoopRampRate(0.1);

        // Aply config to the left motor
        leftMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Invert the direction before aplying to right motor 
        motorConfig.inverted(true);

        // Aply config to the right motor
        rightMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    // Used to set the speeds of the motors
    public void setSpeed (double speed)
    {
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    // Stop the motors
    public void stopClimb ()
    {
        leftMotor.set(0);
        rightMotor.set(0);
    }

    public Command climbUp ()
    {
        return run(() -> setSpeed(0.5));
    }

    public Command climbDown ()
    {
        return run(() -> setSpeed(-0.5));
    }

    public Command stop ()
    {
        return run(() -> stopClimb());
    }
}