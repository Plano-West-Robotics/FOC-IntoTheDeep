package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeClaw
{
    public Servo servoClaw;
    public double servoClosePosition, servoOpenPosition;
    public boolean isOpen;

    public OuttakeClaw(HardwareMap hardwareMap)
    {
        servoClaw = hardwareMap.get(Servo.class, "oClaw");
        servoClosePosition = 0.55;
        servoOpenPosition = 0.44;
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

    public void switchStates()
    {
        if (isOpen) close();
        else open();
    }

}
