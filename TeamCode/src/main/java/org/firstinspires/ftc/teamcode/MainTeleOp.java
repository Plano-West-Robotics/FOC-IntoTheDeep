package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;
import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

public class MainTeleOp extends OpModeWrapper
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
