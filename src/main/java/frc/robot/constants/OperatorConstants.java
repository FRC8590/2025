package frc.robot.constants;

public record OperatorConstants(
    double deadband,
    double leftYDeadband,
    double rightXDeadband,
    double turnConstant
) {
    public static final OperatorConstants DEFAULT = new OperatorConstants(
        0.05,    // deadband
        0.05,    // leftYDeadband
        0.05,    // rightXDeadband
        6.0     // turnConstant
    );
} 