package org.firstinspires.ftc.teamcode;

public class MathUtils
{
    public static final double K = 0.1; // Steepness constant for calcSpeed().

    /**
     * Applies a double logistic curve to an input (ticks) that returns an output which (nearly)
     * equals 0 when ticks is close to 0 or {@code maxTicks}, begins to decrease when
     * ticks={@code threshold} or ticks={@code maxTicks} - {@code threshold}, and never exceeds
     * {@code maxSpeed} nor drops below 0.
     * @param ticks     motor's current tick reading
     * @param maxTicks  motor's maximum tick reading in the slide system
     * @param threshold tick reading difference (distance from nearest endpoint) from which to begin
     *                  slowing down
     * @param maxSpeed  maximum value the function can return
     * @return          a weighted speed value (between 0 and maxSpeed, exclusive)
     */
    public static double calcSmartMaxSpeed(int ticks, double maxSpeed, int maxTicks, int threshold)
    {
        return (maxSpeed / (1 + Math.exp(-K * (ticks - threshold)))) - (maxSpeed / (1 + Math.exp(-K * (ticks - (maxTicks - threshold)))));
    }

    /**
     * Clips {@code value} if it is below {@code min} or above {@code max} and returns it;
     * returns {@code value} otherwise.
     * @param value the given value
     * @param min   the lower bound of the range
     * @param max   the upper bound of the range
     * @return      the evaluation of the clamp function
     */
    public static double clamp(double value, double min, double max)
    {
        return Math.min(max, Math.max(value, min));
    }

    private MathUtils() {}
}
