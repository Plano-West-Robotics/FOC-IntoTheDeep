package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.control.Gamepads;

public abstract class OpModeWrapper extends OpMode
{
    public Gamepads gamepads;

    @Override
    public final void init()
    {
        gamepads = new Gamepads(gamepad1, gamepad2);
        setup();
    }

    public void setup() {}

    @Override
    public final void loop()
    {
        gamepads.update(gamepad1, gamepad2);
        run();
    }

    public void run() {}
}