package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class HorizontalExtendo extends Extendo
{
    public static final double P = 0.0001;
    public static final double I = 0;
    public static final double D = 0.00002;
    public static final double F = 0;

    public HorizontalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "hl", "hr",
                30, 1000,
                0.2, 0.6,
                0.005,
                P, I, D, F,
                15,
                0
        );
        getLeft().forward();
        getRight().reverse();
    }
}
