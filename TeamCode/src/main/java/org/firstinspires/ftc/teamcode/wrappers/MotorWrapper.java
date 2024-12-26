package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorWrapper
{
    public DcMotor motor;

    public MotorWrapper(HardwareMap hardwareMap, String name)
    {
        motor = hardwareMap.get(DcMotor.class, name);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void forward()
    {
        setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void reverse()
    {
        setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void stop()
    {
        setPower(0);
    }

    public void resetEncoder(boolean useEncoder)
    {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (useEncoder) useEncoder();
        else noEncoder();
    }

    public void useEncoder()
    {
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void noEncoder()
    {
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void toPosition()
    {
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean isBusy()
    {
        return motor.isBusy();
    }

    public DcMotorSimple.Direction getDirection()
    {
        return motor.getDirection();
    }

    public void setDirection(DcMotorSimple.Direction direction)
    {
        motor.setDirection(direction);
    }

    public double getPower()
    {
        return motor.getPower();
    }

    public void setPower(double power)
    {
        motor.setPower(power);
    }

    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior()
    {
        return motor.getZeroPowerBehavior();
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior)
    {
        motor.setZeroPowerBehavior(behavior);
    }

    public DcMotor.RunMode getMode()
    {
        return motor.getMode();
    }

    public void setMode(DcMotor.RunMode mode)
    {
        motor.setMode(mode);
    }

    public int getTarget()
    {
        return motor.getTargetPosition();
    }

    public void setTarget(double target)
    {
        motor.setTargetPosition((int) Math.round(target));
    }

    public int getPosition()
    {
        return motor.getCurrentPosition();
    }
}
