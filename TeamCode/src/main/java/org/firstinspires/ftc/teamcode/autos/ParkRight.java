package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="ParkRight")
public class ParkRight extends LinearOpMode
{
    public Servo iArmL, iArmR;
    public double iArmLExtendPosition, iArmRExtendPosition, iArmLRetractPosition, iArmRRetractPosition;

    public DcMotor fr, fl, br, bl;
    public double MECANUM_DRIVE_SPEED, MECANUM_DRIVE_TURN_SPEED;

    public Servo oClaw;
    public double oClawClosePosition, oClawOpenPosition;

    @Override
    public void runOpMode()
    {
        iArmL = hardwareMap.get(Servo.class, "L1");
        iArmR = hardwareMap.get(Servo.class, "R1");
        iArmLExtendPosition = 0.08;
        iArmRExtendPosition = 0.92;
        iArmLRetractPosition = 0.96;
        iArmRRetractPosition = 0.04;

        // Initialize the drive system variables.
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

        MECANUM_DRIVE_SPEED = 0.5;
        MECANUM_DRIVE_TURN_SPEED = 0.7;

        oClaw = hardwareMap.get(Servo.class, "oClaw");
        oClawClosePosition = 0.16;
        oClawOpenPosition = 0.035;

        waitForStart();

        moveForward();
        strafeRight();
        retractIntakeArm();
        closeOuttakeClaw();
    }

    public void moveForward()
    {
        fr.setPower(0.1);
        fl.setPower(0.1);
        br.setPower(0.1);
        bl.setPower(0.1);
        sleep(700);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public void strafeRight()
    {
        fr.setPower(-0.5);
        fl.setPower(0.5);
        br.setPower(0.5);
        bl.setPower(-0.5);
        sleep(1200);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public void retractIntakeArm()
    {
        iArmL.setPosition(iArmLRetractPosition);
        iArmR.setPosition(iArmRRetractPosition);
    }

    public void closeOuttakeClaw()
    {
        oClaw.setPosition(oClawClosePosition);
    }
}
