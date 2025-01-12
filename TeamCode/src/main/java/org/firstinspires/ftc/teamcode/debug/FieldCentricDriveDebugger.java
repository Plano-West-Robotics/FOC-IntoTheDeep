package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.FieldTeleDrive;
import org.firstinspires.ftc.teamcode.teleop.BaseTeleOp;

@TeleOp(group = "Debug")
public class FieldCentricDriveDebugger extends OpMode
{
    public FieldTeleDrive drive;

    @Override
    public void init()
    {
        drive = new FieldTeleDrive(hardwareMap, gamepad1);
    }

    @Override
    public void loop()
    {
        drive.drive();
    }

}
