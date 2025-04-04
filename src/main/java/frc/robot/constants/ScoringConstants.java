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
        new Pose2d(new Translation2d(4.17, 2.77), new Rotation2d(Units.degreesToRadians(61.2))),
        new Pose2d(new Translation2d(3.89, 2.94), new Rotation2d(Units.degreesToRadians(60.6))),
        //18
        new Pose2d(new Translation2d(3.24, 3.67), new Rotation2d(Units.degreesToRadians(1.3))),
        new Pose2d(new Translation2d(3.24, 4.00), new Rotation2d(Units.degreesToRadians(0.7))),
        //19
        new Pose2d(new Translation2d(3.56, 4.93), new Rotation2d(Units.degreesToRadians(-57.8))),
        new Pose2d(new Translation2d(3.84, 5.09), new Rotation2d(Units.degreesToRadians(-58.4))),
        //20
        new Pose2d(new Translation2d(4.85, 5.26), new Rotation2d(Units.degreesToRadians(-119.9))),
        new Pose2d(new Translation2d(5.13, 5.09), new Rotation2d(Units.degreesToRadians(-120.5))),
        //21
        new Pose2d(new Translation2d(5.74, 4.38), new Rotation2d(Units.degreesToRadians(-177.9))),
        new Pose2d(new Translation2d(5.74, 4.05), new Rotation2d(Units.degreesToRadians(-178.5))),
        //22
        new Pose2d(new Translation2d(5.42, 3.12), new Rotation2d(Units.degreesToRadians(121.7))),
        new Pose2d(new Translation2d(5.14, 2.96), new Rotation2d(Units.degreesToRadians(121.1))),
        //6
        new Pose2d(new Translation2d(13.99, 3.13), new Rotation2d(Units.degreesToRadians(121.9))),
        new Pose2d(new Translation2d(13.71, 2.97), new Rotation2d(Units.degreesToRadians(121.3))),
        //7
        new Pose2d(new Translation2d(14.31, 4.38), new Rotation2d(Units.degreesToRadians(-178.4))),
        new Pose2d(new Translation2d(14.31, 4.05), new Rotation2d(Units.degreesToRadians(-179.0))),
        //8
        new Pose2d(new Translation2d(13.37, 5.28), new Rotation2d(Units.degreesToRadians(-118.1))),
        new Pose2d(new Translation2d(13.66, 5.12), new Rotation2d(Units.degreesToRadians(-118.7))),
        //9
        new Pose2d(new Translation2d(12.13, 4.93), new Rotation2d(Units.degreesToRadians(-58.1))),
        new Pose2d(new Translation2d(12.41, 5.09), new Rotation2d(Units.degreesToRadians(-58.7))),
        //10
        new Pose2d(new Translation2d(11.82, 3.67), new Rotation2d(Units.degreesToRadians(2.1))),
        new Pose2d(new Translation2d(11.81, 4.00), new Rotation2d(Units.degreesToRadians(1.5))),
        //11
        new Pose2d(new Translation2d(12.76, 2.77), new Rotation2d(Units.degreesToRadians(62.3))),
        new Pose2d(new Translation2d(12.47, 2.93), new Rotation2d(Units.degreesToRadians(61.7)))
    );
}