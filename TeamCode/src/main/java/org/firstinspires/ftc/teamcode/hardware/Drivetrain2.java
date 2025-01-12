package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.Motor;

public class Drivetrain2
{
    public Motor fr, fl, br, bl;

    public Drivetrain2(HardwareMap hardwareMap)
    {
        fr = new Motor(hardwareMap, "fr");
        fl = new Motor(hardwareMap, "fl");
        br = new Motor(hardwareMap, "br");
        bl = new Motor(hardwareMap, "bl");

        fl.reverse();
        bl.reverse();

        fr.noEncoder();
        fl.noEncoder();
        br.noEncoder();
        bl.noEncoder();
    }

    public void setPower(double frPower, double flPower, double brPower, double blPower)
    {
        fr.setPower(frPower);
        fl.setPower(flPower);
        br.setPower(brPower);
        bl.setPower(blPower);
    }
}
