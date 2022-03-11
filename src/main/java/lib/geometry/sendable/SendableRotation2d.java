package lib.geometry.sendable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SendableRotation2d extends Rotation2d implements Sendable {
    
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Rotation2d");
        builder.addDoubleProperty("radians", this::getRadians, null);
    }
    
    /** Constructs a Rotation2d with a default angle of 0 degrees. */
    public SendableRotation2d() {
        super();
    }

    /**
     * Constructs a Rotation2d with the given radian value. The x and y don't have to be normalized.
     *
     * @param value The value of the angle in radians.
     */
    @JsonCreator
    public SendableRotation2d(@JsonProperty(required = true, value = "radians") double value) {
        super(value);
    }

    public SendableRotation2d(Rotation2d rotation) {
        super(rotation.getRadians());
    }

    /**
     * Constructs and returns a Rotation2d with the given degree value.
     *
     * @param degrees The value of the angle in degrees.
     * @return The rotation object with the desired angle value.
     */
    public static SendableRotation2d fromDegrees(double degrees) {
        return new SendableRotation2d(Rotation2d.fromDegrees(degrees));
    }

    /**
     * Adds two rotations together, with the result being bounded between -pi and
     * pi.
     *
     * <p>
     * For example, Rotation2d.fromDegrees(30) + Rotation2d.fromDegrees(60) =
     * Rotation2d{-pi/2}
     *
     * @param other The rotation to add.
     * @return The sum of the two rotations.
     */
    public SendableRotation2d plus(Rotation2d other) {
        return new SendableRotation2d(rotateBy(other));
    }

    /**
     * Subtracts the new rotation from the current rotation and returns the new
     * rotation.
     *
     * <p>
     * For example, Rotation2d.fromDegrees(10) - Rotation2d.fromDegrees(100) =
     * Rotation2d{-pi/2}
     *
     * @param other The rotation to subtract.
     * @return The difference between the two rotations.
     */
    public SendableRotation2d minus(Rotation2d other) {
        return new SendableRotation2d(super.minus(other));
    }

    /**
     * Takes the inverse of the current rotation. This is simply the negative of the
     * current angular
     * value.
     *
     * @return The inverse of the current rotation.
     */
    public SendableRotation2d unaryMinus() {
        return new SendableRotation2d(super.unaryMinus());
    }

    /**
     * Multiplies the current rotation by a scalar.
     *
     * @param scalar The scalar.
     * @return The new scaled Rotation2d.
     */
    public SendableRotation2d times(double scalar) {
        return new SendableRotation2d(super.times(scalar));
    }

    /**
     * Adds the new rotation to the current rotation using a rotation matrix.
     *
     * <p>
     * The matrix multiplication is as follows:
     *
     * <pre>
     * [cos_new]   [other.cos, -other.sin][cos]
     * [sin_new] = [other.sin,  other.cos][sin]
     * value_new = atan2(sin_new, cos_new)
     * </pre>
     *
     * @param other The rotation to rotate by.
     * @return The new rotated Rotation2d.
     */
    public SendableRotation2d rotateBy(Rotation2d other) {
        return new SendableRotation2d(super.rotateBy(other));
    }

    @Override
    @SuppressWarnings("ParameterName")
    public SendableRotation2d interpolate(Rotation2d endValue, double t) {
        return new SendableRotation2d(super.interpolate(endValue, t));
    }
}
