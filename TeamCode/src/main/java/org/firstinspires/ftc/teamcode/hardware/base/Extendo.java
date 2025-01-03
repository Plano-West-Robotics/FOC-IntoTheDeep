package org.firstinspires.ftc.teamcode.hardware.base;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Utils;
import org.firstinspires.ftc.teamcode.wrappers.MotorPair;

public abstract class Extendo extends MotorPair
{
    public PIDFController controller;

    public final double minPosition;
    public final double halfPosition;

    // Parameters associated with an Extendo's calculateAllowedPower method.
    public final double minPower; // the minimum value the method may return
    public final double maxPower; // the maximum value the method may return
    public final double k; // a constant that adjusts the steepness of the curve
    public final double maxPosition; // the motors' encoder value at the slides' maximum extension

    public Extendo(HardwareMap hardwareMap, String leftMotorName, String rightMotorName,
                   int minPosition, double minPower, double maxPower, double k,
                   int maxPosition, double p, double i, double d, double f)
    {
        super(hardwareMap, leftMotorName, rightMotorName);

        this.minPosition = minPosition;
        this.halfPosition = Math.round((minPosition + maxPosition) / 2.0);

        this.minPower = minPower;
        this.maxPower = maxPower;
        this.k = k;
        this.maxPosition = maxPosition;

        controller = new PIDFController(p, i, d, f);

        Utils.validate(
                minPosition < maxPosition,
                "minPosition must be less than maxPosition."
        );
        Utils.validate(
                minPower > 0 && minPower < 1,
                "minPower must be greater than 0 but less than 1."
        );
        Utils.validate(
                maxPower > 0 && maxPower < 1,
                "maxPower must be greater than 0 but less than 1."
        );
        Utils.validate(
                minPower <= maxPower,
                "minPower must be less than or equal to maxPower."
        );
        Utils.validate(
                maxPosition > 0,
                "maxPosition must be positive."
        );
    }

    @Override
    public void setPower(double power)
    {
        setPower(power, true);
    }

    public void setPower(double power, boolean limitPower)
    {
        if (!limitPower)
        {
            super.setPower(power);
            return;
        }

        double maxAllowedPower = calculateAllowedPower();
        double targetPower;
        if (atUpperHalf()) targetPower = Range.clip(power, -maxPower, maxAllowedPower);
        else targetPower = Range.clip(power, -maxAllowedPower, maxPower);
        super.setPower(targetPower);
    }

    /**
     * Calculates the magnitude of the maximum power value that can safely be used in a setPower
     * call for a motor which drives a linear slide. The graph of this function, where the x-axis is
     * {@code currentPosition} and the y-axis is the return value, tapers down to
     * y = {@code minPower} near x = 0 and x = {@code maxPosition}, approaches y = {@code maxPower}
     * in for x-values in the middle, and uses a reflection at x = 0 and x = {@code maxPosition}
     * (accomplished with the local double {@code c}) so that the method still returns a reasonable
     * power value when x is not within the domain [0, {@code maxPosition}].
     * @return the maximum power value that can safely be used given the current average position
     */
    public double calculateAllowedPower()
    {
        double currentPosition = getAveragePosition();
        double a = Math.tanh(k * currentPosition);
        double b = Math.tanh(k * (currentPosition - maxPosition));
        double c = (maxPower - minPower) * (a - b) + 2 * minPower - maxPower;
        return Math.abs(c - minPower) + minPower;
    }

    public double calculateControllerPower(int targetPosition)
    {
        Utils.validate(
                targetPosition >= minPosition,
                "targetPosition must be greater than or equal to minPosition."
        );
        Utils.validate(
                targetPosition <= maxPosition,
                "targetPosition must be less than or equal to maxPosition."
        );
        double currentPosition = getAveragePosition();
        return controller.calculate(currentPosition, targetPosition);
    }

    public boolean atPosition(int targetPosition, int errorMargin)
    {
        return Math.abs(getAveragePosition() - targetPosition) <= Math.abs(errorMargin);
    }

    public boolean atLowerHalf()
    {
        return getAveragePosition() < halfPosition;
    }

    public boolean atUpperHalf()
    {
        return getAveragePosition() > halfPosition;
    }

    public boolean isRetracted()
    {
        return getAveragePosition() <= minPosition;
    }

    public boolean isExtended()
    {
        return getAveragePosition() >= maxPosition;
    }

    public int getHalfPosition()
    {
        return (int) Math.round(halfPosition);
    }
}
