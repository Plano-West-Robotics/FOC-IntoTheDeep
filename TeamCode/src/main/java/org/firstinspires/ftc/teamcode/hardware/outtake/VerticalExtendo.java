package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.Extendo;

public class VerticalExtendo extends Extendo
{
    public VerticalExtendo(HardwareMap hardwareMap)
    {
        super(
                hardwareMap, "vl", "vr", 45,
                0.3, 0.8, 0.0025, 3800
        );
        getLeftMotor().reverse();
        getRightMotor().forward();
    }
}
