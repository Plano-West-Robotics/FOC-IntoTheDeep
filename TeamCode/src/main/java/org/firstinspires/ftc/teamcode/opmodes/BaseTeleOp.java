package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Gamepads;

public abstract class BaseTeleOp extends OpMode
{
    public Hardware hardware;
    public Gamepads gamepads;

    @Override
    public final void init()
    {
        hardware = new Hardware(hardwareMap);
        gamepads = new Gamepads(gamepad1, gamepad2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        setup();
    }

    public void setup() {}

    @Override
    public final void loop()
    {
        gamepads.update(gamepad1, gamepad2);
        telemetry.update();
        run();
    }

    public void run() {}
}
