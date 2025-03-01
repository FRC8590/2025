package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public record ScoringConstants(
    Pose2d locationOne,
    Pose2d locationTwo, 
    Pose2d locationThree, 
    Pose2d locationFour
) {

    public static final ScoringConstants DEFAULT = new ScoringConstants(
        new Pose2d(new Translation2d(3.46, 4.73), new Rotation2d(Units.degreesToRadians(-56))),
        new Pose2d(new Translation2d(3.86, 5.11), new Rotation2d(Units.degreesToRadians(-55))),
        new Pose2d(new Translation2d(4.70,5.25), new Rotation2d(Units.degreesToRadians(-117))), //3
        new Pose2d(new Translation2d(5.13,5.04), new Rotation2d(Units.degreesToRadians(-117))) //4
    );
} 