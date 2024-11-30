package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeClaw
{
    public Servo servoClaw;
    public double servoClosePosition, servoOpenPosition;
    public boolean isOpen;

    public IntakeClaw(HardwareMap hardwareMap)
    {
        servoClaw = hardwareMap.get(Servo.class, "L2");
        servoClosePosition = 0.66; // need to adjust
        servoOpenPosition = 0.57; // need to adjust
    }

    public void close()
    {
        servoClaw.setPosition(servoClosePosition);
        isOpen = false;
    }

    public void open()
    {
        servoClaw.setPosition(servoOpenPosition);
        isOpen = true;
    }

    public void closeIfPossible()
    {
        if (isOpen) close();
    }

    public void openIfPossible()
    {
        if (!isOpen) open();
    }

    public void switchStates()
    {
        if (isOpen) close();
        else open();
    }

}
