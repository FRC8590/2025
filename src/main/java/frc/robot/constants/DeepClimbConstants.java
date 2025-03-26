package frc.robot.constants;

import frc.robot.subsystems.DeepClimb;

/**
 * Stores the values related to Deep Climb motor oporation.
 * Curently the left and right motor ID's
 */
public record DeepClimbConstants(
    int kLeftMotorID,
    int kRightMotorID
)
{
    public static final DeepClimbConstants DEFAULT = new DeepClimbConstants (
        99, // Left Motor
        98 // Right motor
    );
}