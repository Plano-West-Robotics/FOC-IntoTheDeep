package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.message.redux.ReceiveGamepadState;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain2;
import com.arcrobotics.ftclib.hardware.RevIMU;

public class FieldTeleDrive
{
    public Drivetrain2 drivetrain2;
    public GamepadEx gamepadOne;

    public FieldTeleDrive(HardwareMap hardware, Gamepad gp1)
    {
        drivetrain2 = new Drivetrain2(hardware, gp1);
        gamepadOne = drivetrain2.controller1;
    }

    public void drive()
    {
        drivetrain2.drive.driveFieldCentric(gamepadOne.getLeftX(), gamepadOne.getLeftY(),
                gamepadOne.getRightX(), drivetrain2.imu.getRotation2d().getDegrees(), true);
    }


}
