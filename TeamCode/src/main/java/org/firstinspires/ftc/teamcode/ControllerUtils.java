package org.firstinspires.ftc.teamcode;

public final class ControllerUtils
{
    public static boolean justPressed(boolean curr, boolean last)
    {
        return curr && !last;
    }

    private ControllerUtils() {}
}
