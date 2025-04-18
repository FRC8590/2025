package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public record AlgaeConstants(
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

    public static final AlgaeConstants DEFAULT = new AlgaeConstants(
        //17
        new Pose2d(new Translation2d(4.26, 2.52), new Rotation2d(Units.degreesToRadians(58.4))),
        new Pose2d(new Translation2d(3.84, 2.72), new Rotation2d(Units.degreesToRadians(60.2))),
        //18
        new Pose2d(new Translation2d(3.07, 3.48), new Rotation2d(Units.degreesToRadians(-1.5))),
        new Pose2d(new Translation2d(3.02, 3.94), new Rotation2d(Units.degreesToRadians(0.3))),
        //19
        new Pose2d(new Translation2d(3.29, 4.97), new Rotation2d(Units.degreesToRadians(-60.6))),
        new Pose2d(new Translation2d(3.67, 5.25), new Rotation2d(Units.degreesToRadians(-58.8))),
        //20
        new Pose2d(new Translation2d(4.77, 5.51), new Rotation2d(Units.degreesToRadians(-122.7))),
        new Pose2d(new Translation2d(5.19, 5.31), new Rotation2d(Units.degreesToRadians(-120.9))),
        //21
        new Pose2d(new Translation2d(5.90, 4.59), new Rotation2d(Units.degreesToRadians(179.3))),
        new Pose2d(new Translation2d(5.96, 4.12), new Rotation2d(Units.degreesToRadians(-178.9))),
        //22
        new Pose2d(new Translation2d(5.68, 3.07), new Rotation2d(Units.degreesToRadians(118.9))),
        new Pose2d(new Translation2d(5.30, 2.80), new Rotation2d(Units.degreesToRadians(120.7))),
        //6
        new Pose2d(new Translation2d(14.25, 3.09), new Rotation2d(Units.degreesToRadians(119.1))),
        new Pose2d(new Translation2d(13.88, 2.81), new Rotation2d(Units.degreesToRadians(120.9))),
        //7
        new Pose2d(new Translation2d(14.48, 4.58), new Rotation2d(Units.degreesToRadians(178.8))),
        new Pose2d(new Translation2d(14.53, 4.11), new Rotation2d(Units.degreesToRadians(-179.4))),
        //8
        new Pose2d(new Translation2d(13.28, 5.52), new Rotation2d(Units.degreesToRadians(-120.9))),
        new Pose2d(new Translation2d(13.71, 5.34), new Rotation2d(Units.degreesToRadians(-119.1))),
        //9
        new Pose2d(new Translation2d(11.87, 4.97), new Rotation2d(Units.degreesToRadians(-60.9))),
        new Pose2d(new Translation2d(12.24, 5.25), new Rotation2d(Units.degreesToRadians(-59.1))),
        //10
        new Pose2d(new Translation2d(11.65, 3.46), new Rotation2d(Units.degreesToRadians(-0.7))),
        new Pose2d(new Translation2d(11.60, 3.93), new Rotation2d(Units.degreesToRadians(1.1))),
        //11
        new Pose2d(new Translation2d(12.84, 2.53), new Rotation2d(Units.degreesToRadians(59.5))),
        new Pose2d(new Translation2d(12.42, 2.71), new Rotation2d(Units.degreesToRadians(61.3)))
    );
}