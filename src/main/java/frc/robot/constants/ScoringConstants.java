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
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
        new Pose2d(new Translation2d(3.90, 5.02), new Rotation2d(Units.degreesToRadians(-68))),
        new Pose2d(new Translation2d(3.50,4.66), new Rotation2d(Units.degreesToRadians(-57))),
        new Pose2d(new Translation2d(4.70,5.25), new Rotation2d(Units.degreesToRadians(-117))), //3
        new Pose2d(new Translation2d(5.13,5.04), new Rotation2d(Units.degreesToRadians(-117))) //4
=======
>>>>>>> Stashed changes
        new Pose2d(new Translation2d(3.30, 5.08), new Rotation2d(Units.degreesToRadians(-57))),
        new Pose2d(new Translation2d(3.94, 4.68), new Rotation2d(Units.degreesToRadians(-54))),
        new Pose2d(new Translation2d(4.85,5.59), new Rotation2d(Units.degreesToRadians(-119))),
        new Pose2d(new Translation2d(4.87,4.91), new Rotation2d(Units.degreesToRadians(-124)))
<<<<<<< Updated upstream
=======
>>>>>>> 18a407efe4e55263a92be4e4299ac0cd60299663
>>>>>>> Stashed changes
    );
} 