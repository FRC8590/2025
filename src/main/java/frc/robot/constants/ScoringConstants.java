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
        new Pose2d(new Translation2d(4.40, 2.77), new Rotation2d(Units.degreesToRadians(60))), //done
        new Pose2d(new Translation2d(3.86, 3.01), new Rotation2d(Units.degreesToRadians(65))), //done

        //18
        new Pose2d(new Translation2d(3.36, 3.47), new Rotation2d(Units.degreesToRadians(0))), //done
        new Pose2d(new Translation2d(3.29, 4.08), new Rotation2d(Units.degreesToRadians(4))), //done

        //19
        new Pose2d(new Translation2d(3.49, 4.75), new Rotation2d(Units.degreesToRadians(-57))), //done
        new Pose2d(new Translation2d(3.93, 5.10), new Rotation2d(Units.degreesToRadians(-58))), //done

        //20
        new Pose2d(new Translation2d(4.70,5.22), new Rotation2d(Units.degreesToRadians(-115))), //done
        new Pose2d(new Translation2d(5.06, 5.03), new Rotation2d(Units.degreesToRadians(-119))), //done

        //21
        new Pose2d(new Translation2d(5.64, 4.52), new Rotation2d(Units.degreesToRadians(178))),
        new Pose2d(new Translation2d(5.68, 3.96), new Rotation2d(Units.degreesToRadians(176))), //done

        //22
        new Pose2d(new Translation2d(5.5, 3.29), new Rotation2d(Units.degreesToRadians(119))),
        new Pose2d(new Translation2d(5.05, 2.96), new Rotation2d(Units.degreesToRadians(124))) //done

    );
} 