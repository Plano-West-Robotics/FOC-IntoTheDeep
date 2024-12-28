package org.firstinspires.ftc.teamcode;

public final class Utils
{
    private Utils() {}

    /**
     * Calculates the maximum power value that can safely be used in a setPower call for a motor
     * which drives a linear slide. The graph of this function, where the x-axis is
     * {@code currentPosition} and the y-axis is the return value, tapers down to
     * y = {@code minPower} near x = 0 and x = {@code maxPosition}, approaches y = {@code maxPower}
     * in for x-values in the middle, and uses a reflection at x = 0 and x = {@code maxPosition}
     * (accomplished with the local double {@code c}) so that the method still returns a reasonable
     * power value when x is not within the domain [0, {@code maxPosition}].
     * @param minPower the minimum value the method may return
     * @param maxPower the maximum value the method may return
     * @param k a constant that influences the steepness of the mathematical function's graph
     * @param maxPosition the motor's encoder value at the slide's maximum extension
     * @param currentPosition the argument of mathematical function
     * @return the maximum power value that can safely be used given the current position
     * @throws IllegalArgumentException when any of the preconditions are not met
     */
    public static double calculateAllowedPower(double minPower, double maxPower, double k, int maxPosition, int currentPosition) throws IllegalArgumentException
    {
        validate(minPower > 0 && minPower < 1, "minPower must be greater than 0 but less than 1.");
        validate(maxPower > 0 && maxPower < 1, "maxPower must be greater than 0 but less than 1.");
        validate(minPower <= maxPower, "minPower must be less than or equal to maxPower.");
        validate(maxPosition > 0, "maxPosition must be positive.");

        double a = Math.tanh(k * currentPosition);
        double b = Math.tanh(k * (currentPosition - maxPosition));
        double c = (maxPower - minPower) * (a - b) + 2 * minPower - maxPower;
        return Math.abs(c - minPower) + minPower;
    }

    public static void validate(boolean condition, String message) throws RuntimeException
    {
        if (!condition) throw new RuntimeException(message);
    }
}
