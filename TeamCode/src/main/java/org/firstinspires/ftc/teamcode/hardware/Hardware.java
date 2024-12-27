package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware
{
    public HardwareMap hardwareMap;

    public Drivetrain drivetrain;

    public Hardware(OpMode opMode)
    {
        hardwareMap = opMode.hardwareMap;

        drivetrain = new Drivetrain(hardwareMap);
    }
}
