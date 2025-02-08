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

    public static class Positions
    {
        public static final int HIGH_BUCKET = 4100;
        public static final int ABOVE_HIGH_CHAMBER = 2200;
        public static final int BELOW_HIGH_CHAMBER = 1200;
        public static final int SPECIMEN_CLEARANCE = 1000;
        public static final int BUCKET_CLEARANCE = 500;
        public static final int BOTTOM = 20; // "Safe" low position.
        public static final int FULLY_LOW = 10;
    }

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

    public void setHighBucket()
    {
        setPos(Positions.HIGH_BUCKET);
    }

    public boolean reachedHighBucket()
    {
        return isReached(Positions.HIGH_BUCKET);
    }

    public void setAboveHighChamber()
    {
        setPos(Positions.ABOVE_HIGH_CHAMBER);
    }

    public boolean reachedAboveHighChamber()
    {
        return isReached(Positions.ABOVE_HIGH_CHAMBER);
    }

    public void setBelowHighChamber()
    {
        setPos(Positions.BELOW_HIGH_CHAMBER);
    }

    public boolean reachedBelowHighChamber()
    {
        return isReached(Positions.BELOW_HIGH_CHAMBER);
    }

    public void setSpecimenClearance()
    {
        setPos(Positions.SPECIMEN_CLEARANCE);
    }

    public boolean reachedSpecimenClearance()
    {
        return isReached(Positions.SPECIMEN_CLEARANCE);
    }

    public void setBucketClearance()
    {
        setPos(Positions.BUCKET_CLEARANCE);
    }

    public boolean reachedBucketClearance()
    {
        return isReached(Positions.BUCKET_CLEARANCE);
    }

    public void setBottom()
    {
        setPos(Positions.BOTTOM);
    }

    public boolean reachedBottom()
    {
        return isReached(Positions.BOTTOM);
    }

    public void setFullyLow()
    {
        setPos(Positions.FULLY_LOW);
    }

    public boolean reachedFullyLow()
    {
        return isReached(Positions.FULLY_LOW);
    }

    public Action highBucket()
    {
        return getSlideAction(Positions.HIGH_BUCKET);
    }

    public Action aboveHighChamber()
    {
        return getSlideAction(Positions.ABOVE_HIGH_CHAMBER);
    }

    public Action belowHighChamber()
    {
        return getSlideAction(Positions.BELOW_HIGH_CHAMBER);
    }

    public Action lowerFully()
    {
        return getSlideAction(Positions.FULLY_LOW);
    }
}
