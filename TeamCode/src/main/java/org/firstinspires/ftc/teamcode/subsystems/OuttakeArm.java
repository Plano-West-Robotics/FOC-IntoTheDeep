package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeArm
{
    public static final double RETRACT_POSITION = 0.82;
    public static final double EXTEND_POSITION = 0;

    public Servo servoArm;
    public Position position;

    public enum Position
    {
        UNINITIALIZED,
        RETRACT,
        EXTEND
    }

    public OuttakeArm(HardwareMap hardwareMap)
    {
        servoArm = hardwareMap.get(Servo.class, "oArm");
        position = Position.UNINITIALIZED;
    }

    public void retract()
    {
        setPosition(Position.RETRACT);
    }

    public void extend()
    {
        setPosition(Position.EXTEND);
    }

    public void togglePosition()
    {
        switch (position)
        {
            case RETRACT:
                setPosition(Position.EXTEND);
                break;
            case EXTEND:
                setPosition(Position.RETRACT);
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
            case RETRACT:
                servoArm.setPosition(RETRACT_POSITION);
                break;
            case EXTEND:
                servoArm.setPosition(EXTEND_POSITION);
                break;
        }
        position = newPosition;
    }
}
