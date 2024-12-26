package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.MotorWrapper;

public class TeleDrive
{
    public MotorWrapper fr, fl, br, bl;
    public double speed, turnSpeed;
    public boolean slowMode;

    public TeleDrive(HardwareMap hardwareMap)
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

        regularSpeed();
    }

    public void stop()
    {
        drive(0, 0, 0);
    }

    public void drive(double drive, double strafe, double turn)
    {
        double frPower = drive - strafe;
        double flPower = drive + strafe;
        double brPower = drive + strafe;
        double blPower = drive - strafe;

        frPower -= turn * turnSpeed;
        brPower -= turn * turnSpeed;
        flPower += turn * turnSpeed;
        blPower += turn * turnSpeed;

        frPower *= speed;
        flPower *= speed;
        brPower *= speed;
        blPower *= speed;

        fr.setPower(frPower);
        fl.setPower(flPower);
        br.setPower(brPower);
        bl.setPower(blPower);
    }

    public void toggleSlowMode()
    {
        if (slowMode) regularSpeed();
        else slowSpeed();
    }

    public void regularSpeed()
    {
        speed = 0.5;
        turnSpeed = 0.7;
        slowMode = false;
    }

    public void slowSpeed()
    {
        speed = 0.1;
        turnSpeed = 0.4;
        slowMode = true;
    }
}
