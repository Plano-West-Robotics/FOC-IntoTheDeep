package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

public abstract class Drive
{
    public Drivetrain drivetrain;

    public double speed, turnSpeed;
    public boolean slowMode;

    public static class SpeedParams
    {
        public static double regularSpeed = 0.85;
        public static double regularTurnSpeed = 0.7;

        public static double slowSpeed = 0.2;
        public static double slowTurnSpeed = 0.4;
    }

    public Drive(Hardware hardware)
    {
        drivetrain = hardware.drivetrain;
        regularSpeed();
    }

    public abstract void drive(double drive, double strafe, double turn);

    public void toggleSlowMode()
    {
        if (slowMode) regularSpeed();
        else slowSpeed();
    }

    public void regularSpeed()
    {
        speed = SpeedParams.regularSpeed;
        turnSpeed = SpeedParams.regularTurnSpeed;
        slowMode = false;
    }

    public void slowSpeed()
    {
        speed = SpeedParams.slowSpeed;
        turnSpeed = SpeedParams.slowTurnSpeed;
        slowMode = true;
    }

    public void setSpeedParams(double regularSpeed, double regularTurnSpeed, double slowSpeed,
                               double slowTurnSpeed)
    {
        SpeedParams.regularSpeed = regularSpeed;
        SpeedParams.regularTurnSpeed = regularTurnSpeed;
        SpeedParams.slowSpeed = slowSpeed;
        SpeedParams.slowTurnSpeed = slowTurnSpeed;
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
