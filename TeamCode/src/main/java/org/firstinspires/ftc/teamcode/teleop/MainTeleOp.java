package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;
import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;

public class MainTeleOp extends BaseTeleOp
{
    TeleDrive teleDrive;

    Subsystem[] subsystems;

    @Override
    public void setup()
    {
        teleDrive = new TeleDrive(hardware);

        subsystems = new Subsystem[] {teleDrive};
    }

    @Override
    public void run()
    {
        for (Subsystem subsystem : subsystems) subsystem.update(gamepads);
    }
}
