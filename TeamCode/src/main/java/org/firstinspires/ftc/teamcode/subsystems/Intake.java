package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotParameters;

public class Intake
{
    public final Servo left, right;
    public final CRServo leftWheel, rightWheel;
    public final Gamepad gamepad;
    public boolean a, x;
    public boolean a_last, x_last;
    public boolean isExtended, isIntaking;

    public Intake(Servo left,
                  Servo right,
                  CRServo leftWheel,
                  CRServo rightWheel,
                  Gamepad gamepad)
    {
        this.left = left;
        this.right = right;
        this.leftWheel = leftWheel;
        this.rightWheel = rightWheel;
        this.gamepad = gamepad;

        a = x = a_last = x_last = false;
        isExtended = isIntaking = true;

        extend();
        startIntake();
    }

    public void takeControllerInput()
    {
        a = gamepad.a;
        x = gamepad.x;

        if ((a_last != a) && a)
        {
            if (isExtended) retract();
            else extend();
            isExtended = !isExtended;
        }
        if ((x_last != x) && x)
        {
            if (isIntaking) startEject();
            else startIntake();
            isIntaking = !isIntaking;
        }

        a_last = gamepad.a;
        x_last = gamepad.x;
    }

    // TODO: Program servo limits before calling.
    public void retract()
    {
        left.setPosition(0);
        right.setPosition(0);
    }

    // TODO: Program servo limits before calling.
    public void extend()
    {
        left.setPosition(1);
        right.setPosition(1);
    }

    public void startIntake()
    {
        leftWheel.setPower(RobotParameters.INTAKE_WHEEL_SPEED);
        rightWheel.setPower(RobotParameters.INTAKE_WHEEL_SPEED);
    }

    public void startEject()
    {
        leftWheel.setPower(-RobotParameters.INTAKE_WHEEL_SPEED);
        rightWheel.setPower(-RobotParameters.INTAKE_WHEEL_SPEED);
    }

    public void haltWheels()
    {
        leftWheel.setPower(0);
        rightWheel.setPower(0);
    }
}