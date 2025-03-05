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
        new Pose2d(new Translation2d(4.23, 2.67), new Rotation2d(Units.degreesToRadians(60.5))), //done
        new Pose2d(new Translation2d(3.83, 2.93), new Rotation2d(Units.degreesToRadians(62.9))), 

        //18
        new Pose2d(new Translation2d(3.20, 3.54), new Rotation2d(Units.degreesToRadians(3.76))), //done
        new Pose2d(new Translation2d(3.20, 4.05), new Rotation2d(Units.degreesToRadians(2.5))), 

        //19
        new Pose2d(new Translation2d(3.43, 4.90), new Rotation2d(Units.degreesToRadians(-56))), //done
        new Pose2d(new Translation2d(3.87, 5.15), new Rotation2d(Units.degreesToRadians(-57.4))), 

        //20
        new Pose2d(new Translation2d(4.72,5.35), new Rotation2d(Units.degreesToRadians(-116.5))), //done
        new Pose2d(new Translation2d(5.15, 5.1), new Rotation2d(Units.degreesToRadians(-118))), //done

        //21
        new Pose2d(new Translation2d(5.78, 4.51), new Rotation2d(Units.degreesToRadians(-176.87))), //done
        new Pose2d(new Translation2d(5.77, 4.00), new Rotation2d(Units.degreesToRadians(-177.39))), //done

        //22
        new Pose2d(new Translation2d(5.56, 3.14), new Rotation2d(Units.degreesToRadians(123))), //done
        new Pose2d(new Translation2d(5.09, 2.90), new Rotation2d(Units.degreesToRadians(120))) //done

    );
} 