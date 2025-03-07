package frc.robot.constants;

public record AlgaeRemoverConstants(
    int pivotMotorID,
    double kP,
    double kI,
    double kD,
    double maxAcceleration,
    double maxVelocity,
    double activeGoal,
    double inactiveGoal,
    int removerID,
    double rampRate,
    double distancePerRotation
) {
    public static final AlgaeRemoverConstants DEFAULT = new AlgaeRemoverConstants(
        12,     // pivotMotorID
        1,    // kP
        0,   // kI
        0,  // kD
        5.0,    // maxAcceleration
        10.0,   // maxVelocity
    5,   // activeGoal
        0.00,   // inactiveGoal
        13,     // thirteenthReasonID
        0.1,    // rampRate
        1.0     // distancePerRotation
    );
    
    public record PIDConstants(
        double kP,
        double kI,
        double kD
    ) {
        public static final PIDConstants DEFAULT = new PIDConstants(0.6, 0, 0);
    }
    
    public record FeedforwardConstants(
        double kS,
        double kG,
        double kV,
        double kA
    ) {
        public static final FeedforwardConstants DEFAULT = new FeedforwardConstants(0.0, 0, 0, 0.0);
    }
}
