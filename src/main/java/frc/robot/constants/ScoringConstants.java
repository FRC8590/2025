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
        new Pose2d(new Translation2d(4.36, 2.80), new Rotation2d(Units.degreesToRadians(58.4))),
        new Pose2d(new Translation2d(3.94, 3.00), new Rotation2d(Units.degreesToRadians(60.2))),
        //18
        new Pose2d(new Translation2d(3.37, 3.53), new Rotation2d(Units.degreesToRadians(-1.5))),
        new Pose2d(new Translation2d(3.32, 3.99), new Rotation2d(Units.degreesToRadians(0.3))),
        //19
        new Pose2d(new Translation2d(3.49, 4.74), new Rotation2d(Units.degreesToRadians(-60.6))),
        new Pose2d(new Translation2d(3.87, 5.02), new Rotation2d(Units.degreesToRadians(-58.8))),
        //20
        new Pose2d(new Translation2d(4.66, 5.23), new Rotation2d(Units.degreesToRadians(-122.7))),
        new Pose2d(new Translation2d(5.08, 5.03), new Rotation2d(Units.degreesToRadians(-120.9))),
        //21
        new Pose2d(new Translation2d(5.61, 4.53), new Rotation2d(Units.degreesToRadians(179.3))),
        new Pose2d(new Translation2d(5.67, 4.06), new Rotation2d(Units.degreesToRadians(-178.9))),
        //22
        new Pose2d(new Translation2d(5.49, 3.30), new Rotation2d(Units.degreesToRadians(118.9))),
        new Pose2d(new Translation2d(5.11, 3.03), new Rotation2d(Units.degreesToRadians(120.7))),
        //6
        new Pose2d(new Translation2d(14.05, 3.32), new Rotation2d(Units.degreesToRadians(119.1))),
        new Pose2d(new Translation2d(13.68, 3.04), new Rotation2d(Units.degreesToRadians(120.9))),
        //7
        new Pose2d(new Translation2d(14.18, 4.53), new Rotation2d(Units.degreesToRadians(178.8))),
        new Pose2d(new Translation2d(14.23, 4.06), new Rotation2d(Units.degreesToRadians(-179.4))),
        //8
        new Pose2d(new Translation2d(13.18, 5.24), new Rotation2d(Units.degreesToRadians(-120.9))),
        new Pose2d(new Translation2d(13.61, 5.06), new Rotation2d(Units.degreesToRadians(-119.1))),
        //9
        new Pose2d(new Translation2d(12.07, 4.74), new Rotation2d(Units.degreesToRadians(-60.9))),
        new Pose2d(new Translation2d(12.44, 5.02), new Rotation2d(Units.degreesToRadians(-59.1))),
        //10
        new Pose2d(new Translation2d(11.94, 3.52), new Rotation2d(Units.degreesToRadians(-0.7))),
        new Pose2d(new Translation2d(11.89, 3.99), new Rotation2d(Units.degreesToRadians(1.1))),
        //11
        new Pose2d(new Translation2d(12.94, 2.81), new Rotation2d(Units.degreesToRadians(59.5))),
        new Pose2d(new Translation2d(12.52, 2.99), new Rotation2d(Units.degreesToRadians(61.3)))
    );
} 