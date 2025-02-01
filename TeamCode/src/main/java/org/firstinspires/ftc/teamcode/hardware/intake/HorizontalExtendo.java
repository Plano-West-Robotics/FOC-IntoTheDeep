package org.firstinspires.ftc.teamcode.hardware.intake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class HorizontalExtendo extends Extendo
{
    public static final double P = 0.0001;
    public static final double I = 0;
    public static final double D = 0.00002;
    public static final double F = 0;

    // TeleOp positions.
    public static final int RETRACT_THRESHOLD = 350;
    public static final int EXTEND_THRESHOLD = 430; // Must be greater than RETRACT_THRESHOLD.

    // Auto positions.
    public static final int RETRACT_POSITION = 30;
    public static final int EXTEND_POSITION = 800;

    public HorizontalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "hl", "hr",
                30, 1000,
                0.1, 0.5,
                0.006,
                P, I, D, F,
                    15,
                0
        );
        getLeft().forward();
        getRight().reverse();
    }

    public boolean inRetractZone()
    {
        return getAveragePosition() < RETRACT_THRESHOLD;
    }

    public boolean inExtendZone()
    {
        return getAveragePosition() > EXTEND_THRESHOLD;
    }

    public Action retract()
    {
        return getSlideAction(RETRACT_POSITION);
    }

    public Action extend()
    {
        return getSlideAction(EXTEND_POSITION);
    }
}
