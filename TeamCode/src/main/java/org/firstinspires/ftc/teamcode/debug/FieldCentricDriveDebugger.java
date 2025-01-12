package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.FieldCentricDrive;
import org.firstinspires.ftc.teamcode.teleop.BaseTeleOp;

@TeleOp(group = "Debug")
public class FieldCentricDriveDebugger extends BaseTeleOp
{
    public FieldCentricDrive drive;

    @Override
    public void setup()
    {
        drive = new FieldCentricDrive(hardware);
    }

    @Override
    public void run()
    {
        drive.update(gamepads);
    }
}
