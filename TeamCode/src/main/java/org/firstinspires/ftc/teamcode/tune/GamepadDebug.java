package org.firstinspires.ftc.teamcode.tune;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;

@TeleOp(name = "Controller", group = "Debug")
public class GamepadDebug extends OpMode
{
    public Gamepads gamepads;

    @Override
    public void init()
    {
        gamepads = new Gamepads(gamepad1, gamepad2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop()
    {
        for (Analog analogValue : Analog.values())
        {
            telemetry.addData(analogValue.name(), gamepads.getAnalogValue(analogValue));
        }
        for (Button buttonValue: Button.values())
        {
            telemetry.addData(buttonValue.name(), gamepads.isPressed(buttonValue));
        }
        telemetry.update();
    }
}
