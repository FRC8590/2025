package frc.robot.commands.Scoring;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.MoveElevator;

public class QuickScore extends SequentialCommandGroup {
    // Distance threshold in meters to activate quick scoring
    private static final double SCORING_THRESHOLD = 0.5; 

    public QuickScore() {
        // Only add commands if we're close enough to a scoring position
        if (isCloseToScoringPosition()) {
            addCommands(
                new ParallelCommandGroup(
                    getDriveToNearestScoreCommand(),
                    new MoveElevator(0.7000)
                ),
                new ScoreCoral(),
                new MoveElevator(0)
            );
        }
        
        addRequirements(Constants.SHOOTER);
        addRequirements(Constants.ELEVATOR);
    }

    private boolean isCloseToScoringPosition() {
        Pose2d currentPose = Constants.drivebase.getPose();
        int closestTag = Constants.drivebase.findClosestAprilTag();
        
        // Get the target pose based on the closest AprilTag
        Pose2d targetPose = getScoringPoseForTag(closestTag);
        if (targetPose == null) return false;

        // Calculate distance to target pose
        double distance = currentPose.getTranslation().getDistance(
            targetPose.getTranslation());
        
        return distance <= SCORING_THRESHOLD;
    }

    private Pose2d getScoringPoseForTag(int tagId) {
        return switch (tagId) {
            case 17 -> Constants.SCORING_CONSTANTS.right17();
            case 18 -> Constants.SCORING_CONSTANTS.right18();
            case 19 -> Constants.SCORING_CONSTANTS.right19();
            case 20 -> Constants.SCORING_CONSTANTS.right20();
            case 21 -> Constants.SCORING_CONSTANTS.right21();
            case 22 -> Constants.SCORING_CONSTANTS.right22();
            case 6 -> Constants.SCORING_CONSTANTS.right6();
            case 7 -> Constants.SCORING_CONSTANTS.right7();
            case 8 -> Constants.SCORING_CONSTANTS.right8();
            case 9 -> Constants.SCORING_CONSTANTS.right9();
            case 10 -> Constants.SCORING_CONSTANTS.right10();
            case 11 -> Constants.SCORING_CONSTANTS.right11();
            default -> null;
        };
    }

    private Command getDriveToNearestScoreCommand() {
        int closestTag = Constants.drivebase.findClosestAprilTag();
        Pose2d targetPose = getScoringPoseForTag(closestTag);
        return Constants.drivebase.driveToPose(targetPose);
    }
} 