package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class RobotCentricDrive extends Drive
{
    public RobotCentricDrive(Hardware hardware)
    {
        super(hardware);
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
}
