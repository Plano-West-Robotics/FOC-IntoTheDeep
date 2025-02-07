package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class VerticalExtendo extends Extendo
{
    public static final double P = 0.009;
    public static final double I = 0;
    public static final double D = 0.00015;
    public static final double F = 0;

    // Auto VerticalExtendo parameters.
    public static final int LB_POSITION = 123; // Low basket.
    public static final int HB_POSITION = 3500; // High basket.
    public static final int LC_POSITION = 345; // Low chamber.
    public static final int HC_POSITION = 2100; // High chamber.
    public static final int HC2_POSITION = 1200; // High chamber.
    public static final int HC3_POSITION = 2200; // High chamber.
    public static final int LR_POSITION = 567; // Low rung.
    public static final int HR_POSITION = 678; // High rung.
    public static final int WG_POSITION = 10; // Wall grab.
    public static final int CLEARANCE_POSITION = 500;
    public static final int SAFE_LOW = 20;

    public VerticalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "vl", "vr",
                10, 4300,
                0.1, 0.95,
                0.0025,
                P, I, D, F,
                50,
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

    public Action hc2()
    {
        return getSlideAction(HC2_POSITION);
    }

    public Action hc3()
    {
        return getSlideAction(HC3_POSITION);
    }

    public Action lowerFully()
    {
        return getSlideAction(WG_POSITION);
    }

    public void setHighBucket()
    {
        setPos(HB_POSITION);
    }

    public boolean reachedHighBucket()
    {
        return isReached(HB_POSITION);
    }

    public void createBucketClearance()
    {
        setPos(CLEARANCE_POSITION);
    }

    public boolean clearanceMade()
    {
        return isReached(CLEARANCE_POSITION);
    }

    public void setBottom()
    {
        setPos(SAFE_LOW);
    }

    public boolean reachedBottom()
    {
        return isReached(SAFE_LOW);
    }

    public void setHC2()
    {
        setPos(HC2_POSITION);
    }

    public boolean reachedHC2()
    {
        return isReached(HC2_POSITION);
    }

    public void setHC3()
    {
        setPos(HC3_POSITION);
    }

    public boolean reachedHC3()
    {
        return isReached(HC3_POSITION);
    }

    public void brakeSlides()
    {
        setPower(0);
    }
}
