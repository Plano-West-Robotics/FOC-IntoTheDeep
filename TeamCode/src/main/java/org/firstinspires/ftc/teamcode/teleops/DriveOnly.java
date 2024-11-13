package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "DriveOnly")
public class DriveOnly extends OpMode
{
    Hardware hw;
    MecanumDrive drive;

    @Override
    public void init()
    {
        hw = new Hardware(hardwareMap);
        drive = new MecanumDrive(hw, telemetry);

        telemetry.addLine("Init finished.");
        telemetry.update();
    }

    @Override
    public void loop()
    {
        drive.takeControllerInput(gamepad1);
        drive.updateMotorPowers();
    }
}