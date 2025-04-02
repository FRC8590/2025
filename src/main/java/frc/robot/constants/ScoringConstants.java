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
        new Pose2d(new Translation2d(4.14, 2.79), new Rotation2d(Units.degreesToRadians(61.6))),
        new Pose2d(new Translation2d(3.85, 2.94), new Rotation2d(Units.degreesToRadians(61.5))),
        //18
        new Pose2d(new Translation2d(3.24, 3.71), new Rotation2d(Units.degreesToRadians(1.7))),
        new Pose2d(new Translation2d(3.23, 4.04), new Rotation2d(Units.degreesToRadians(1.6))),
        //19
        new Pose2d(new Translation2d(3.58, 4.95), new Rotation2d(Units.degreesToRadians(-57.5))),
        new Pose2d(new Translation2d(3.86, 5.12), new Rotation2d(Units.degreesToRadians(-57.5))),
        //20
        new Pose2d(new Translation2d(4.88, 5.24), new Rotation2d(Units.degreesToRadians(-119.5))),
        new Pose2d(new Translation2d(5.17, 5.08), new Rotation2d(Units.degreesToRadians(-119.6))),
        //21
        new Pose2d(new Translation2d(5.74, 4.35), new Rotation2d(Units.degreesToRadians(-177.5))),
        new Pose2d(new Translation2d(5.76, 4.02), new Rotation2d(Units.degreesToRadians(-177.5))),
        //22
        new Pose2d(new Translation2d(5.40, 3.10), new Rotation2d(Units.degreesToRadians(122.1))),
        new Pose2d(new Translation2d(5.12, 2.93), new Rotation2d(Units.degreesToRadians(122.0))),
        //6
        new Pose2d(new Translation2d(13.96, 3.11), new Rotation2d(Units.degreesToRadians(122.3))),
        new Pose2d(new Translation2d(13.68, 2.94), new Rotation2d(Units.degreesToRadians(122.2))),
        //7
        new Pose2d(new Translation2d(14.31, 4.35), new Rotation2d(Units.degreesToRadians(-178.0))),
        new Pose2d(new Translation2d(14.32, 4.02), new Rotation2d(Units.degreesToRadians(-178.1))),
        //8
        new Pose2d(new Translation2d(13.40, 5.26), new Rotation2d(Units.degreesToRadians(-117.7))),
        new Pose2d(new Translation2d(13.70, 5.11), new Rotation2d(Units.degreesToRadians(-117.8))),
        //9
        new Pose2d(new Translation2d(12.16, 4.95), new Rotation2d(Units.degreesToRadians(-57.7))),
        new Pose2d(new Translation2d(12.44, 5.12), new Rotation2d(Units.degreesToRadians(-57.8))),
        //10
        new Pose2d(new Translation2d(11.81, 3.70), new Rotation2d(Units.degreesToRadians(2.5))),
        new Pose2d(new Translation2d(11.80, 4.03), new Rotation2d(Units.degreesToRadians(2.4))),
        //11
        new Pose2d(new Translation2d(12.72, 2.79), new Rotation2d(Units.degreesToRadians(62.7))),
        new Pose2d(new Translation2d(12.43, 2.94), new Rotation2d(Units.degreesToRadians(62.6)))
    );
}