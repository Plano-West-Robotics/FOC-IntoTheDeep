package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.MotorWrapper;

public class Drivetrain
{
    public MotorWrapper fr, fl, br, bl;

    public Drivetrain(HardwareMap hardwareMap)
    {
        fr = new MotorWrapper(hardwareMap, "fr");
        fl = new MotorWrapper(hardwareMap, "fl");
        br = new MotorWrapper(hardwareMap, "br");
        bl = new MotorWrapper(hardwareMap, "bl");

        fl.reverse();
        bl.reverse();

        fr.resetEncoder(true);
        fl.resetEncoder(true);
        br.resetEncoder(true);
        bl.resetEncoder(true);

        fr.stop();
        fl.stop();
        br.stop();
        bl.stop();
    }

    public void setPower(double frPower, double flPower, double brPower, double blPower)
    {
        fr.setPower(frPower);
        fl.setPower(flPower);
        br.setPower(brPower);
        bl.setPower(blPower);
    }
}
