package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Gamepads;

@TeleOp(name = "ControllerTesting")
public class GamepadsTesting extends OpMode
{
    Gamepads gamepads;

    @Override
    public void init()
    {
        gamepads = new Gamepads(gamepad1, gamepad2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop()
    {
        telemetry.addData("GP1_LEFT_STICK_X", gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_X));
        telemetry.addData("GP1_LEFT_STICK_Y", gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_Y));
        telemetry.addData("GP1_RIGHT_STICK_X", gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_X));
        telemetry.addData("GP1_RIGHT_STICK_Y", gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_Y));

        telemetry.addData("GP2_LEFT_STICK_X", gamepads.getAnalogValue(Analog.GP2_LEFT_STICK_X));
        telemetry.addData("GP2_LEFT_STICK_Y", gamepads.getAnalogValue(Analog.GP2_LEFT_STICK_Y));
        telemetry.addData("GP2_RIGHT_STICK_X", gamepads.getAnalogValue(Analog.GP2_RIGHT_STICK_X));
        telemetry.addData("GP2_RIGHT_STICK_Y", gamepads.getAnalogValue(Analog.GP2_RIGHT_STICK_Y));

        telemetry.update();
        gamepads.update(gamepad1, gamepad2);
    }
}
