package org.firstinspires.ftc.teamcode.hardware.base;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.roadrunner.Utils;
import org.firstinspires.ftc.teamcode.wrappers.MotorPair;

public abstract class Extendo extends MotorPair
{
    public final PIDFController controller;

    public final double gravityFeedforward;

    // Parameters used in the calculateAllowedPower method.
    public final double minPosition; // the motors' encoder value at the slides' minimum extension
    public final double maxPosition; // the motors' encoder value at the slides' maximum extension
    public final double minPower; // the minimum value the method may return
    public final double maxPower; // the maximum value the method may return
    public final double k; // a constant that adjusts the steepness of the curve
    public final double positionErrorTolerance;

    public Extendo(HardwareMap hardwareMap, String leftMotorName, String rightMotorName,
                   int minPosition, int maxPosition,
                   double minPower, double maxPower,
                   double k,
                   double p,
                   double i,
                   double d,
                   double f,
                   double positionErrorTolerance,
                   double gravityFeedforward)
    {
        super(hardwareMap, leftMotorName, rightMotorName);

        this.gravityFeedforward = gravityFeedforward;

        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.k = k;
        this.positionErrorTolerance = positionErrorTolerance;

        controller = new PIDFController(p, i, d, f);
        controller.setTolerance(positionErrorTolerance);

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

    public void halt()
    {
        setPower(0, false);
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
            super.setPower(power + gravityFeedforward);
            return;
        }

        double maxAllowedPower = calculateAllowedPower();
        double targetPower;
        if (atUpperHalf()) targetPower = Range.clip(power, -maxPower, maxAllowedPower);
        else targetPower = Range.clip(power, -maxAllowedPower, maxPower);
        super.setPower(targetPower + gravityFeedforward);
    }

    /**
     * Calculates the magnitude of the maximum power value that can safely be used in a setPower
     * call for a motor which drives a linear slide. The graph of this function, where the x-axis is
     * {@code currentPosition} and the y-axis is the return value, tapers down to
     * y = {@code minPower} near x = {@code minPosition} and x = {@code maxPosition}, approaches
     * y = {@code maxPower} in for x-values in the middle, and uses a reflection at
     * x = {@code minPosition} and x = {@code maxPosition} (accomplished with the local double
     * {@code c}) so that the method still returns a reasonable power value when x is not within the
     * domain [0, {@code maxPosition}].
     * @return the maximum power value that can safely be used given the current average position
     */
    public double calculateAllowedPower()
    {
        double currentPosition = getAveragePosition();
        double a = Math.tanh(k * (currentPosition - minPosition));
        double b = Math.tanh(k * (currentPosition - maxPosition));
        double c = (maxPower - minPower) * (a - b) + 2 * minPower - maxPower;
        return Math.abs(c - minPower) + minPower;
    }

    public double calculatePIDFControllerPower(int targetPosition)
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

    public boolean atLowerHalf()
    {
        return getAveragePosition() < getHalfPosition();
    }

    public boolean atUpperHalf()
    {
        return getAveragePosition() > getHalfPosition();
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
        return (int) Math.round((minPosition + maxPosition) / 2.0);
    }

    public Action getSlideAction(int targetPosition)
    {
        return new Action()
        {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {
                double power = calculatePIDFControllerPower(targetPosition);
                setPower(power, false);

                /*
                controller.atSetPoint() returns true when the controller's position error is less
                than or equal to its position error tolerance. False is returned because this tells
                Road Runner that this action is complete (extendo has reached its target position).
                 */
                if (controller.atSetPoint())
                {
                    setPower(0, false); // Holds extendo at target position.
                    return false;
                }

                return true; // when controller is not at set point.
            }
        };
    }

    public void setPos(int targetPosition)
    {
        double power = calculatePIDFControllerPower(targetPosition);
        setPower(power, false);
    }

    public boolean isReached(int targetPosition)
    {
        return (Math.abs(getAveragePosition() - targetPosition) <= positionErrorTolerance);
    }


}
