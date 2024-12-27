package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class TeleDrive
{
    public Drivetrain drivetrain;
    public double speed, turnSpeed;
    public boolean slowMode;

    public TeleDrive(Hardware hardware)
    {
        drivetrain = hardware.drivetrain;
        regularSpeed();
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

        drivetrain.setPower(frPower, flPower, brPower, blPower);
    }

    public void stop()
    {
        drivetrain.setPower(0, 0, 0, 0);
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
