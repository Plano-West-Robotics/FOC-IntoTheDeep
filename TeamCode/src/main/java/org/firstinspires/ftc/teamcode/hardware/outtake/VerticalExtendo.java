package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class VerticalExtendo extends Extendo
{
    public VerticalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "vl", "vr",
                40, 4300,
                0.3, 0.8,
                0.0025,
                0.0015,
                0,
                0.0002,
                0,
                5,
                0
        );
        getLeft().reverse();
        getRight().forward();
    }
}
