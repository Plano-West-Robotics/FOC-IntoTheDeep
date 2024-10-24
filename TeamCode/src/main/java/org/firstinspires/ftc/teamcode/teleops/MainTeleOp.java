package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.BaseTeleOp;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends BaseTeleOp
{
    @Override
    public void init()
    {
        initHardware();
        telemetry.addLine("Init finished.");
        telemetry.update();
    }

    @Override
    public void loop()
    {
        updateDrive();
    }
}
