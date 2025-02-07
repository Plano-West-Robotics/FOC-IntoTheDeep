package org.firstinspires.ftc.teamcode.debug;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.wrappers.Motor;

@Config
@TeleOp(group = "Debug")
public class DrivetrainDebugger extends OpMode
{
    public static double frPower, flPower, brPower, blPower;

    public Motor fr, fl, br, bl;
    public Motor[] motors;

    @Override
    public void init()
    {
        fr = new Motor(hardwareMap, "fr");
        fl = new Motor(hardwareMap, "fl");
        br = new Motor(hardwareMap, "br");
        bl = new Motor(hardwareMap, "bl");

        fl.reverse();
        bl.reverse();

        motors = new Motor[] {fr, fl, br, bl};

        for (Motor motor : motors) motor.noEncoder();
    }

    @Override
    public void loop()
    {
        fr.setPower(frPower);
        fl.setPower(flPower);
        br.setPower(brPower);
        bl.setPower(blPower);
    }
}
