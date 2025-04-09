package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public record ScoringConstants(
    Pose2d right17,
    Pose2d left17,

    Pose2d right18,
    Pose2d left18,

    Pose2d right19,
    Pose2d left19,

    Pose2d right20,
    Pose2d left20,

    Pose2d right21,
    Pose2d left21,

    Pose2d right22,
    Pose2d left22,

    Pose2d right6,
    Pose2d left6,

    Pose2d right7,
    Pose2d left7,

    Pose2d right8,
    Pose2d left8,

    Pose2d right9,
    Pose2d left9,

    Pose2d right10,
    Pose2d left10,

    Pose2d right11,
    Pose2d left11

) {
    public static final ScoringConstants DEFAULT = new ScoringConstants(
        //17
        new Pose2d(new Translation2d(4.14, 2.79), new Rotation2d(Units.degreesToRadians(60.6))),
        new Pose2d(new Translation2d(3.86, 2.96), new Rotation2d(Units.degreesToRadians(59.0))),
        //18
        new Pose2d(new Translation2d(3.24, 3.72), new Rotation2d(Units.degreesToRadians(0.7))),
        new Pose2d(new Translation2d(3.25, 4.05), new Rotation2d(Units.degreesToRadians(-0.9))),
        //19
        new Pose2d(new Translation2d(3.59, 4.95), new Rotation2d(Units.degreesToRadians(-58.5))),
        new Pose2d(new Translation2d(3.88, 5.11), new Rotation2d(Units.degreesToRadians(-60.1))),
        //20
        new Pose2d(new Translation2d(4.89, 5.24), new Rotation2d(Units.degreesToRadians(-120.5))),
        new Pose2d(new Translation2d(5.16, 5.06), new Rotation2d(Units.degreesToRadians(-122.1))),
        //21
        new Pose2d(new Translation2d(5.74, 4.34), new Rotation2d(Units.degreesToRadians(-178.5))),
        new Pose2d(new Translation2d(5.74, 4.01), new Rotation2d(Units.degreesToRadians(179.9))),
        //22
        new Pose2d(new Translation2d(5.39, 3.10), new Rotation2d(Units.degreesToRadians(121.1))),
        new Pose2d(new Translation2d(5.10, 2.94), new Rotation2d(Units.degreesToRadians(119.5))),
        //6
        new Pose2d(new Translation2d(13.96, 3.11), new Rotation2d(Units.degreesToRadians(121.3))),
        new Pose2d(new Translation2d(13.67, 2.95), new Rotation2d(Units.degreesToRadians(119.7))),
        //7
        new Pose2d(new Translation2d(14.31, 4.34), new Rotation2d(Units.degreesToRadians(-179.0))),
        new Pose2d(new Translation2d(14.30, 4.01), new Rotation2d(Units.degreesToRadians(179.4))),
        //8
        new Pose2d(new Translation2d(13.41, 5.26), new Rotation2d(Units.degreesToRadians(-118.7))),
        new Pose2d(new Translation2d(13.69, 5.09), new Rotation2d(Units.degreesToRadians(-120.3))),
        //9
        new Pose2d(new Translation2d(12.16, 4.95), new Rotation2d(Units.degreesToRadians(-58.7))),
        new Pose2d(new Translation2d(12.45, 5.11), new Rotation2d(Units.degreesToRadians(-60.3))),
        //10
        new Pose2d(new Translation2d(11.81, 3.71), new Rotation2d(Units.degreesToRadians(1.5))),
        new Pose2d(new Translation2d(11.82, 4.04), new Rotation2d(Units.degreesToRadians(-0.1))),
        //11
        new Pose2d(new Translation2d(12.72, 2.79), new Rotation2d(Units.degreesToRadians(61.7))),
        new Pose2d(new Translation2d(12.44, 2.96), new Rotation2d(Units.degreesToRadians(60.1)))
    );
}