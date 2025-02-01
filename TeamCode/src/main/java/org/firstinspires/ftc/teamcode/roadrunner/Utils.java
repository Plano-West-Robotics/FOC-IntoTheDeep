package org.firstinspires.ftc.teamcode.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

public final class Utils
{
    private Utils() {}

    public static void validate(boolean condition, String message) throws RuntimeException
    {
        if (!condition) throw new RuntimeException(message);
    }

    public static double iR(double degrees)
    {
        return Math.toRadians(degrees);
    }

    public static Action getTimedAction(Runnable action, int timeInMilliseconds)
    {
        return new Action()
        {
            public boolean initialized = false;
            public final ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {
                if (!initialized)
                {
                    action.run();
                    timer.reset();
                    initialized = true;
                }
                return timer.time() < timeInMilliseconds;
            }
        };
    }
}
