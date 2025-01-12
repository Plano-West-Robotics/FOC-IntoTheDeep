package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class VerticalExtendo extends Extendo
{
    public static final double P = 0.0015;
    public static final double I = 0;
    public static final double D = 0.0002;
    public static final double F = 0;

    public VerticalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "vl", "vr",
                40, 4300,
                0.3, 0.8,
                0.0025,
                P, I, D, F,
                5,
                0
        );
        getLeft().reverse();
        getRight().forward();
    }
}
