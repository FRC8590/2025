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
        new Pose2d(new Translation2d(3.87, 3.05), new Rotation2d(Units.degreesToRadians(63.1))),
        new Pose2d(new Translation2d(4.31, 2.81), new Rotation2d(Units.degreesToRadians(60.7))),
        //18
        new Pose2d(new Translation2d(3.33, 4.08), new Rotation2d(Units.degreesToRadians(3.2))),
        new Pose2d(new Translation2d(3.35, 3.57), new Rotation2d(Units.degreesToRadians(0.8))),
        //19
        new Pose2d(new Translation2d(3.94, 5.06), new Rotation2d(Units.degreesToRadians(-55.9))),
        new Pose2d(new Translation2d(3.52, 4.79), new Rotation2d(Units.degreesToRadians(-58.4))),
        //20
        new Pose2d(new Translation2d(5.15, 4.98), new Rotation2d(Units.degreesToRadians(-118.0))),
        new Pose2d(new Translation2d(4.71, 5.22), new Rotation2d(Units.degreesToRadians(-120.4))),
        //21
        new Pose2d(new Translation2d(5.66, 3.98), new Rotation2d(Units.degreesToRadians(-175.9))),
        new Pose2d(new Translation2d(5.63, 4.48), new Rotation2d(Units.degreesToRadians(-178.4))),
        //22
        new Pose2d(new Translation2d(5.04, 2.99), new Rotation2d(Units.degreesToRadians(123.7))),
        new Pose2d(new Translation2d(5.46, 3.26), new Rotation2d(Units.degreesToRadians(121.2))),
        //6
        new Pose2d(new Translation2d(13.60, 3.00), new Rotation2d(Units.degreesToRadians(123.9))),
        new Pose2d(new Translation2d(14.02, 3.27), new Rotation2d(Units.degreesToRadians(121.4))),
        //7
        new Pose2d(new Translation2d(14.22, 3.98), new Rotation2d(Units.degreesToRadians(-176.4))),
        new Pose2d(new Translation2d(14.20, 4.48), new Rotation2d(Units.degreesToRadians(-178.9))),
        //8
        new Pose2d(new Translation2d(13.68, 5.01), new Rotation2d(Units.degreesToRadians(-116.2))),
        new Pose2d(new Translation2d(13.23, 5.24), new Rotation2d(Units.degreesToRadians(-118.6))),
        //9
        new Pose2d(new Translation2d(12.52, 5.06), new Rotation2d(Units.degreesToRadians(-56.1))),
        new Pose2d(new Translation2d(12.10, 4.79), new Rotation2d(Units.degreesToRadians(-58.6))),
        //10
        new Pose2d(new Translation2d(11.90, 4.07), new Rotation2d(Units.degreesToRadians(4.0))),
        new Pose2d(new Translation2d(11.92, 3.57), new Rotation2d(Units.degreesToRadians(1.6))),
        //11
        new Pose2d(new Translation2d(12.45, 3.04), new Rotation2d(Units.degreesToRadians(64.2))),
        new Pose2d(new Translation2d(12.89, 2.81), new Rotation2d(Units.degreesToRadians(61.8))),
    );
} 