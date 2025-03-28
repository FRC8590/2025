package frc.robot.constants;

import frc.robot.subsystems.Shooter;

public record DeepClimbConstants(
    int frontMotorID,
    int backMotorID
    ) {
    public static final DeepClimbConstants DEFAULT = new DeepClimbConstants(
        15,
        14
    );
}
