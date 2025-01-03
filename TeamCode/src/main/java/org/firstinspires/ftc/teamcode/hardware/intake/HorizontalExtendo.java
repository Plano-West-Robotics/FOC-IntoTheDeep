package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class HorizontalExtendo extends Extendo
{
    public HorizontalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "hl", "hr", 30, 0.2,
                0.6, 0.005, 1100, 0, 0, 0, 0
        );
        getLeftMotor().forward();
        getRightMotor().reverse();
    }
}
