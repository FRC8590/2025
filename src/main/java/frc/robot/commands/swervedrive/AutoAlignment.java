package frc.robot.commands.swervedrive;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAlignment extends SequentialCommandGroup {

    private HolonomicDriveController roughHolonomicDriveController, smallHolonomicDriveController;
    /**
     * creates a precise auto-alignment command
     * NOTE: AutoBuilder must be configured!
     * the command has two steps:
     * 1. path-find to the target pose, roughly
     * 2. accurate auto alignment
     * */
    public AutoAlignment(
            PathConstraints constraints,
            Supplier<Pose2d> robotPoseSupplier,
            Consumer<ChassisSpeeds> robotRelativeSpeedsOutput,
            Subsystem driveSubsystem,
            Pose2d targetPose
    ) {

        roughHolonomicDriveController = new HolonomicDriveController(
            new PIDController(5, 0, 0),  // X controller 
            new PIDController(5, 0, 0),  // Y controller - increased P, added I and D terms
            new ProfiledPIDController(4, 0, 0, 
                new TrapezoidProfile.Constraints(3, 6))); 
        smallHolonomicDriveController = new HolonomicDriveController(
                new PIDController(13, 0, 0),  // X controller 
                new PIDController(13, 0, 0),  // Y controller - increased P, added I and D terms
                new ProfiledPIDController(10, 0, 0, 
                        new TrapezoidProfile.Constraints(3, 6))); 
            

            
        // Tighter tolerance for Y direction
        smallHolonomicDriveController.setTolerance(new Pose2d(0.01, 0.01, Rotation2d.fromDegrees(1)));
        roughHolonomicDriveController.setTolerance(new Pose2d(0.15, 0.15, Rotation2d.fromDegrees(1)));

        final Command
                pathFindToTargetRough = AutoBuilder.pathfindToPose(targetPose, constraints, 0),


        
                roughAlignment = new FunctionalCommand(
                        () -> {},
                        () -> {
                                Pose2d currentPose = robotPoseSupplier.get(); 
                                robotRelativeSpeedsOutput.accept(roughHolonomicDriveController.calculate(
                                        currentPose,
                                        targetPose,
                                        0,
                                        targetPose.getRotation()
                                ));
                        },
                        (interrupted) ->
                                robotRelativeSpeedsOutput.accept(new ChassisSpeeds()),
                                roughHolonomicDriveController::atReference        
                        ),
                preciseAlignment = new FunctionalCommand(
                                () -> {},
                                () -> {
                                        Pose2d currentPose = robotPoseSupplier.get(); 
                                        robotRelativeSpeedsOutput.accept(smallHolonomicDriveController.calculate(
                                                currentPose,
                                                targetPose,
                                                0,
                                                targetPose.getRotation()
                                        ));
                                },
                                (interrupted) ->
                                        robotRelativeSpeedsOutput.accept(new ChassisSpeeds()),
                                        smallHolonomicDriveController::atReference        
                                );
        

        
        // super.addCommands(new DriveToPose(targetPose));
        super.addCommands(roughAlignment);
        super.addCommands(new WaitCommand(0.15));
        // super.addCommands(new PrintCommand("DONEEE"));
        super.addCommands(preciseAlignment);


        super.addRequirements(driveSubsystem);
    }
}