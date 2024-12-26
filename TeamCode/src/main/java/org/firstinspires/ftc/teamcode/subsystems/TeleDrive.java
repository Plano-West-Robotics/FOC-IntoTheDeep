package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeleDrive
{
    public DcMotor fr, fl, br, bl;
    public double frPower, flPower, brPower, blPower;
    public boolean slowModeActivated;
    public double SPEED_MULTIPLIER, TURN_SPEED_MULTIPLIER;

    public TeleDrive(HardwareMap hardwareMap)
    {
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);

        slowModeActivated = false;
        SPEED_MULTIPLIER = 0.5;
        TURN_SPEED_MULTIPLIER = 0.7;
    }

    public void updateMotorPowers(double drive, double strafe, double turn)
    {
        frPower = drive - strafe;
        flPower = drive + strafe;
        brPower = drive + strafe;
        blPower = drive - strafe;

        frPower -= turn * TURN_SPEED_MULTIPLIER;
        brPower -= turn * TURN_SPEED_MULTIPLIER;
        flPower += turn * TURN_SPEED_MULTIPLIER;
        blPower += turn * TURN_SPEED_MULTIPLIER;

        frPower *= SPEED_MULTIPLIER;
        flPower *= SPEED_MULTIPLIER;
        brPower *= SPEED_MULTIPLIER;
        blPower *= SPEED_MULTIPLIER;

        fr.setPower(frPower);
        fl.setPower(flPower);
        br.setPower(brPower);
        bl.setPower(blPower);
    }

    public void toggleSlowMode()
    {
        if (slowModeActivated)
        {
            SPEED_MULTIPLIER = 0.5;
            TURN_SPEED_MULTIPLIER = 0.7;
        }
        else
        {
            SPEED_MULTIPLIER = 0.1;
            TURN_SPEED_MULTIPLIER = 0.4;
        }
        slowModeActivated = !slowModeActivated;
    }
}
