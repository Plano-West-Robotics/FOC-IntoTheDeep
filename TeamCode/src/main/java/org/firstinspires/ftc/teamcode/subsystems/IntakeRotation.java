package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeRotation
{
    public Servo servoClaw;
    public double servoMinPosition, servoMaxPosition;
    public double curPos;
    public static final double INCREMENT_AMNT = 0.25;

    public IntakeRotation(HardwareMap hardwareMap)
    {
        servoClaw = hardwareMap.get(Servo.class, "R2");
        servoMinPosition = 0.17; // need to adjust
        servoMaxPosition = 0.83; // need to adjust
        servoClaw.scaleRange(servoMinPosition, servoMaxPosition);
        servoClaw.setPosition(0.5);
        curPos = 0.5;
    }

    public void rotCCW()
    {
        curPos = Math.min(curPos + INCREMENT_AMNT, 1);
        servoClaw.setPosition(curPos);
    }

    public void rotCW()
    {
        curPos = Math.max(curPos - INCREMENT_AMNT, 0);
        servoClaw.setPosition(curPos);
    }

}
