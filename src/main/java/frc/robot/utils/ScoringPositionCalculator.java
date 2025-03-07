package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Utility class for calculating scoring positions based on AprilTag positions
 * with proper handling of the hexagonal field geometry
 */
public class ScoringPositionCalculator {
    
    // Reference tag ID
    private static final int REFERENCE_TAG_ID = 20;
    
    // Field center coordinates for each hexagon
    private static final Translation2d BLUE_HEXAGON_CENTER = new Translation2d(4.5, 4.0);
    private static final Translation2d RED_HEXAGON_CENTER = new Translation2d(12.0, 4.0);
    
    // Outward shift distance in meters
    private static final double OUTWARD_SHIFT = 0.5;
    // Lateral shift distance in meters (left/right)
    private static final double LATERAL_SHIFT = 0.55;
    
    /**
     * Calculates a scoring position for any AprilTag based on the hexagonal field geometry
     * 
     * @param tagId The AprilTag ID to calculate scoring position for
     * @param isLeft Whether to use left (true) or right (false) scoring position
     * @return The calculated scoring position
     */
    public static Pose2d calculateScoringPosition(int tagId, boolean isLeft) {
        try {
            // Get the target AprilTag position
            var tagPose = Constants.layout.getTagPose(tagId).get().toPose2d();
            
            // Determine which hexagon this tag belongs to
            Translation2d hexagonCenter = isBlueHexagon(tagId) ? BLUE_HEXAGON_CENTER : RED_HEXAGON_CENTER;
            
            // Calculate the vector from the tag to the hexagon center
            Translation2d tagToCenter = new Translation2d(
                hexagonCenter.getX() - tagPose.getX(),
                hexagonCenter.getY() - tagPose.getY()
            );
            
            // Normalize this vector (make it unit length)
            double magnitude = Math.sqrt(
                tagToCenter.getX() * tagToCenter.getX() + 
                tagToCenter.getY() * tagToCenter.getY()
            );
            
            // The outward direction is opposite to the center direction
            Translation2d outwardDirection = new Translation2d(
                -tagToCenter.getX() / magnitude,
                -tagToCenter.getY() / magnitude
            );
            
            // Calculate the perpendicular vector for left/right offset
            // Rotate the outward vector 90 degrees left or right
            Translation2d lateralDirection;
            if (isLeft) {
                // Rotate 90 degrees counter-clockwise
                lateralDirection = new Translation2d(
                    -outwardDirection.getY(),
                    outwardDirection.getX()
                );
            } else {
                // Rotate 90 degrees clockwise
                lateralDirection = new Translation2d(
                    outwardDirection.getY(),
                    -outwardDirection.getX()
                );
            }
            
            // Apply the outward and lateral shifts
            Translation2d scoringPosition = new Translation2d(
                tagPose.getX() + outwardDirection.getX() * OUTWARD_SHIFT + lateralDirection.getX() * LATERAL_SHIFT,
                tagPose.getY() + outwardDirection.getY() * OUTWARD_SHIFT + lateralDirection.getY() * LATERAL_SHIFT
            );
            
            // Calculate the rotation - this should point toward the tag
            Rotation2d scoringRotation = new Rotation2d(
                Math.atan2(tagPose.getY() - scoringPosition.getY(), tagPose.getX() - scoringPosition.getX())
            );
            
            // Log the calculated values for debugging
            SmartDashboard.putNumber("Tag ID", tagId);
            SmartDashboard.putBoolean("Is Left", isLeft);
            SmartDashboard.putNumber("Scoring X", scoringPosition.getX());
            SmartDashboard.putNumber("Scoring Y", scoringPosition.getY());
            SmartDashboard.putNumber("Scoring Rotation", scoringRotation.getDegrees());
            
            return new Pose2d(scoringPosition, scoringRotation);
            
        } catch (Exception e) {
            // If there's an error, fall back to the constants
            System.err.println("Error calculating scoring position for tag " + tagId + ": " + e.getMessage());
            return fallbackPosition(tagId, isLeft);
        }
    }
    
    /**
     * Determines if a tag ID belongs to the blue hexagon
     */
    private static boolean isBlueHexagon(int tagId) {
        return tagId >= 17 && tagId <= 22;
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