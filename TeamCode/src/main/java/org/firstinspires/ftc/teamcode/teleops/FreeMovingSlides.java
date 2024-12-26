package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "FreeMovingSlides")
public class FreeMovingSlides extends OpMode
{
    DcMotor motorL, motorR;

    @Override
    public void init()
    {
        motorL = hardwareMap.get(DcMotor.class, "oSlideL");
        motorR = hardwareMap.get(DcMotor.class, "oSlideR");

        motorL.setDirection(DcMotorSimple.Direction.REVERSE);

        motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motorL.setPower(0);
        motorR.setPower(0);
    }

    @Override
    public void loop()
    {
        telemetry.addData("Outtake Left Position", motorL.getCurrentPosition());
        telemetry.addData("Outtake Right Position", motorR.getCurrentPosition());

        telemetry.update();
    }
}
