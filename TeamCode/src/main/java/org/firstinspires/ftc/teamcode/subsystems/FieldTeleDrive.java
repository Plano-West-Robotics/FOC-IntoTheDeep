package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;

public class  FieldTeleDrive
{
    public Drivetrain drivetrain;
    public double frPower, flPower, brPower, blPower, speed, turnSpeed;
    public boolean slowMode;

    public FieldTeleDrive(Hardware hardware)
    {
        drivetrain = hardware.drivetrain;
        regularSpeed();
    }

    public void drive(double drive, double strafe, double turn)
    {
        if (drive == 0 && strafe == 0 && turn == 0) zeroPower();

        else
        {
            frPower = drive - strafe;
            flPower = drive + strafe;
            brPower = drive + strafe;
            blPower = drive - strafe;

            frPower -= turn * turnSpeed;
            brPower -= turn * turnSpeed;
            flPower += turn * turnSpeed;
            blPower += turn * turnSpeed;

            frPower *= speed;
            flPower *= speed;
            brPower *= speed;
            blPower *= speed;
        }

        drivetrain.setPower(frPower, flPower, brPower, blPower);
    }

    public void zeroPower()
    {
        frPower = flPower = brPower = blPower = 0;
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

    public boolean isSlowMode()
    {
        return slowMode;
    }

    public void update(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP1_RIGHT_BUMPER)) toggleSlowMode();

        double drive = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_Y);
        double strafe = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_X);
        double turn = gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_X);
        drive(drive, strafe, turn);
    }
}
