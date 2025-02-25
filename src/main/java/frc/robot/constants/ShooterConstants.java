package frc.robot.constants;

import frc.robot.subsystems.Shooter;

public record ShooterConstants(
    int kShooterMotorID,
    int intakePhotoElectricSensorID
    ) {
    public static final ShooterConstants DEFAULT = new ShooterConstants(
        11,
        0
    );
}
