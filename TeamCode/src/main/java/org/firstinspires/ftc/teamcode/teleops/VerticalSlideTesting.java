package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSlides;

@TeleOp(name = "VerticalSlideTesting")
public class VerticalSlideTesting extends OpMode
{
    OuttakeSlides oSlides;
    Gamepads gamepads;

    @Override
    public void init()
    {
        oSlides = new OuttakeSlides(hardwareMap);
        gamepads = new Gamepads(gamepad1, gamepad2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void start()
    {
        oSlides.setHeight(OuttakeSlides.Height.REST);
    }

    @Override
    public void loop()
    {
        if (gamepads.justPressed(Button.GP2_DPAD_UP)) oSlides.incrementHeight();
        if (gamepads.justPressed(Button.GP2_DPAD_DOWN)) oSlides.decrementHeight();

        oSlides.updatePower();

        gamepads.update(gamepad1, gamepad2);

        telemetry.addData("Outtake Left Position", oSlides.motorL.getCurrentPosition());
        telemetry.addData("Outtake Right Position", oSlides.motorR.getCurrentPosition());
        telemetry.addData("Height State", oSlides.height);

        telemetry.update();
        gamepads.update(gamepad1, gamepad2);
    }
}
