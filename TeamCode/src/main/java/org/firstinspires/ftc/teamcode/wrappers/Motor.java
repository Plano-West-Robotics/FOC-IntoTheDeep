package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motor
{
    public DcMotor motor;

    public Motor(HardwareMap hardwareMap, String name)
    {
        motor = hardwareMap.get(DcMotor.class, name);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power)
    {
        motor.setPower(power);
    }

    public double getPower()
    {
        return motor.getPower();
    }

    public void stop()
    {
        setPower(0);
    }

    public void resetEncoder(boolean useEncoder)
    {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (useEncoder) useEncoder();
        else noEncoder();
    }

    public void noEncoder()
    {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void useEncoder()
    {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setTarget(double target)
    {
        motor.setTargetPosition((int) target);
    }

    public void toPosition()
    {
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean isBusy()
    {
        return motor.isBusy();
    }
}
