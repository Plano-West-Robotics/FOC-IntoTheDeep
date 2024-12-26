package org.firstinspires.ftc.teamcode.helpers;

import com.qualcomm.robotcore.hardware.DcMotor;

public final class SlideUtils
{
    public static int getAveragePosition(DcMotor a, DcMotor b)
    {
        return (int) Math.round((a.getCurrentPosition() + b.getCurrentPosition()) / 2.0);
    }

    public static boolean isExtended(DcMotor a, DcMotor b, int maxPosition, int buffer)
    {
        return getAveragePosition(a, b) >= maxPosition - buffer;
    }

    public static boolean isRetracted(DcMotor a, DcMotor b, int buffer)
    {
        return getAveragePosition(a, b) <= buffer;
    }

    private SlideUtils() {}
}
