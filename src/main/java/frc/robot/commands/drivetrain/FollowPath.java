package frc.robot.commands.drivetrain;

import java.io.IOException;
import java.util.List;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.spline.Spline;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.TrajectoryGenerator.ControlVectorList;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class FollowPath extends RamseteCommand{

    private static final Drivetrain mDrivetrain = Drivetrain.getInstance();
    private static final TrajectoryConfig mTrajectoryConfig = new TrajectoryConfig(Constants.Drivetrain.maxVelocity, Constants.Drivetrain.maxAcceleration);

    private FollowPath(Trajectory trajectory) {
        super(trajectory, mDrivetrain::getPose, new RamseteController(Constants.Drivetrain.kB, Constants.Drivetrain.kZeta), new DifferentialDriveKinematics(Constants.Drivetrain.kTrackWidth), mDrivetrain::drivePower, mDrivetrain);
        setName("Follow Path");
    }

    public FollowPath(Spline.ControlVector initial,List<Translation2d> interiorWaypoints,Spline.ControlVector end) {
        this(TrajectoryGenerator.generateTrajectory(initial, interiorWaypoints, end, mTrajectoryConfig));
    }

    public FollowPath(Pose2d start, List<Translation2d> interiorWaypoints, Pose2d end) {
        this(TrajectoryGenerator.generateTrajectory(start, interiorWaypoints, end, mTrajectoryConfig));
    }

    public FollowPath(ControlVectorList controlVectors) {
        this(TrajectoryGenerator.generateTrajectory(controlVectors, mTrajectoryConfig));
    }

    public FollowPath(List<Pose2d> waypoints) {
        this(TrajectoryGenerator.generateTrajectory(waypoints, mTrajectoryConfig));
    }

    public FollowPath(String fileName) throws IOException {
        this(TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve(fileName)));
    }
    
}
