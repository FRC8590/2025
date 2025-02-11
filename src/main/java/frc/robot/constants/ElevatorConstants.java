package frc.robot.constants;

import edu.wpi.first.math.util.Units;

public record ElevatorConstants(
    double gearRatio,
    double pulleyRadiusMeters,
    double carriageMassKg,
    double minHeightMeters,
    double maxHeightMeters,
    double ticksPerRevolution,
    double distancePerTick,
    PIDConstants pid,
    double bottomSetpoint,
    double midSetpoint,
    double topSetpoint,
    int masterMotorID,
    int followerMotorID,
    FFConstants feedforward,
    double maxVelocity,
    double maxAcceleration,
    double rampRate
) {
    // Nested record for PID constants
    public record PIDConstants(
        double kP,
        double kI,
        double kD
    ) {}

    // Nested record for feedforward constants
    public record FFConstants(
        double kS,
        double kG,
        double kV,
        double kA
    ) {}

    public static final ElevatorConstants DEFAULT = new ElevatorConstants(
        10.0,   // gearRatio
        Units.inchesToMeters(2), // pulleyRadiusMeters
        6.803,  // carriageMassKg
        0.0,    // minHeightMeters
        3.35,   // maxHeightMeters
        2048,   // ticksPerRevolution
        2 * Math.PI * Units.inchesToMeters(2) / 2048 / 10.0, // distancePerTick
        new PIDConstants(
            1.0,    // kP
            0.5,    // kI
            0.2     // kD
        ),
        0.0,    // bottomSetpoint
        2.0,    // midSetpoint
        3.0,    // topSetpoint
        1,      // masterMotorID
        2,      // followerMotorID
        new FFConstants(
            0.0,    // kS
            0.0,    // kG
            0.0,    // kV
            0.0     // kA
        ),
        0.0,    // maxVelocity
        0.0,    // maxAcceleration
        0.0     // rampRate
    );
}