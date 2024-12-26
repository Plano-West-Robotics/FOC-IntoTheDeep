package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoWrapper
{
    public Servo servo;

    public ServoWrapper(HardwareMap hardwareMap, String name)
    {
        this(hardwareMap, name, 0.0, 1.0);
    }

    public ServoWrapper(HardwareMap hardwareMap, String name, double minPosition, double maxPosition)
    {
        servo = hardwareMap.get(Servo.class, name);
        servo.scaleRange(minPosition, maxPosition);
    }

    public void setPosition(double position)
    {
        servo.setPosition(position);
    }

    public double getPosition()
    {
        return servo.getPosition();
    }

    public void toMin()
    {
        setPosition(0);
    }

    public void toMax()
    {
        setPosition(1);
    }
}
