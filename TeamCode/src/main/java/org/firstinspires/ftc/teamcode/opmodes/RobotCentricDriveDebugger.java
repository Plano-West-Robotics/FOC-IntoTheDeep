package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;

@TeleOp(name = "RobotCentricDriveDebugger", group = "Debug")
public class RobotCentricDriveDebugger extends BaseTeleOp
{
    public TeleDrive drive;

    @Override
    public void setup()
    {
        drive = new TeleDrive(hardware);
    }

    @Override
    public void run()
    {
        drive.update(gamepads);
    }
}
