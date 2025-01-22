package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.FieldCentricDrivetrain;

public class FieldCentricDrive
{
    public FieldCentricDrivetrain drivetrain;
    public MecanumDrive inner;
    public boolean slowMode;
    public double turnSpeed;

    public FieldCentricDrive(Hardware hardware)
    {
        drivetrain = hardware.fDrivetrain;
        inner = new MecanumDrive(
                false,
                drivetrain.fl,
                drivetrain.fr,
                drivetrain.bl,
                drivetrain.br
        );
        if (drivetrain == null) throw new RuntimeException("No field centric drivetrain given.");
        regularSpeed();
    }

    public void drive(double drive, double strafe, double turn)
    {
        if (strafe == 0 && drive == 0 && turn == 0) inner.stop();

        else
        {
            inner.driveFieldCentric(
                    strafe,
                    drive,
                    turn * turnSpeed,
                    drivetrain.getImu().getRotation2d().getDegrees(),
                    true
            );
        }
    }

    public void toggleSlowMode()
    {
        if (slowMode) regularSpeed();
        else slowSpeed();
    }

    public void regularSpeed()
    {
        inner.setMaxSpeed(0.85);
        turnSpeed = 0.7;
        slowMode = false;
    }

    public void slowSpeed()
    {
        inner.setMaxSpeed(0.2);
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
