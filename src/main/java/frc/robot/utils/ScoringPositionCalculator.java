package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants;

/**
 * Utility class for calculating scoring positions based on AprilTag positions
 */
public class ScoringPositionCalculator {
    
    // Reference tag ID
    private static final int REFERENCE_TAG_ID = 20;
    
    /**
     * Calculates a scoring position for any AprilTag based on the known transform
     * from the reference tag (ID 17) to its scoring positions.
     * 
     * @param tagId The AprilTag ID to calculate scoring position for
     * @param isLeft Whether to use left (true) or right (false) scoring position
     * @return The calculated scoring position
     */
    public static Pose2d calculateScoringPosition(int tagId, boolean isLeft) {
        try {
            // Get the target AprilTag position
            var tagPose = Constants.layout.getTagPose(tagId).get().toPose2d();
            
            // Get the reference tag position (ID 17)
            var referenceTagPose = Constants.layout.getTagPose(REFERENCE_TAG_ID).get().toPose2d();
            
            // Get the reference scoring position from our constants
            Pose2d referenceScoringPose = fallbackPosition(tagId, isLeft);
            
            // Calculate the transform from reference tag to scoring position
            Translation2d offsetVector = new Translation2d(
                referenceScoringPose.getX() - referenceTagPose.getX(),
                referenceScoringPose.getY() - referenceTagPose.getY()
            );
            
            // Calculate rotation offset (difference between tag rotation and scoring position rotation)
            double rotationOffset = referenceScoringPose.getRotation().getRadians() - 
                                   referenceTagPose.getRotation().getRadians();
            
            // Apply the same transform to the current tag
            return new Pose2d(
                tagPose.getX() + offsetVector.getX(),
                tagPose.getY() + offsetVector.getY(),
                new Rotation2d(tagPose.getRotation().getRadians() + rotationOffset)
            );
        } catch (Exception e) {
            // If there's an error, fall back to the constants
            System.err.println("Error calculating scoring position for tag " + tagId + ": " + e.getMessage());
            return fallbackPosition(tagId, isLeft);
        }
    }
    
    /**
     * Fallback method to get scoring positions from constants if dynamic calculation fails
     */
    private static Pose2d fallbackPosition(int tagId, boolean isLeft) {
        return switch (tagId) {
            case 17 -> isLeft ? Constants.SCORING_CONSTANTS.left17() : Constants.SCORING_CONSTANTS.right17();
            case 18 -> isLeft ? Constants.SCORING_CONSTANTS.left18() : Constants.SCORING_CONSTANTS.right18();
            case 19 -> isLeft ? Constants.SCORING_CONSTANTS.left19() : Constants.SCORING_CONSTANTS.right19();
            case 20 -> isLeft ? Constants.SCORING_CONSTANTS.left20() : Constants.SCORING_CONSTANTS.right20();
            case 21 -> isLeft ? Constants.SCORING_CONSTANTS.left21() : Constants.SCORING_CONSTANTS.right21();
            case 22 -> isLeft ? Constants.SCORING_CONSTANTS.left22() : Constants.SCORING_CONSTANTS.right22();
            case 6 -> isLeft ? Constants.SCORING_CONSTANTS.left6() : Constants.SCORING_CONSTANTS.right6();
            case 7 -> isLeft ? Constants.SCORING_CONSTANTS.left7() : Constants.SCORING_CONSTANTS.right7();
            case 8 -> isLeft ? Constants.SCORING_CONSTANTS.left8() : Constants.SCORING_CONSTANTS.right8();
            case 9 -> isLeft ? Constants.SCORING_CONSTANTS.left9() : Constants.SCORING_CONSTANTS.right9();
            case 10 -> isLeft ? Constants.SCORING_CONSTANTS.left10() : Constants.SCORING_CONSTANTS.right10();
            case 11 -> isLeft ? Constants.SCORING_CONSTANTS.left11() : Constants.SCORING_CONSTANTS.right11();
            default -> throw new IllegalArgumentException("Invalid AprilTag ID: " + tagId);
        };
    }
} 