package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class FieldCentricDrive extends Drive
{
    public IMU imu;

    public FieldCentricDrive(Hardware hardware)
    {
        super(hardware);
        imu = hardware.imu;
    }

    public void drive(double y, double x, double rx)
    {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotY = (x * Math.sin(-botHeading) + y * Math.cos(-botHeading)) * speed;
        double rotX = (x * Math.cos(-botHeading) - y * Math.sin(-botHeading)) * speed;
        rx *= turnSpeed;

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frPower = (rotY - rotX - rx) / denominator;
        double flPower = (rotY + rotX + rx) / denominator;
        double brPower = (rotY + rotX - rx) / denominator;
        double blPower = (rotY - rotX + rx) / denominator;

        drivetrain.setPower(frPower, flPower, brPower, blPower);
    }

    @Override
    public void update(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP1_DPAD_RIGHT)) imu.resetYaw();

        double drive = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_Y);
        double strafe = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_X);
        double turn = gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_X);
        drive(strafe, drive, turn);
    }
}
