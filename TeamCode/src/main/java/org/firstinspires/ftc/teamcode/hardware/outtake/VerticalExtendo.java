package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class VerticalExtendo extends Extendo
{
    public static final double P = 0.0015;
    public static final double I = 0;
    public static final double D = 0.0002;
    public static final double F = 0;

    // Auto VerticalExtendo parameters.
    public static final int LB_POSITION = 123; // Low basket.
    public static final int HB_POSITION = 234; // High basket.
    public static final int LC_POSITION = 345; // Low chamber.
    public static final int HC_POSITION = 1800; // High chamber.
    public static final int LR_POSITION = 567; // Low rung.
    public static final int HR_POSITION = 678; // High rung.
    public static final int WG_POSITION = 0; // Wall grab.

    public VerticalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "vl", "vr",
                40, 4300,
                0.1, 0.8,
                0.0025,
                P, I, D, F,
                5,
                0.0005
        );
        getLeft().reverse();
        getRight().forward();
    }

    public Action lowBasket()
    {
        return getSlideAction(LB_POSITION);
    }

    public Action highBasket()
    {
        return getSlideAction(HB_POSITION);
    }

    public Action lowChamber()
    {
        return getSlideAction(LC_POSITION);
    }

    public Action highChamber()
    {
        return getSlideAction(HC_POSITION);
    }

    public Action lowRung()
    {
        return getSlideAction(LR_POSITION);
    }

    public Action highRung()
    {
        return getSlideAction(HR_POSITION);
    }

    public Action wallGrab()
    {
        return getSlideAction(WG_POSITION);
    }
}
