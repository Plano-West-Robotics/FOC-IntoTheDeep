package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.RobotParameters;

public class Intake
{
    public final Servo servoL, servoR;
    public final CRServo wheelL, wheelR;
    public final Gamepad gamepad;
    public IntakeState intakeState;
    public boolean a, b;
    public boolean a_last, b_last;

    public enum IntakeState
    {
        RETRACTED_SLIDE,
        EXTENDED_SLIDE,
        RETRACT,
        EXTEND,
        RETREAT,
        DUMP
    }

    public Intake(Hardware hw, Gamepad gamepad)
    {
        this.servoL = hw.intakeServoL;
        this.servoR = hw.intakeServoR;
        this.wheelL = hw.intakeWheelL;
        this.wheelR = hw.intakeWheelR;
        this.gamepad = gamepad;

        a = b = a_last = b_last = false;
    }

    // TODO: Program servo limits before calling.
    public void retract()
    {
        servoL.setPosition(0);
        servoR.setPosition(0);
    }

    // TODO: Program servo limits before calling.
    public void extend()
    {
        servoL.setPosition(1);
        servoR.setPosition(1);
    }

    public void startIntake()
    {
        wheelL.setPower(RobotParameters.INTAKE_WHEEL_SPEED);
        wheelR.setPower(RobotParameters.INTAKE_WHEEL_SPEED);
    }

    public void startDump()
    {
        wheelL.setPower(-RobotParameters.INTAKE_WHEEL_SPEED);
        wheelR.setPower(-RobotParameters.INTAKE_WHEEL_SPEED);
    }

    public void haltWheels()
    {
        wheelL.setPower(0);
        wheelR.setPower(0);
    }
}