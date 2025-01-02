package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorPair
{
    public Motor leftMotor, rightMotor;

    public MotorPair(HardwareMap hardwareMap, String leftMotorName, String rightMotorName)
    {
        leftMotor = new Motor(hardwareMap, leftMotorName);
        rightMotor = new Motor(hardwareMap, rightMotorName);
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
        return leftMotor.isBusy() || rightMotor.isBusy();
    }

    /**
     * Finds the difference of the motor pair's power values.
     * @return the left motor's power minus the right power's motor
     */
    public double getDiffPower()
    {
        return leftMotor.getPower() - rightMotor.getPower();
    }

    public double getAveragePower()
    {
        return (leftMotor.getPower() + rightMotor.getPower()) / 2.0;
    }

    public double[] getPower()
    {
        return new double[] {leftMotor.getPower(), rightMotor.getPower()};
    }

    public void setPower(double power)
    {
        setPower(power, power);
    }

    public void setPower(double leftPower, double rightPower)
    {
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    public DcMotor.RunMode[] getMode()
    {
        return new DcMotor.RunMode[] {leftMotor.getMode(), rightMotor.getMode()};
    }

    public void setMode(DcMotor.RunMode mode)
    {
        leftMotor.setMode(mode);
        rightMotor.setMode(mode);
    }

    public int[] getTarget()
    {
        return new int[] {leftMotor.getTarget(), rightMotor.getTarget()};
    }

    public void setTarget(double target)
    {
        setTarget(target, target);
    }

    public void setTarget(double leftTarget, double rightTarget)
    {
        leftMotor.setTarget(leftTarget);
        rightMotor.setTarget(rightTarget);
    }

    /**
     * Finds the difference of the motor pair's position values.
     * @return the left motor's position minus the right power's position
     */
    public int getDiffPosition()
    {
        return leftMotor.getPosition() - rightMotor.getPosition();
    }

    public double getAveragePosition()
    {
        return (leftMotor.getPosition() + rightMotor.getPosition()) / 2.0;
    }

    public int[] getPosition()
    {
        return new int[] {leftMotor.getPosition(), rightMotor.getPosition()};
    }

    public Motor getLeftMotor()
    {
        return leftMotor;
    }

    public Motor getRightMotor()
    {
        return rightMotor;
    }
}
