package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.Motor;

public class Drivetrain
{
    public Motor fr, fl, br, bl;
    public Motor[] motors;

    public Drivetrain(HardwareMap hardwareMap)
    {
        fr = new Motor(hardwareMap, "fr");
        fl = new Motor(hardwareMap, "fl");
        br = new Motor(hardwareMap, "br");
        bl = new Motor(hardwareMap, "bl");

        fl.reverse();
        bl.reverse();

        motors = new Motor[] {fr, fl, br, bl};

        for (Motor motor : motors) motor.noEncoder();
    }

    public void setPower(double frPower, double flPower, double brPower, double blPower)
    {
        fr.setPower(frPower);
        fl.setPower(flPower);
        br.setPower(brPower);
        bl.setPower(blPower);
    }
}
