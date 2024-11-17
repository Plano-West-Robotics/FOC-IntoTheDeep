package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeWheels
{
    public CRServo wheelL, wheelR;
    public boolean areIntaking, areStopped;

    public IntakeWheels(HardwareMap hardwareMap)
    {
        wheelL = hardwareMap.get(CRServo.class, "L2");
        wheelR = hardwareMap.get(CRServo.class, "R2");
        wheelL.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void intake()
    {
        wheelL.setPower(1);
        wheelR.setPower(1);
        areIntaking = true;
    }

    public void eject()
    {
        wheelL.setPower(-1);
        wheelR.setPower(-1);
        areIntaking = false;
    }

    public void stop()
    {
        wheelL.setPower(0);
        wheelR.setPower(0);
        areStopped = true;
    }
}
