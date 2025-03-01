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
        new Pose2d(new Translation2d(3.30, 5.08), new Rotation2d(Units.degreesToRadians(-57))),
        new Pose2d(new Translation2d(3.94, 4.68), new Rotation2d(Units.degreesToRadians(-54))),
        new Pose2d(new Translation2d(4.85,5.59), new Rotation2d(Units.degreesToRadians(-119))),
        new Pose2d(new Translation2d(4.87,4.91), new Rotation2d(Units.degreesToRadians(-124)))
    );
} 