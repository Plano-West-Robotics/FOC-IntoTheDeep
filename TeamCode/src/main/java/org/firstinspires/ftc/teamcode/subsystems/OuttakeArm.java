package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeArm
{
    public Servo servoArm;
    public double servoExtendPosition, servoRetractPosition;
    public boolean isExtended;

    public OuttakeArm(HardwareMap hardwareMap)
    {
        servoArm = hardwareMap.get(Servo.class, "oArm");
        servoExtendPosition = 0.06;
        servoRetractPosition = 0.85;
    }

    public void extend()
    {
        servoArm.setPosition(servoExtendPosition);
        isExtended = true;
    }

    public void retract()
    {
        servoArm.setPosition(servoRetractPosition);
        isExtended = false;
    }

    public void extendIfPossible()
    {
        if (!isExtended) extend();
    }

    public void retractIfPossible()
    {
        if (isExtended) retract();
    }

    public void switchStates()
    {
        if (isExtended) retract();
        else extend();
    }

}
