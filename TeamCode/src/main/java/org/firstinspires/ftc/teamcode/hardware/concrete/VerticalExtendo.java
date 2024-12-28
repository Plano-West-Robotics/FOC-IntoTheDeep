package org.firstinspires.ftc.teamcode.hardware.concrete;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Extendo;

public class VerticalExtendo extends Extendo
{
    public VerticalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "vl", "vr",
                0.3, 0.8, 0.0025, 3800
        );
    }
}
