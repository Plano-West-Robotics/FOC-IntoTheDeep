package org.firstinspires.ftc.teamcode;

public final class Utils
{
    private Utils() {}

    public static void validate(boolean condition, String message) throws RuntimeException
    {
        if (!condition) throw new RuntimeException(message);
    }
}
