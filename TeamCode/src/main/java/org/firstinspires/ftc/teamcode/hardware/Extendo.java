package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utils;
import org.firstinspires.ftc.teamcode.wrappers.MotorPair;

public abstract class Extendo extends MotorPair
{
    // Parameters associated with each Extendo's calculateAllowedPower method.
    public final double minPower; // the minimum value the method may return
    public final double maxPower; // the maximum value the method may return
    public final double k; // a constant that adjusts the steepness of the curve
    public final double maxPosition; // the motors' encoder value at the slides' maximum extension

    public Extendo(HardwareMap hardwareMap, String leftMotorName, String rightMotorName,
                   double minPower, double maxPower, double k, double maxPosition)
    {
        super(hardwareMap, leftMotorName, rightMotorName);

        this.minPower = minPower;
        this.maxPower = maxPower;
        this.k = k;
        this.maxPosition = maxPosition;

        Utils.validate(minPower > 0 && minPower < 1, "minPower must be greater than 0 but less than 1.");
        Utils.validate(maxPower > 0 && maxPower < 1, "maxPower must be greater than 0 but less than 1.");
        Utils.validate(minPower <= maxPower, "minPower must be less than or equal to maxPower.");
        Utils.validate(maxPosition > 0, "maxPosition must be positive.");
    }

    /**
     * Calculates the maximum power value that can safely be used in a setPower call for a motor
     * which drives a linear slide. The graph of this function, where the x-axis is
     * {@code currentPosition} and the y-axis is the return value, tapers down to
     * y = {@code minPower} near x = 0 and x = {@code maxPosition}, approaches y = {@code maxPower}
     * in for x-values in the middle, and uses a reflection at x = 0 and x = {@code maxPosition}
     * (accomplished with the local double {@code c}) so that the method still returns a reasonable
     * power value when x is not within the domain [0, {@code maxPosition}].
     * @return the maximum power value that can safely be used given the current average position
     */
    public double calculateAllowedPower() throws IllegalArgumentException
    {
        double currentPosition = getAveragePosition();
        double a = Math.tanh(k * currentPosition);
        double b = Math.tanh(k * (currentPosition - maxPosition));
        double c = (maxPower - minPower) * (a - b) + 2 * minPower - maxPower;
        return Math.abs(c - minPower) + minPower;
    }
}
