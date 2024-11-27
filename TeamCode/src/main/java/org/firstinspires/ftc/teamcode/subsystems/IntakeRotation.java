package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeRotation
{
    public Servo servoClaw;
    public double servoClosePosition, servoOpenPosition;
    public boolean isOpen;
    public double curPos;
    public static final double INCREMENT_AMNT = 0.05;

    public IntakeRotation(HardwareMap hardwareMap)
    {
        servoClaw = hardwareMap.get(Servo.class, "iRot");
        servoClosePosition = 0.2; // need to adjust
        servoOpenPosition = 0.8; // need to adjust
        servoClaw.scaleRange(servoClosePosition, servoOpenPosition);
    }

    public void rotCCW()
    {
        curPos = curPos + (INCREMENT_AMNT * -1);
        servoClaw.setPosition(curPos);
        isOpen = false;
    }

    public void rotCW()
    {
        curPos = curPos + INCREMENT_AMNT;
        servoClaw.setPosition(curPos);
        isOpen = true;
    }

}
