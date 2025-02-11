package frc.robot.constants;

public record IntakeConstants(
    int motorID,
    double speed
) {
    public IntakeConstants {
        if (speed < -1.0 || speed > 1.0) {
            throw new IllegalArgumentException("Speed must be between -1.0 and 1.0");
        }
        if (motorID < 0) {
            throw new IllegalArgumentException("Motor ID must be positive");
        }
    }

    public static final IntakeConstants DEFAULT = new IntakeConstants(
        3,      // kIntakeMotorID (from old Passthrough)
        0.73    // kIntakeSpeed (from old Passthrough)
    );
} 