package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeArm
{
    public Servo servoL, servoR;
    public double servoLExtendPosition, servoRExtendPosition;
    public double servoLStandbyPosition, servoRStandbyPosition;
    public double servoLRetractPosition, servoRRetractPosition;
    public boolean isExtended;
    public boolean isStandby;

    public IntakeArm(HardwareMap hardwareMap)
    {
        servoL = hardwareMap.get(Servo.class, "L1");
        servoR = hardwareMap.get(Servo.class, "R1");

        servoLExtendPosition = 0.06;
        servoRExtendPosition = 0.94;

        servoLStandbyPosition = 0.12;
        servoRStandbyPosition = 0.88;

        servoLRetractPosition = 0.87;
        servoRRetractPosition = 0.13;
    }

    public void extend()
    {
        servoL.setPosition(servoLExtendPosition);
        servoR.setPosition(servoRExtendPosition);
        isExtended = true;
        isStandby = false;
    }

    public void standby()
    {
        servoL.setPosition(servoLStandbyPosition);
        servoR.setPosition(servoRStandbyPosition);
        isExtended = false;
        isStandby = true;
    }

    public void retract()
    {
        servoL.setPosition(servoLRetractPosition);
        servoR.setPosition(servoRRetractPosition);
        isExtended = false;
        isStandby = false;
    }

    public void extendIfPossible()
    {
        if (!isExtended) extend();
    }

    public void retractIfPossible()
    {
        if (isExtended) retract();
    }

    public void switchPositions()
    {
        if (isExtended) retract();
        else extend();
    }
}
