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
    Pose2d left22

) {

    public static final ScoringConstants DEFAULT = new ScoringConstants(
        //17
        new Pose2d(new Translation2d(4.28, 2.76), new Rotation2d(Units.degreesToRadians(61))), //done
        new Pose2d(new Translation2d(3.86, 3.01), new Rotation2d(Units.degreesToRadians(65))), 

        //18
        new Pose2d(new Translation2d(3.28, 3.57), new Rotation2d(Units.degreesToRadians(1))), //done
        new Pose2d(new Translation2d(3.29, 4.08), new Rotation2d(Units.degreesToRadians(4))), 

        //19
        new Pose2d(new Translation2d(3.52, 4.85), new Rotation2d(Units.degreesToRadians(-59))), //done
        new Pose2d(new Translation2d(3.93, 5.10), new Rotation2d(Units.degreesToRadians(-58))), 

        //20
        new Pose2d(new Translation2d(4.70,5.26), new Rotation2d(Units.degreesToRadians(-119))), //done
        new Pose2d(new Translation2d(5.15, 5.03), new Rotation2d(Units.degreesToRadians(-118))), //done

        //21
        new Pose2d(new Translation2d(5.69, 4.47), new Rotation2d(Units.degreesToRadians(-179))), //done
        new Pose2d(new Translation2d(5.68, 3.96), new Rotation2d(Units.degreesToRadians(176))), 

        //22
        new Pose2d(new Translation2d(5.47, 3.21), new Rotation2d(Units.degreesToRadians(121))), //done
        new Pose2d(new Translation2d(5.05, 2.96), new Rotation2d(Units.degreesToRadians(124))) 

    );
} 