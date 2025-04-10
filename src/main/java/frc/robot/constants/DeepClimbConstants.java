package frc.robot.constants;

public record DeepClimbConstants(
    int kFrontMotorID,
    int kBackMotorID
) {
    public static final DeepClimbConstants DEFAULT = new DeepClimbConstants(
        14,      // kIntakeMotorID (from old Passthrough)
        15    // kIntakeSpeed (from old Passthrough)
    );
} 