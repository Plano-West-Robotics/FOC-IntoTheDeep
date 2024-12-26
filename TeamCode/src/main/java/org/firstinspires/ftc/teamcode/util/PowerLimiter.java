package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.Range;

public class PowerLimiter
{
    public final double minPower, maxPower, k, maxPosition;

    public PowerLimiter(double minPower, double maxPower, double k, int maxPosition)
    {
        minPower = Range.clip(minPower, 0, 1);
        maxPower = Range.clip(maxPower, 0, 1);

        if (minPower >= maxPower)
            throw new IllegalArgumentException("minPower must be less than maxPower.");

        this.minPower = minPower;
        this.maxPower = maxPower;
        this.k = k;
        this.maxPosition = maxPosition;
    }

    public double calculateAllowedPower(int currentPosition)
    {
        double term1 = Math.tanh(k * currentPosition);
        double term2 = Math.tanh(k * (currentPosition - maxPosition));
        return (maxPower - minPower) * (term1 - term2) + 2 * minPower - maxPower;
    }

}
