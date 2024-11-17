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
        servoClosePosition = 0.16;
        servoOpenPosition = 0.035;
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
}
