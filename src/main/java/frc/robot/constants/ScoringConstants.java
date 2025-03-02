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
        new Pose2d(new Translation2d(3.50, 4.75), new Rotation2d(Units.degreesToRadians(-57))),
        // new Pose2d(new Translation2d(3.97, 5.73), new Rotation2d(Units.degreesToRadians(-58))),
        new Pose2d(new Translation2d(3.93, 5.10), new Rotation2d(Units.degreesToRadians(-58))),
        new Pose2d(new Translation2d(4.71,5.23), new Rotation2d(Units.degreesToRadians(-115))), //3
        new Pose2d(new Translation2d(5.14,5.02), new Rotation2d(Units.degreesToRadians(-116))) //4
    );
} 