
package frc.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ThroughBoreEncoder extends SubsystemBase {
    private final Encoder encoder;
    private static final int SLOT_A = 1;
    private static final int SLOT_B = 2;
    private static final int SLOT_I = 3;
    private double offset = 0;

    // Constants for conversion
    private static final double PULSES_PER_REVOLUTION = 2048.0; 
    private static final double DEGREES_PER_PULSE = 360.0 / PULSES_PER_REVOLUTION;
    
    private double previousDistance = 0;
    private double totalDistance = 0;
    
    private final Notifier notifier;
    private static final double UPDATE_FREQUENCY = 0.002;
    
    public ThroughBoreEncoder() {
        encoder = new Encoder(SLOT_A, SLOT_B, SLOT_I);

        encoder.setDistancePerPulse(DEGREES_PER_PULSE);
        encoder.setReverseDirection(false);
        
        notifier = new Notifier(this::updatePosition);
        notifier.startPeriodic(UPDATE_FREQUENCY);
        
    }

    
    /***
     * Updates the current position.
     * If the limit switch is pressed, reset the current position.
     */
    private void updatePosition() {
        if(Constants.testingMotor.limitSwitchPressed()){
            reset();
            return;
        }

        double currentDistance = encoder.getDistance();
        double delta = currentDistance - previousDistance;
        
        if (delta < -180) {
            totalDistance += 360;
        }
        else if (delta > 180) {
            totalDistance -= 360;
        }
        
        previousDistance = currentDistance;

    }

    /**
     * Gets the current angle in degrees (0-360)
     * @return current angle in degrees
     */
    public double getCurrentAngle() {
        return encoder.getDistance();
    }

    public void reset(){
        encoder.reset();

    }

    /**
     * Gets the total number of rotations including partial rotations
     * @return total rotations (can be positive or negative)
     */
    public double getTotalRotations() {
        return ((totalDistance + encoder.getDistance())/360);
    }

    /**
     * Gets the total angle in degrees (accumulates beyond 360)
     * @return total degrees traveled
     */
    public double getDistance() {
        return totalDistance + encoder.getDistance();
    }

    public void pushValues() {
        SmartDashboard.putNumber("getCurrent", getCurrentAngle());
        SmartDashboard.putNumber("getTotalRotations", getTotalRotations());
    }

    public void close() {
        notifier.close();
    }
}