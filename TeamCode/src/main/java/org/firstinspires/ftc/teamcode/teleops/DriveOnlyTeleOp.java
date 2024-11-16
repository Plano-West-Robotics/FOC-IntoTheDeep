package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.BaseTeleOp;

@TeleOp(name = "DriveOnlyTeleOp")
public class DriveOnlyTeleOp extends BaseTeleOp
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
        drivetrain.takeControllerInput();
        drivetrain.drive();
    }
}
