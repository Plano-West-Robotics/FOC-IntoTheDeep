package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OuttakeSlides
{
    public DcMotor motorL, motorR;
    public double SPEED_MULTIPLIER, GRAVITY_FEEDFORWARD;
    public int MAX_POSITION;

    public OuttakeSlides(HardwareMap hardwareMap)
    {
        motorL = hardwareMap.get(DcMotor.class, "oSlideL");
        motorR = hardwareMap.get(DcMotor.class, "oSlideR");

        motorL.setDirection(DcMotorSimple.Direction.REVERSE);

        motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorL.setPower(0);
        motorR.setPower(0);

        SPEED_MULTIPLIER = 0.5;
        GRAVITY_FEEDFORWARD = 0.001;
        MAX_POSITION = 4000;
    }

    public void setPower(double input)
    {
        motorL.setPower(input * SPEED_MULTIPLIER + GRAVITY_FEEDFORWARD);
        motorR.setPower(input * SPEED_MULTIPLIER + GRAVITY_FEEDFORWARD);
    }
}
