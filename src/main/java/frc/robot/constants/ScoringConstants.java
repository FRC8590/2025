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
        new Pose2d(new Translation2d(3.47, 4.74 ), new Rotation2d(Units.degreesToRadians(-59))), //1
        new Pose2d(new Translation2d(3.91, 5.06), new Rotation2d(Units.degreesToRadians(-58))), //2
        new Pose2d(new Translation2d(4.74,5.25), new Rotation2d(Units.degreesToRadians(-117))), //3
        new Pose2d(new Translation2d(5.13,5.04), new Rotation2d(Units.degreesToRadians(
            -117))) //4
    );
} 