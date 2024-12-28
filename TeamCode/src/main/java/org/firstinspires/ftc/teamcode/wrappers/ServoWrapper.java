package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoWrapper
{
    public Servo servo;

    public ServoWrapper(HardwareMap hardwareMap, String name)
    {
        this(hardwareMap, name, true);
    }

    public ServoWrapper(HardwareMap hardwareMap, String name, boolean isForward)
    {
        servo = hardwareMap.get(Servo.class, name);
        if (isForward) setDirection(Servo.Direction.FORWARD);
        else setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Remember that servos do not have encoders!
     * @return  the argument of the last {@code setPosition()} call
     */
    public double getPosition()
    {
        return servo.getPosition();
    }

    public void setPosition(double position)
    {
        servo.setPosition(position);
    }

    public Servo.Direction getDirection()
    {
        return servo.getDirection();
    }

    public void setDirection(Servo.Direction direction)
    {
        servo.setDirection(direction);
    }
}
