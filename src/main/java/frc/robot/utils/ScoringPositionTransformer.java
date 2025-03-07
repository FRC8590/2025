package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Utility class for transforming scoring positions from one AprilTag to another
 * with proper handling of the hexagonal field geometry
 */
public class ScoringPositionTransformer {
    
    // Field center coordinates for each hexagon (calculated from tag coordinates)
    private static final Translation2d BLUE_HEXAGON_CENTER = new Translation2d(4.50, 4.02);
    private static final Translation2d RED_HEXAGON_CENTER = new Translation2d(13.06, 4.03);
    
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
            // Start with a clean logging slate
            SmartDashboard.putString("Transform Status", "Starting transformation");
            
            if (sourceTagId == targetTagId) {
                // No transformation needed if source and target are the same
                return targetIsLeft ? sourceLeftPosition : sourceRightPosition;
            }
            
            // Get the positions of source and target AprilTags
            var sourceTagPose = Constants.layout.getTagPose(sourceTagId).get().toPose2d();
            var targetTagPose = Constants.layout.getTagPose(targetTagId).get().toPose2d();
            
            // Calculate vectors from tags to hexagon center
            Translation2d sourceToCenter = calculateTagToCenter(sourceTagId);
            Translation2d targetToCenter = calculateTagToCenter(targetTagId);
            
            // The outward direction is opposite to the center direction
            Translation2d sourceOutward = sourceToCenter.unaryMinus();
            Translation2d targetOutward = targetToCenter.unaryMinus();
            
            // Calculate source left and right lateral direction vectors (perpendicular to outward)
            Translation2d sourceLeftLateral = new Translation2d(-sourceOutward.getY(), sourceOutward.getX());
            Translation2d sourceRightLateral = new Translation2d(sourceOutward.getY(), -sourceOutward.getX());
            
            // Calculate target lateral direction vector based on desired side
            Translation2d targetLateral = targetIsLeft ? 
                new Translation2d(-targetOutward.getY(), targetOutward.getX()) :
                new Translation2d(targetOutward.getY(), -targetOutward.getX());
            
            // Select the source position based on which target side is requested
            Pose2d sourcePosition = targetIsLeft ? sourceLeftPosition : sourceRightPosition;
            Translation2d sourceLateral = targetIsLeft ? sourceLeftLateral : sourceRightLateral;
            
            // Calculate the relative offset of the working position from the source tag
            Translation2d relativeOffset = new Translation2d(
                sourcePosition.getX() - sourceTagPose.getX(),
                sourcePosition.getY() - sourceTagPose.getY()
            );
            
            // Decompose the offset into outward and lateral components relative to the source tag
            double outwardComponent = dotProduct(relativeOffset, sourceOutward);
            double lateralComponent = dotProduct(relativeOffset, sourceLateral);
            
            // Log the components for diagnostics
            SmartDashboard.putNumber("Outward Component", outwardComponent);
            SmartDashboard.putNumber("Lateral Component", lateralComponent);
            
            // Create a new coordinate system based on the target tag
            // Apply the same outward and lateral components to get the new position
            Translation2d newPosition = new Translation2d(
                targetTagPose.getX() + targetOutward.getX() * outwardComponent + targetLateral.getX() * lateralComponent,
                targetTagPose.getY() + targetOutward.getY() * outwardComponent + targetLateral.getY() * lateralComponent
            );
            
            // Log for diagnostics
            SmartDashboard.putNumber("Source Tag X", sourceTagPose.getX());
            SmartDashboard.putNumber("Source Tag Y", sourceTagPose.getY());
            SmartDashboard.putNumber("Target Tag X", targetTagPose.getX());
            SmartDashboard.putNumber("Target Tag Y", targetTagPose.getY());
            SmartDashboard.putNumber("Source Outward X", sourceOutward.getX());
            SmartDashboard.putNumber("Source Outward Y", sourceOutward.getY());
            SmartDashboard.putNumber("Target Outward X", targetOutward.getX());
            SmartDashboard.putNumber("Target Outward Y", targetOutward.getY());
            
            // Calculate the rotation by preserving the same relative angle from source to target
            // First get the angle difference between source position and source tag
            Rotation2d sourceRelativeRotation = sourcePosition.getRotation();
            
            // Get the angle between source tag and target tag relative to the hexagon
            double sourceTagAngle = Math.atan2(sourceToCenter.getY(), sourceToCenter.getX());
            double targetTagAngle = Math.atan2(targetToCenter.getY(), targetToCenter.getX());
            double tagAngleDifference = targetTagAngle - sourceTagAngle;
            
            // Apply the angle difference to the source rotation
            Rotation2d newRotation = new Rotation2d(sourceRelativeRotation.getRadians() + tagAngleDifference);
            
            // Log rotation diagnostics
            SmartDashboard.putNumber("Source Rotation Degrees", Units.radiansToDegrees(sourceRelativeRotation.getRadians()));
            SmartDashboard.putNumber("Tag Angle Difference Degrees", Units.radiansToDegrees(tagAngleDifference));
            SmartDashboard.putNumber("New Rotation Degrees", Units.radiansToDegrees(newRotation.getRadians()));
            
            // Create final pose
            Pose2d transformedPose = new Pose2d(newPosition, newRotation);
            
            // Log the calculated values for debugging
            SmartDashboard.putNumber("Source Tag ID", sourceTagId);
            SmartDashboard.putNumber("Target Tag ID", targetTagId);
            SmartDashboard.putBoolean("Target Is Left", targetIsLeft);
            SmartDashboard.putNumber("Transformed X", newPosition.getX());
            SmartDashboard.putNumber("Transformed Y", newPosition.getY());
            SmartDashboard.putNumber("Transformed Rotation", newRotation.getDegrees());
            SmartDashboard.putString("Transform Status", "Successful");
            
            return transformedPose;
            
        } catch (Exception e) {
            // If there's an error, log it and fall back to the constants
            String errorMsg = "Error transforming scoring position from tag " + sourceTagId + 
                              " to " + targetTagId + ": " + e.getMessage();
            System.err.println(errorMsg);
            SmartDashboard.putString("Transform Status", "Error: " + errorMsg);
            return fallbackPosition(targetTagId, targetIsLeft);
        }
    }
    
    /**
     * Calculate the vector from tag to center and validates the outward direction
     * 
     * @param tagId The AprilTag ID
     * @return A normalized vector pointing from the tag to the hexagon center
     */
    private static Translation2d calculateTagToCenter(int tagId) {
        try {
            // Get the tag pose
            var tagPose = Constants.layout.getTagPose(tagId).get().toPose2d();
            
            // Determine which hexagon this tag belongs to
            Translation2d hexagonCenter = isBlueHexagon(tagId) ? BLUE_HEXAGON_CENTER : RED_HEXAGON_CENTER;
            
            // Calculate the vector from the tag to the hexagon center
            Translation2d tagToCenter = new Translation2d(
                hexagonCenter.getX() - tagPose.getX(),
                hexagonCenter.getY() - tagPose.getY()
            );
            
            // Log this vector for debugging
            SmartDashboard.putNumber("Tag " + tagId + " To Center X", tagToCenter.getX());
            SmartDashboard.putNumber("Tag " + tagId + " To Center Y", tagToCenter.getY());
            
            return normalizeVector(tagToCenter);
        } catch (Exception e) {
            System.err.println("Error calculating tag to center vector for tag " + tagId + ": " + e.getMessage());
            // Provide a fallback based on the tag ID
            return fallbackTagToCenter(tagId);
        }
    }
    
    /**
     * Fallback method to get tag-to-center vectors if AprilTag detection fails
     */
    private static Translation2d fallbackTagToCenter(int tagId) {
        // These are approximate normalized vectors based on the hexagon geometry
        return switch (tagId) {
            case 17 -> normalizeVector(new Translation2d(0.15, 1.2));  // Bottom left of blue
            case 18 -> normalizeVector(new Translation2d(1.15, 0.45)); // Left of blue
            case 19 -> normalizeVector(new Translation2d(1.0, -0.75)); // Top left of blue
            case 20 -> normalizeVector(new Translation2d(-0.2, -1.2)); // Top right of blue
            case 21 -> normalizeVector(new Translation2d(-1.15, -0.5)); // Right of blue
            case 22 -> normalizeVector(new Translation2d(-1.0, 0.7));  // Bottom right of blue
            case 6 -> normalizeVector(new Translation2d(-1.0, 0.7));   // Bottom left of red
            case 7 -> normalizeVector(new Translation2d(-1.15, -0.5)); // Left of red
            case 8 -> normalizeVector(new Translation2d(-0.2, -1.2));  // Top left of red
            case 9 -> normalizeVector(new Translation2d(1.0, -0.75));  // Top right of red
            case 10 -> normalizeVector(new Translation2d(1.15, 0.45)); // Right of red
            case 11 -> normalizeVector(new Translation2d(0.15, 1.2));  // Bottom right of red
            default -> new Translation2d(0, 1); // Default fallback
        };
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