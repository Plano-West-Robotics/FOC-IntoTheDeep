package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Servo
{
    public com.qualcomm.robotcore.hardware.Servo servo;

    public Servo(HardwareMap hardwareMap, String name)
    {
        this(hardwareMap, name, true);
    }

    public Servo(HardwareMap hardwareMap, String name, boolean isForward)
    {
        servo = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, name);
        if (isForward) setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.FORWARD);
        else setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
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

    public com.qualcomm.robotcore.hardware.Servo.Direction getDirection()
    {
        return servo.getDirection();
    }

    public void setDirection(com.qualcomm.robotcore.hardware.Servo.Direction direction)
    {
        servo.setDirection(direction);
    }
}
