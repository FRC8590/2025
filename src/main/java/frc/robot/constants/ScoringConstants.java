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
        new Pose2d(new Translation2d(4.29, 2.84), new Rotation2d(Units.degreesToRadians(61.5))),
        new Pose2d(new Translation2d(3.85, 3.03), new Rotation2d(Units.degreesToRadians(62.3))),
        //18
        new Pose2d(new Translation2d(3.36, 3.61), new Rotation2d(Units.degreesToRadians(1.6))),
        new Pose2d(new Translation2d(3.31, 4.08), new Rotation2d(Units.degreesToRadians(2.4))),
        //19
        new Pose2d(new Translation2d(3.56, 4.79), new Rotation2d(Units.degreesToRadians(-57.6))),
        new Pose2d(new Translation2d(3.93, 5.08), new Rotation2d(Units.degreesToRadians(-56.8))),
        //20
        new Pose2d(new Translation2d(4.83, 5.30), new Rotation2d(Units.degreesToRadians(-122.6))),
        new Pose2d(new Translation2d(5.16, 5.00), new Rotation2d(Units.degreesToRadians(-118.8))),
        //21
        new Pose2d(new Translation2d(5.62, 4.45), new Rotation2d(Units.degreesToRadians(-177.6))),
        new Pose2d(new Translation2d(5.68, 3.98), new Rotation2d(Units.degreesToRadians(-176.8))),
        //22
        new Pose2d(new Translation2d(5.43, 3.26), new Rotation2d(Units.degreesToRadians(122.0))),
        new Pose2d(new Translation2d(5.04, 2.97), new Rotation2d(Units.degreesToRadians(122.8))),
        //6
        new Pose2d(new Translation2d(13.99, 3.27), new Rotation2d(Units.degreesToRadians(122.2))),
        new Pose2d(new Translation2d(13.61, 2.98), new Rotation2d(Units.degreesToRadians(123.0))),
        //7
        new Pose2d(new Translation2d(14.19, 4.45), new Rotation2d(Units.degreesToRadians(-178.1))),
        new Pose2d(new Translation2d(14.25, 3.98), new Rotation2d(Units.degreesToRadians(-177.3))),
        //8
        new Pose2d(new Translation2d(13.25, 5.21), new Rotation2d(Units.degreesToRadians(-117.8))),
        new Pose2d(new Translation2d(13.69, 5.03), new Rotation2d(Units.degreesToRadians(-117.0))),
        //9
        new Pose2d(new Translation2d(12.13, 4.79), new Rotation2d(Units.degreesToRadians(-57.8))),
        new Pose2d(new Translation2d(12.51, 5.08), new Rotation2d(Units.degreesToRadians(-57.0))),
        //10
        new Pose2d(new Translation2d(11.93, 3.60), new Rotation2d(Units.degreesToRadians(2.4))),
        new Pose2d(new Translation2d(11.87, 4.07), new Rotation2d(Units.degreesToRadians(3.2))),
        //11
        new Pose2d(new Translation2d(12.87, 2.84), new Rotation2d(Units.degreesToRadians(62.6))),
        new Pose2d(new Translation2d(12.43, 3.02), new Rotation2d(Units.degreesToRadians(63.4)))
    );
}