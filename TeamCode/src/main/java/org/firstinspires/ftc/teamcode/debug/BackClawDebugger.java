package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.wrappers.Servo;

@TeleOp(group = "Debug")
public class BackClawDebugger extends OpMode
{
    public Servo claw;
    public Gamepads gamepads;
    public boolean isOpen;

    @Override
    public void init()
    {
        claw = new Servo(hardwareMap, "bc");
        gamepads = new Gamepads(gamepad1, gamepad2);
        isOpen = true;
    }

    @Override
    public void loop()
    {
        if (gamepads.justPressed(Button.GP2_X))
        {
            if (isOpen)
            {
                claw.setPosition(0.08);
                isOpen = false;
            }
            else
            {
                claw.setPosition(0.35);
                isOpen = true;
            }
        }

        telemetry.update();
        gamepads.update(gamepad1, gamepad2);
    }
}
