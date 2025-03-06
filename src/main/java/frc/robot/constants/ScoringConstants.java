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
        new Pose2d(new Translation2d(4.35, 2.81), new Rotation2d(Units.degreesToRadians(58))), //done
        new Pose2d(new Translation2d(3.85, 3.04), new Rotation2d(Units.degreesToRadians(58))), 

        //18
        new Pose2d(new Translation2d(3.36, 3.55), new Rotation2d(Units.degreesToRadians(-2))), //done
        new Pose2d(new Translation2d(3.32, 4.08), new Rotation2d(Units.degreesToRadians(-1))), 

        //19
        new Pose2d(new Translation2d(3.50, 4.77), new Rotation2d(Units.degreesToRadians(-61))), //done
        new Pose2d(new Translation2d(3.95, 5.06), new Rotation2d(Units.degreesToRadians(-62))), 

        //20
        new Pose2d(new Translation2d(4.72,5.20), new Rotation2d(Units.degreesToRadians(-119))), //done
        new Pose2d(new Translation2d(5.12, 5.01), new Rotation2d(Units.degreesToRadians(-121))), //done

        //21
        new Pose2d(new Translation2d(5.63, 4.51), new Rotation2d(Units.degreesToRadians(178))), //done
        new Pose2d(new Translation2d(5.66, 3.97), new Rotation2d(Units.degreesToRadians(178))), //done

        //22
        new Pose2d(new Translation2d(5.48, 3.29), new Rotation2d(Units.degreesToRadians(118))), //done
        new Pose2d(new Translation2d(5.03, 2.98), new Rotation2d(Units.degreesToRadians(118))), //done

        //6
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), //done
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), 

        //7
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), //done
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), 

        //8
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), //done
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), 

        //9
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), //done
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), //done

        //10
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), //done
        new Pose2d(new Translation2d(0, 0), new Rotation2d(Units.degreesToRadians(0))), //done

        //11
        new Pose2d(new Translation2d(12.93, 2.81), new Rotation2d(Units.degreesToRadians(57))), //done
        new Pose2d(new Translation2d(11.89, 0), new Rotation2d(Units.degreesToRadians(0))) //done

    );
} 