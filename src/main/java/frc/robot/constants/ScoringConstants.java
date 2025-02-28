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
        new Pose2d(new Translation2d(3.90, 5.02), new Rotation2d(Units.degreesToRadians(-68))),
        new Pose2d(new Translation2d(3.50,4.66), new Rotation2d(Units.degreesToRadians(-57))),
        new Pose2d(new Translation2d(4.54,5.23), new Rotation2d(Units.degreesToRadians(-117))),
        new Pose2d(new Translation2d(5.11,4.97), new Rotation2d(Units.degreesToRadians(-133)))
    );
} 