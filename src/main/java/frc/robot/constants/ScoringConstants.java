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
        new Pose2d(new Translation2d(4.37, 2.80), new Rotation2d(Units.degreesToRadians(58.4))),
        new Pose2d(new Translation2d(3.80, 3.07), new Rotation2d(Units.degreesToRadians(67.1))),
        //18
        new Pose2d(new Translation2d(3.37, 3.52), new Rotation2d(Units.degreesToRadians(-1.5))),
        new Pose2d(new Translation2d(3.32, 4.15), new Rotation2d(Units.degreesToRadians(7.2))),
        //19
        new Pose2d(new Translation2d(3.48, 4.73), new Rotation2d(Units.degreesToRadians(-60.6))),
        new Pose2d(new Translation2d(3.99, 5.11), new Rotation2d(Units.degreesToRadians(-51.9))),
        //20
        new Pose2d(new Translation2d(4.65, 5.23), new Rotation2d(Units.degreesToRadians(-122.7))),
        new Pose2d(new Translation2d(5.22, 4.95), new Rotation2d(Units.degreesToRadians(-114.0))),
        //21
        new Pose2d(new Translation2d(5.61, 4.54), new Rotation2d(Units.degreesToRadians(179.3))),
        new Pose2d(new Translation2d(5.67, 3.91), new Rotation2d(Units.degreesToRadians(-171.9))),
        //22
        new Pose2d(new Translation2d(5.50, 3.31), new Rotation2d(Units.degreesToRadians(118.9))),
        new Pose2d(new Translation2d(4.99, 2.94), new Rotation2d(Units.degreesToRadians(127.7))),
        //6
        new Pose2d(new Translation2d(14.06, 3.33), new Rotation2d(Units.degreesToRadians(119.1))),
        new Pose2d(new Translation2d(13.55, 2.95), new Rotation2d(Units.degreesToRadians(127.9))),
        //7
        new Pose2d(new Translation2d(14.18, 4.54), new Rotation2d(Units.degreesToRadians(178.8))),
        new Pose2d(new Translation2d(14.23, 3.91), new Rotation2d(Units.degreesToRadians(-172.4))),
        //8
        new Pose2d(new Translation2d(13.17, 5.24), new Rotation2d(Units.degreesToRadians(-120.9))),
        new Pose2d(new Translation2d(13.75, 4.99), new Rotation2d(Units.degreesToRadians(-112.2))),
        //9
        new Pose2d(new Translation2d(12.06, 4.73), new Rotation2d(Units.degreesToRadians(-60.9))),
        new Pose2d(new Translation2d(12.57, 5.11), new Rotation2d(Units.degreesToRadians(-52.1))),
        //10
        new Pose2d(new Translation2d(11.94, 3.51), new Rotation2d(Units.degreesToRadians(-0.7))),
        new Pose2d(new Translation2d(11.89, 4.14), new Rotation2d(Units.degreesToRadians(8.0))),
        //11
        new Pose2d(new Translation2d(12.95, 2.81), new Rotation2d(Units.degreesToRadians(59.5))),
        new Pose2d(new Translation2d(12.38, 3.06), new Rotation2d(Units.degreesToRadians(68.2)))
    );
}