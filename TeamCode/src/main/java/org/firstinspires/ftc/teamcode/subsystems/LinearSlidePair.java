package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotParameters;

public class LinearSlidePair
{
    public final DcMotor motorL, motorR;
    public final Gamepad gamepad;
    public final Telemetry telemetry;
    public final double SPEED; // Ticks per second;
    public final double MAX_TICKS;
    public double stickInput; // Y-value of the left joystick.
    public boolean a, x, b, y; // Buttons for ascending/descending to the low chamber,
                                // high chamber, low basket, and high basket, respectively
    public boolean a_last, x_last, b_last, y_last;

    public LinearSlidePair(DcMotor motorL,
                           DcMotor motorR,
                           Gamepad gamepad,
                           Telemetry telemetry,
                           double speed,
                           double maxTicks)
    {
        this.motorL = motorL;
        this.motorR = motorR;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
        this.SPEED = speed;
        this.MAX_TICKS = maxTicks;

        stickInput = 0;
        a = x = b = y = a_last = x_last = b_last = y_last = false;
    }

    // Should only be used for intake slide.
    public void updateVelocity()
    {
        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        stickInput = -1 * gamepad.left_stick_y;
        if (motorL.getCurrentPosition() < MAX_TICKS && motorR.getCurrentPosition() < MAX_TICKS)
        {
            if (motorL.getCurrentPosition() >= 0 && motorR.getCurrentPosition() >= 0)
            {
                motorL.setPower(SPEED * stickInput);
                motorR.setPower(SPEED * stickInput);
            }
        }
        else
        {
            motorL.setPower(0);
            motorR.setPower(0);
        }
    }

    // Should only be used for outtake slide.
    // TODO: Not usable until scoring heights are determined (check RobotParameters).
    public void useButtonAction()
    {
        a = gamepad.a;
        x = gamepad.x;
        b = gamepad.b;
        y = gamepad.y;

        if ((a_last != a) && a && !(motorL.isBusy() || motorR.isBusy())) goTo(RobotParameters.LOW_CHAMBER_HEIGHT);
        else if ((x_last != x) && x && !(motorL.isBusy() || motorR.isBusy())) goTo(RobotParameters.HIGH_CHAMBER_HEIGHT);
        else if ((b_last != b) && b && !(motorL.isBusy() || motorR.isBusy())) goTo(RobotParameters.LOW_BASKET_HEIGHT);
        else if ((y_last != y) && y && !(motorL.isBusy() || motorR.isBusy())) goTo(RobotParameters.HIGH_BASKET_HEIGHT);

        a_last = gamepad.a;
        x_last = gamepad.x;
        b_last = gamepad.b;
        y_last = gamepad.y;
    }

    // Should only be used for outtake slide.
    private void goTo(int ticks)
    {
        motorL.setTargetPosition(ticks);
        motorR.setTargetPosition(ticks);

        motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorL.setPower(SPEED);
        motorR.setPower(SPEED);
    }

    public int getMotorLPosition()
    {
        return motorL.getCurrentPosition();
    }

    public int getMotorRPosition()
    {
        return motorR.getCurrentPosition();
    }
}