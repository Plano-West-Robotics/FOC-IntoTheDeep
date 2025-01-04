package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorPair
{
    public Motor left, right;

    public MotorPair(HardwareMap hardwareMap, String leftMotorName, String rightMotorName)
    {
        left = new Motor(hardwareMap, leftMotorName);
        right = new Motor(hardwareMap, rightMotorName);
    }

    public void zeroPower()
    {
        setPower(0);
    }

    public void resetEncoders(boolean useEncoders)
    {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (useEncoders) useEncoders();
        else noEncoders();
    }

    public void useEncoders()
    {
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void noEncoders()
    {
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void toPosition()
    {
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void goTo(double target, double power)
    {
        setTarget(target);
        toPosition();
        setPower(power);
    }

    public boolean isBusy()
    {
        return left.isBusy() || right.isBusy();
    }

    /**
     * Finds the difference of the motor pair's power values.
     * @return the left motor's power minus the right power's motor
     */
    public double getDiffPower()
    {
        return left.getPower() - right.getPower();
    }

    public double getAveragePower()
    {
        return (left.getPower() + right.getPower()) / 2.0;
    }

    public double[] getPower()
    {
        return new double[] {left.getPower(), right.getPower()};
    }

    public void setPower(double power)
    {
        setPower(power, power);
    }

    public void setPower(double leftPower, double rightPower)
    {
        left.setPower(leftPower);
        right.setPower(rightPower);
    }

    public DcMotor.RunMode[] getMode()
    {
        return new DcMotor.RunMode[] {left.getMode(), right.getMode()};
    }

    public void setMode(DcMotor.RunMode mode)
    {
        left.setMode(mode);
        right.setMode(mode);
    }

    public int[] getTarget()
    {
        return new int[] {left.getTarget(), right.getTarget()};
    }

    public void setTarget(double target)
    {
        setTarget(target, target);
    }

    public void setTarget(double leftTarget, double rightTarget)
    {
        left.setTarget(leftTarget);
        right.setTarget(rightTarget);
    }

    /**
     * Finds the difference of the motor pair's position values.
     * @return the left motor's position minus the right power's position
     */
    public int getDiffPosition()
    {
        return left.getPosition() - right.getPosition();
    }

    public int getAveragePosition()
    {
        return (int) Math.round((left.getPosition() + right.getPosition()) / 2.0);
    }

    public int[] getPosition()
    {
        return new int[] {left.getPosition(), right.getPosition()};
    }

    public Motor getLeft()
    {
        return left;
    }

    public Motor getRight()
    {
        return right;
    }
}
