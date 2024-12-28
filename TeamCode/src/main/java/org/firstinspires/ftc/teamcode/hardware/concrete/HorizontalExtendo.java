package org.firstinspires.ftc.teamcode.hardware.concrete;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Extendo;

public class HorizontalExtendo extends Extendo
{
    public HorizontalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "hl", "hr",
                0.2, 0.6, 0.005, 1100
        );
    }
}
