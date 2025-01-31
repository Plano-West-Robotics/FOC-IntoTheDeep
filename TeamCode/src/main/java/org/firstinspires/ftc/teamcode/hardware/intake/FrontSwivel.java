package org.firstinspires.ftc.teamcode.hardware.intake;

import static org.firstinspires.ftc.teamcode.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.Servo;

public class FrontSwivel extends Servo
{
    public double positionIncrement;
    public boolean initialized;

    public FrontSwivel(HardwareMap hardwareMap)
    {
        super(hardwareMap, "fs");
        servo.scaleRange(0.18, 0.86);
        positionIncrement = 0.25;
        initialized = false;
    }

    public void center()
    {
        setPosition(0.5);
        initialized = true;
    }

    public void rotateCCW() // Rotate from right to left.
    {
        if (!initialized) center();
        if (getPosition() <= 1 - positionIncrement) setPosition(getPosition() + positionIncrement);
    }

    public void rotateCW() // Rotate from left to right.
    {
        if (!initialized) center();
        if (getPosition() >= positionIncrement) setPosition(getPosition() - positionIncrement);
    }

    public Action center(int timeInMilliseconds)
    {
        return getTimedAction(this::center, timeInMilliseconds);
    }
}
