package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeArm
{
    public Servo servoL, servoR;
    public double servoLExtendPosition, servoRExtendPosition;
    public double servoLRetractPosition, servoRRetractPosition;
    public boolean isExtended;

    public IntakeArm(HardwareMap hardwareMap)
    {
        servoL = hardwareMap.get(Servo.class, "L1");
        servoR = hardwareMap.get(Servo.class, "R1");

        servoLExtendPosition = 0.08;
        servoRExtendPosition = 0.92;

        servoLRetractPosition = 0.96;
        servoRRetractPosition = 0.04;
    }

    public void extend()
    {
        servoL.setPosition(servoLExtendPosition);
        servoR.setPosition(servoRExtendPosition);
        isExtended = true;
    }

    public void retract()
    {
        servoL.setPosition(servoLRetractPosition);
        servoR.setPosition(servoRRetractPosition);
        isExtended = false;
    }

    public void switchPositions()
    {
        if (isExtended) retract();
        else extend();
    }

    public void extendIfPossible()
    {
        if (!isExtended) extend();
    }


    public void retractIfPossible()
    {
        if (isExtended) retract();
    }

}
