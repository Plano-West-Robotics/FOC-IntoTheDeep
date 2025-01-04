package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class HorizontalExtendo extends Extendo
{
    public HorizontalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "hl", "hr",
                30, 1100,
                0.2, 0.6,
                0.005,
                0,
                0,
                0,
                0,
                25,
                0
        );
        getLeft().forward();
        getRight().reverse();
    }
}
