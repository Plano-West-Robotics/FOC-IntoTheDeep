package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.Servo;

public class FrontSwivel extends Servo
{
    public double positionIncrement;
    public boolean initialized;

    public FrontSwivel(HardwareMap hardwareMap)
    {
        super(hardwareMap, "fs");
        servo.scaleRange(0.123, 0.456);
        positionIncrement = 0.25;
        initialized = false;
    }

    public void center()
    {
        setPosition(0.5);
        initialized = true;
    }

    public void rotateCCW()
    {
        if (!initialized) center();
        if (getPosition() >= positionIncrement) setPosition(getPosition() - positionIncrement);
    }

    public void rotateCW()
    {
        if (!initialized) center();
        if (getPosition() <= 1 - positionIncrement) setPosition(getPosition() + positionIncrement);
    }
}
