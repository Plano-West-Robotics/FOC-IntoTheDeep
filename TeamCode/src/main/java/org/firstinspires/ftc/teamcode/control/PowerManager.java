package org.firstinspires.ftc.teamcode.control;

public class PowerManager
{
    public double slowdown, initialError, minPower, maxPower;

    public PowerManager(int slowdown, int initialError, double minPower, double maxPower)
    {
        this.slowdown = slowdown;
        this.initialError = initialError;
        this.minPower = minPower;
        this.maxPower = maxPower;
    }

    public double getPower(int currError)
    {
        if (currError >= 0 && currError <= Math.min(slowdown, initialError / 2.0))
        {
            return ((maxPower - minPower) / slowdown) * currError + minPower;
        }
        else if (currError > slowdown && currError <= initialError - slowdown) return maxPower;
        else if (currError >= Math.max(initialError - slowdown, initialError / 2.0) && currError <= initialError)
        {
            return ((minPower - maxPower) / slowdown) * (currError - initialError) + minPower;
        }
        return minPower; // idk
    }


    public double getSlowdown()
    {
        return  slowdown;
    }

    public double getInitialError()
    {
        return  initialError;
    }

    public double getMinPower()
    {
        return  minPower;
    }

    public double getMaxPower()
    {
        return maxPower;
    }

    // HACK:
    public void addToInitialError(double x) {
        this.initialError += x;
    }
}
