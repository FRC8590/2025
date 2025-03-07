package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Utility class for transforming scoring positions from one AprilTag to another
 * with proper handling of the hexagonal field geometry
 */
public class ScoringPositionTransformer {
    
    // Field center coordinates for each hexagon
    private static final Translation2d BLUE_HEXAGON_CENTER = new Translation2d(4.5, 4.0);
    private static final Translation2d RED_HEXAGON_CENTER = new Translation2d(13.0, 4.0);
    
    /**
     * Transforms a scoring position from a source tag to a target tag, using known working
     * positions for the source tag as reference.
     * 
     * @param sourceLeftPosition The working left scoring position for the source tag
     * @param sourceRightPosition The working right scoring position for the source tag
     * @param sourceTagId The AprilTag ID that the working positions are for
     * @param targetTagId The AprilTag ID to transform the position to
     * @param targetIsLeft Whether to generate a left (true) or right (false) position for the target tag
     * @return The transformed scoring position for the target tag
     */
    public static Pose2d transformScoringPosition(
            Pose2d sourceLeftPosition, 
            Pose2d sourceRightPosition, 
            int sourceTagId, 
            int targetTagId, 
            boolean targetIsLeft) {
        
        try {
            if (sourceTagId == targetTagId) {
                // No transformation needed if source and target are the same
                return targetIsLeft ? sourceLeftPosition : sourceRightPosition;
            }
            
            // Get the positions of source and target AprilTags
            var sourceTagPose = Constants.layout.getTagPose(sourceTagId).get().toPose2d();
            var targetTagPose = Constants.layout.getTagPose(targetTagId).get().toPose2d();
            
            // Determine which hexagon these tags belong to
            Translation2d hexagonCenter = isBlueHexagon(sourceTagId) ? BLUE_HEXAGON_CENTER : RED_HEXAGON_CENTER;
            
            // Calculate vector from source tag to hexagon center
            Translation2d sourceToCenter = new Translation2d(
                hexagonCenter.getX() - sourceTagPose.getX(),
                hexagonCenter.getY() - sourceTagPose.getY()
            );
            
            // Calculate vector from target tag to hexagon center
            Translation2d targetToCenter = new Translation2d(
                hexagonCenter.getX() - targetTagPose.getX(),
                hexagonCenter.getY() - targetTagPose.getY()
            );
            
            // Calculate the normalized outward direction vectors 
            Translation2d sourceOutward = normalizeVector(sourceToCenter.unaryMinus());
            Translation2d targetOutward = normalizeVector(targetToCenter.unaryMinus());
            
            // Calculate source left and right lateral direction vectors
            Translation2d sourceLeftLateral = new Translation2d(-sourceOutward.getY(), sourceOutward.getX());
            Translation2d sourceRightLateral = new Translation2d(sourceOutward.getY(), -sourceOutward.getX());
            
            // Calculate target lateral direction vector based on desired side
            Translation2d targetLateral = targetIsLeft ? 
                new Translation2d(-targetOutward.getY(), targetOutward.getX()) :
                new Translation2d(targetOutward.getY(), -targetOutward.getX());
            
            // Select the source position based on which was provided as reference
            Pose2d sourcePosition = targetIsLeft ? sourceLeftPosition : sourceRightPosition;
            Translation2d sourceLateral = targetIsLeft ? sourceLeftLateral : sourceRightLateral;
            
            // Calculate the relative offset of the working position from the source tag
            Translation2d relativeOffset = new Translation2d(
                sourcePosition.getX() - sourceTagPose.getX(),
                sourcePosition.getY() - sourceTagPose.getY()
            );
            
            // Decompose the offset into outward and lateral components
            double outwardComponent = dotProduct(relativeOffset, sourceOutward);
            double lateralComponent = dotProduct(relativeOffset, sourceLateral);
            
            // Apply the same components to the target tag with its own directions
            Translation2d newPosition = new Translation2d(
                targetTagPose.getX() + targetOutward.getX() * outwardComponent + targetLateral.getX() * lateralComponent,
                targetTagPose.getY() + targetOutward.getY() * outwardComponent + targetLateral.getY() * lateralComponent
            );
            
            // Calculate the rotation - this should point toward the target tag
            Rotation2d newRotation = new Rotation2d(
                Math.atan2(targetTagPose.getY() - newPosition.getY(), targetTagPose.getX() - newPosition.getX())
            );
            
            // Log the calculated values for debugging
            SmartDashboard.putNumber("Source Tag ID", sourceTagId);
            SmartDashboard.putNumber("Target Tag ID", targetTagId);
            SmartDashboard.putBoolean("Target Is Left", targetIsLeft);
            SmartDashboard.putNumber("Transformed X", newPosition.getX());
            SmartDashboard.putNumber("Transformed Y", newPosition.getY());
            SmartDashboard.putNumber("Transformed Rotation", newRotation.getDegrees());
            
            return new Pose2d(newPosition, newRotation);
            
        } catch (Exception e) {
            // If there's an error, fall back to the constants
            System.err.println("Error transforming scoring position from tag " + sourceTagId + 
                               " to " + targetTagId + ": " + e.getMessage());
            return fallbackPosition(targetTagId, targetIsLeft);
        }
    }
    
    /**
     * Helper method to normalize a vector to unit length
     */
    private static Translation2d normalizeVector(Translation2d vector) {
        double magnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
        return new Translation2d(vector.getX() / magnitude, vector.getY() / magnitude);
    }
    
    /**
     * Helper method to calculate the dot product of two vectors
     */
    private static double dotProduct(Translation2d a, Translation2d b) {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }
    
    /**
     * Determines if a tag ID belongs to the blue hexagon
     */
    private static boolean isBlueHexagon(int tagId) {
        return tagId >= 17 && tagId <= 22;
    }
    
    /**
     * Fallback method to get scoring positions from constants if transformation fails
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