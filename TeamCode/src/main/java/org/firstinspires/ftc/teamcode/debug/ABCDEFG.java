package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;

@TeleOp(group = "Debug")
public class ABCDEFG extends OpMode
{
    BackArm arm;

    @Override
    public void init()
    {
        arm = new BackArm(hardwareMap);
    }

    @Override
    public void start()
    {
        arm.rest();
    }

    @Override
    public void loop()
    {

    }
}
