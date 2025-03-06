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
        new Pose2d(new Translation2d(4.34, 2.78), new Rotation2d(Units.degreesToRadians(58))), //done
        new Pose2d(new Translation2d(3.84, 3.01), new Rotation2d(Units.degreesToRadians(58))),
        //18
        new Pose2d(new Translation2d(3.33, 3.55), new Rotation2d(Units.degreesToRadians(-2))), //done
        new Pose2d(new Translation2d(3.29, 4.08), new Rotation2d(Units.degreesToRadians(-1))),
        //19
        new Pose2d(new Translation2d(3.48, 4.80), new Rotation2d(Units.degreesToRadians(-61))), //done
        new Pose2d(new Translation2d(3.93, 5.09), new Rotation2d(Units.degreesToRadians(-62))),
        //20
        new Pose2d(new Translation2d(4.73, 5.23), new Rotation2d(Units.degreesToRadians(-121))), //done
        new Pose2d(new Translation2d(5.13, 5.04), new Rotation2d(Units.degreesToRadians(-123))), //done
        //21
        new Pose2d(new Translation2d(5.66, 4.51), new Rotation2d(Units.degreesToRadians(178))), //done
        new Pose2d(new Translation2d(5.69, 3.97), new Rotation2d(Units.degreesToRadians(178))), //done
        //22
        new Pose2d(new Translation2d(5.50, 3.27), new Rotation2d(Units.degreesToRadians(118))), //done
        new Pose2d(new Translation2d(5.05, 2.96), new Rotation2d(Units.degreesToRadians(118))), //done
        //6
        new Pose2d(new Translation2d(14.06, 3.28), new Rotation2d(Units.degreesToRadians(119))), //done
        new Pose2d(new Translation2d(13.62, 2.97), new Rotation2d(Units.degreesToRadians(118))),
        //7
        new Pose2d(new Translation2d(14.22, 4.51), new Rotation2d(Units.degreesToRadians(178))), //done
        new Pose2d(new Translation2d(14.26, 3.97), new Rotation2d(Units.degreesToRadians(178))),
        //8
        new Pose2d(new Translation2d(13.21, 5.27), new Rotation2d(Units.degreesToRadians(-122))), //done
        new Pose2d(new Translation2d(13.70, 5.04), new Rotation2d(Units.degreesToRadians(-121))),
        //9
        new Pose2d(new Translation2d(12.06, 4.78), new Rotation2d(Units.degreesToRadians(-62))), //done
        new Pose2d(new Translation2d(12.50, 5.09), new Rotation2d(Units.degreesToRadians(-62))), //done
        //10
        new Pose2d(new Translation2d(11.90, 3.54), new Rotation2d(Units.degreesToRadians(-2))), //done
        new Pose2d(new Translation2d(11.86, 4.08), new Rotation2d(Units.degreesToRadians(-2))), //done
        //11
        new Pose2d(new Translation2d(12.92, 2.78), new Rotation2d(Units.degreesToRadians(57))), //done
        new Pose2d(new Translation2d(12.42, 3.01), new Rotation2d(Units.degreesToRadians(58))) //done
    );
} 