package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Base;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends Base
{
    @Override
    public void init()
    {
        initHardware();
        telemetry.addLine("Init finished.");
        telemetry.update();
    }

    @Override
    public void loop() {
        if (gamepad1.a) clawModule.open();
        if (gamepad1.b) clawModule.close();
    }
}
