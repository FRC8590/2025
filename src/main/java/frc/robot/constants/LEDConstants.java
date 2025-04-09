package frc.robot.constants;

public record LEDConstants(
    int LEDPort,
    int length
) {
    public static final LEDConstants DEFAULT = new LEDConstants(
        0,    // LED Port
        169
    );
} 