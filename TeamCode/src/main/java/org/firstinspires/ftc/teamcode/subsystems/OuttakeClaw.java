package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeClaw
{
    public static final double OPEN_POSITION = 0.4;
    public static final double CLOSE_POSITION = 0.55;
    public static final double TIME_TO_TOGGLE_CLAW_MS = 200;

    public Servo servoClaw;
    public Position position;

    public enum Position
    {
        UNINITIALIZED,
        OPEN,
        CLOSE
    }

    public OuttakeClaw(HardwareMap hardwareMap)
    {
        servoClaw = hardwareMap.get(Servo.class, "oClaw");
        position = Position.UNINITIALIZED;
    }

    public void open()
    {
        setPosition(Position.OPEN);
    }

    public void close()
    {
        setPosition(Position.CLOSE);
    }

    public void togglePosition()
    {
        switch (position)
        {
            case OPEN:
                setPosition(Position.CLOSE);
                break;
            case CLOSE:
                setPosition(Position.OPEN);
                break;
        }
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position newPosition)
    {
        // Preemptive return statement avoids unnecessary servo setPosition() calls
        if (newPosition == position || newPosition == Position.UNINITIALIZED) return;

        switch (newPosition)
        {
            case OPEN:
                servoClaw.setPosition(OPEN_POSITION);
                break;
            case CLOSE:
                servoClaw.setPosition(CLOSE_POSITION);
                break;
        }
        position = newPosition;
    }
}
