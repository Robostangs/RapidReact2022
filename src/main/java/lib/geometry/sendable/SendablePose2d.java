package lib.geometry.sendable;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SendablePose2d extends Pose2d implements Sendable {
    private final SendableTranslation2d m_translation;
    private final SendableRotation2d m_rotation;

    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Pose2d");
        builder.addDoubleProperty("x", this::getX, null);
        builder.addDoubleProperty("y", this::getY, null);
        builder.addDoubleProperty("radians", () -> getRotation().getRadians(), null);
    }

    /**
     * Constructs a pose at the origin facing toward the positive X axis. (Translation2d{0, 0} and
     * Rotation{0})
     */
    public SendablePose2d() {
        this(new SendableTranslation2d(), new SendableRotation2d());
    }

    /**
     * Constructs a pose with the specified translation and rotation.
     *
     * @param translation The translational component of the pose.
     * @param rotation The rotational component of the pose.
     */
    @JsonCreator
    public SendablePose2d(
        @JsonProperty(required = true, value = "translation") Translation2d translation,
        @JsonProperty(required = true, value = "rotation") Rotation2d rotation) {
        this(new SendableTranslation2d(translation), new SendableRotation2d(rotation));
    }

    /**
     * Constructs a pose with the specified translation and rotation.
     *
     * @param translation The translational component of the pose.
     * @param rotation The rotational component of the pose.
     */
    @JsonCreator
    public SendablePose2d(
        @JsonProperty(required = true, value = "translation") SendableTranslation2d translation,
        @JsonProperty(required = true, value = "rotation") SendableRotation2d rotation) {
        super(translation, rotation);
        m_translation = translation;
        m_rotation = rotation;
    }

    /**
     * Convenience constructors that takes in x and y values directly instead of having to construct a
     * Translation2d.
     *
     * @param x The x component of the translational component of the pose.
     * @param y The y component of the translational component of the pose.
     * @param rotation The rotational component of the pose.
     */
    public SendablePose2d(double x, double y, Rotation2d rotation) {
        this(x, y, new SendableRotation2d(rotation));
    }

    /**
     * Convenience constructors that takes in x and y values directly instead of having to construct a
     * Translation2d.
     *
     * @param x The x component of the translational component of the pose.
     * @param y The y component of the translational component of the pose.
     * @param rotation The rotational component of the pose.
     */
    public SendablePose2d(double x, double y, SendableRotation2d rotation) {
        this(new SendableTranslation2d(x, y), rotation)
    }

    /**
     * Constructs a SendablePose2d from a normal Pose2d.
    */
    public SendablePose2d(Pose2d pose) {
        this(new SendableTranslation2d(pose.getTranslation()), new SendableRotation2d(pose.getRotation()));
    }

    public SendablePose2d(SendablePose2d pose) {
        this(pose.getTranslation(), pose.getRotation());
    }

    /**
     * Transforms the pose by the given transformation and returns the new transformed pose.
     *
     * <p>The matrix multiplication is as follows [x_new] [cos, -sin, 0][transform.x] [y_new] += [sin,
     * cos, 0][transform.y] [t_new] [0, 0, 1][transform.t]
     *
     * @param other The transform to transform the pose by.
     * @return The transformed pose.
     */
    public SendablePose2d plus(Transform2d other) {
        return transformBy(other);
    }

    /**
     * Returns the Transform2d that maps the one pose to another.
     *
     * @param other The initial pose of the transformation.
     * @return The transform that maps the other pose to the current pose.
     */
    public SendableTransform2d minus(Pose2d other) {
        final var pose = this.relativeTo(other);
        return new SendableTransform2d(pose.getTranslation(), pose.getRotation());
    }

    /**
     * Returns the translation component of the transformation.
     *
     * @return The translational component of the pose.
     */
    @JsonProperty
    public SendableTranslation2d getTranslation() {
        return m_translation;
    }

    /**
     * Returns the X component of the pose's translation.
     *
     * @return The x component of the pose's translation.
     */
    public double getX() {
        return m_translation.getX();
    }

    /**
     * Returns the Y component of the pose's translation.
     *
     * @return The y component of the pose's translation.
     */
    public double getY() {
        return m_translation.getY();
    }

    /**
     * Returns the rotational component of the transformation.
     *
     * @return The rotational component of the pose.
     */
    @JsonProperty
    public SendableRotation2d getRotation() {
        return m_rotation;
    }

    /**
     * Transforms the pose by the given transformation and returns the new pose. See + operator for
     * the matrix multiplication performed.
     *
     * @param other The transform to transform the pose by.
     * @return The transformed pose.
     */
    public SendablePose2d transformBy(Transform2d other) {
        return new SendablePose2d(
            m_translation.plus(other.getTranslation().rotateBy(m_rotation)),
            m_rotation.plus(other.getRotation()));
    }

    /**
     * Returns the other pose relative to the current pose.
     *
     * <p>This function can often be used for trajectory tracking or pose stabilization algorithms to
     * get the error between the reference and the current pose.
     *
     * @param other The pose that is the origin of the new coordinate frame that the current pose will
     *     be converted into.
     * @return The current pose relative to the new origin pose.
     */
    public SendablePose2d relativeTo(Pose2d other) {
        var transform = new Transform2d(other, this);
        return new SendablePose2d(transform.getTranslation(), transform.getRotation());
    }

    /**
     * Obtain a new Pose2d from a (constant curvature) velocity.
     *
     * <p>See <a href="https://file.tavsys.net/control/controls-engineering-in-frc.pdf">Controls
     * Engineering in the FIRST Robotics Competition</a> section 10.2 "Pose exponential" for a
     * derivation.
     *
     * <p>The twist is a change in pose in the robot's coordinate frame since the previous pose
     * update. When the user runs exp() on the previous known field-relative pose with the argument
     * being the twist, the user will receive the new field-relative pose.
     *
     * <p>"Exp" represents the pose exponential, which is solving a differential equation moving the
     * pose forward in time.
     *
     * @param twist The change in pose in the robot's coordinate frame since the previous pose update.
     *     For example, if a non-holonomic robot moves forward 0.01 meters and changes angle by 0.5
     *     degrees since the previous pose update, the twist would be Twist2d{0.01, 0.0,
     *     toRadians(0.5)}
     * @return The new pose of the robot.
     */
    public SendablePose2d exp(Twist2d twist) {
        double dx = twist.dx;
        double dy = twist.dy;
        double dtheta = twist.dtheta;

        double sinTheta = Math.sin(dtheta);
        double cosTheta = Math.cos(dtheta);

        double s;
        double c;
        if (Math.abs(dtheta) < 1E-9) {
        s = 1.0 - 1.0 / 6.0 * dtheta * dtheta;
        c = 0.5 * dtheta;
        } else {
        s = sinTheta / dtheta;
        c = (1 - cosTheta) / dtheta;
        }
        var transform =
            new Transform2d(
                new Translation2d(dx * s - dy * c, dx * c + dy * s),
                new Rotation2d(cosTheta, sinTheta));

        return this.plus(transform);
    }

    /**
     * Returns a Twist2d that maps this pose to the end pose. If c is the output of a.Log(b), then
     * a.Exp(c) would yield b.
     *
     * @param end The end pose for the transformation.
     * @return The twist that maps this to end.
     */
    public SendableTwist2d log(Pose2d end) {
        final var transform = end.relativeTo(this);
        final var dtheta = transform.getRotation().getRadians();
        final var halfDtheta = dtheta / 2.0;

        final var cosMinusOne = transform.getRotation().getCos() - 1;

        double halfThetaByTanOfHalfDtheta;
        if (Math.abs(cosMinusOne) < 1E-9) {
        halfThetaByTanOfHalfDtheta = 1.0 - 1.0 / 12.0 * dtheta * dtheta;
        } else {
        halfThetaByTanOfHalfDtheta = -(halfDtheta * transform.getRotation().getSin()) / cosMinusOne;
        }

        Translation2d translationPart =
            transform
                .getTranslation()
                .rotateBy(new Rotation2d(halfThetaByTanOfHalfDtheta, -halfDtheta))
                .times(Math.hypot(halfThetaByTanOfHalfDtheta, halfDtheta));

        return new SendableTwist2d(translationPart.getX(), translationPart.getY(), dtheta);
    }

    @Override
    public String toString() {
        return String.format("Pose2d(%s, %s)", m_translation, m_rotation);
    }

    /**
     * Checks equality between this Pose2d and another object.
     *
     * @param obj The other object.
     * @return Whether the two objects are equal or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pose2d) {
            return ((Pose2d) obj).m_translation.equals(m_translation)
                && ((Pose2d) obj).m_rotation.equals(m_rotation);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_translation, m_rotation);
    }

    @Override
    @SuppressWarnings("ParameterName")
    public SendablePose2d interpolate(Pose2d endValue, double t) {
        if (t < 0) {
            return this;
        } else if (t >= 1) {
            return new SendablePose2d(endValue);
        } else {
            var twist = this.log(endValue);
            var scaledTwist = new Twist2d(twist.dx * t, twist.dy * t, twist.dtheta * t);
            return this.exp(scaledTwist);
        }
    }
}
