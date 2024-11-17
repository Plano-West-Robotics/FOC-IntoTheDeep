package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSlides
{
    public DcMotor motorL, motorR;
    public double SPEED_MULTIPLIER;
    public int MAX_POSITION;

    public IntakeSlides(HardwareMap hardwareMap)
    {
        motorL = hardwareMap.get(DcMotor.class, "iSlideL");
        motorR = hardwareMap.get(DcMotor.class, "iSlideR");

        motorR.setDirection(DcMotorSimple.Direction.REVERSE);

        motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorL.setPower(0);
        motorR.setPower(0);

        MAX_POSITION = 1100;
        SPEED_MULTIPLIER = 0.4;
    }

    public void setPower(double input)
    {
        motorL.setPower(input * SPEED_MULTIPLIER);
        motorR.setPower(input * SPEED_MULTIPLIER);
    }
}
