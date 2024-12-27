package org.firstinspires.ftc.teamcode.util;

public final class Utils
{
    private Utils() {}

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
