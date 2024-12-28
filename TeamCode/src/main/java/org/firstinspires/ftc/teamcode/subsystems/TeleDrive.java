package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class TeleDrive implements Subsystem
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
        if (drive == 0 && strafe == 0 && turn == 0)
        {
            halt();
            return;
        }

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

    public void halt()
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

    @Override
    public void update(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP1_RIGHT_BUMPER)) toggleSlowMode();

        drive(
                gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_Y),
                gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_X),
                gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_X)
        );
    }
}
