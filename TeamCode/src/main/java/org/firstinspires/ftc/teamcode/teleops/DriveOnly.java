package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.RobotParameters;
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
        drive = new MecanumDrive(
                hw.motorFR,
                hw.motorFL,
                hw.motorBR,
                hw.motorBL,
                gamepad1,
                telemetry);

        telemetry.addLine("Init finished.");
        telemetry.update();
    }

    @Override
    public void loop()
    {
        drive.takeControllerInput();
        drive.updateMotorPowers();
        drive.powerMotors();
    }
}
