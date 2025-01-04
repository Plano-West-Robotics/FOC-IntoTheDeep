package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class VerticalExtendo extends Extendo
{
    public VerticalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "vl", "vr",
                45, 3800,
                0.3, 0.8,
                0.0025,
                0,
                0,
                0,
                0,
                45,
                0.0005
        );
        getLeft().reverse();
        getRight().forward();
    }
}
