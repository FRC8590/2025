package frc.robot.commands.swervedrive;

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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class AutoAlignment extends SequentialCommandGroup {

    private HolonomicDriveController holonomicDriveController;
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

        holonomicDriveController = new HolonomicDriveController(
            new PIDController(1, 0, 0), // X controller
            new PIDController(1, 0, 0), // Y controller
            new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14))); //rotation controller w/profiled pid controller
        /* tolerance for the precise approach */
        holonomicDriveController.setTolerance(new Pose2d(0.05, 0.05, Rotation2d.fromDegrees(5)));
        final Command
                pathFindToTargetRough = AutoBuilder.pathfindToPose(targetPose, constraints, 0.5),
                preciseAlignment = new FunctionalCommand(
                        () -> {},
                        () -> robotRelativeSpeedsOutput.accept(holonomicDriveController.calculate(
                                robotPoseSupplier.get(),
                                targetPose,
                                0,
                                targetPose.getRotation()
                        )),
                        (interrupted) ->
                                robotRelativeSpeedsOutput.accept(new ChassisSpeeds()),
                        holonomicDriveController::atReference
                        );

        super.addCommands(pathFindToTargetRough);
        super.addCommands(preciseAlignment);

        super.addRequirements(driveSubsystem);
    }
}