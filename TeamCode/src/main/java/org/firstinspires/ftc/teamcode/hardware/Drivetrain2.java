package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drivetrain2
{
    public Motor fr, fl, br, bl;
    public MecanumDrive drive;
    public GamepadEx controller1;
    public RevIMU imu;

    public Drivetrain2(HardwareMap hardwareMap, Gamepad gamepad1)
    {
        fr = new Motor(hardwareMap, "fr", Motor.GoBILDA.RPM_312);
        fl = new Motor(hardwareMap, "fl", Motor.GoBILDA.RPM_312);
        br = new Motor(hardwareMap, "br", Motor.GoBILDA.RPM_312);
        bl = new Motor(hardwareMap, "bl", Motor.GoBILDA.RPM_312);

        fl.setInverted(true);
        bl.setInverted(true);

        drive = new MecanumDrive(false, fl, fr, bl, br);
        imu = new RevIMU(hardwareMap);
        imu.init();

        controller1 = new GamepadEx(gamepad1);
    }

    /*
    public void setPower(double frPower, double flPower, double brPower, double blPower)
    {
        fr.setPower(frPower);
        fl.setPower(flPower);
        br.setPower(brPower);
        bl.setPower(blPower);
    }

     */
}
