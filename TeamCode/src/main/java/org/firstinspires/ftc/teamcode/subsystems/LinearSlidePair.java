package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MathUtils;
import org.firstinspires.ftc.teamcode.RobotParameters;

public class LinearSlidePair
{
    public final DcMotor motorL, motorR;
    public final Gamepad gamepad;
    public final Telemetry telemetry;
    public final double MAX_SPEED;  // Within [0, 1].
    public final int MAX_TICKS;
    public double stickInput;   // Y-value of the left joystick.
    public boolean a, x, b, y;  // Buttons for traveling to the low chamber, high chamber, low
                                // basket, and high basket, respectively.
    public boolean a_last, x_last, b_last, y_last;

    public LinearSlidePair(DcMotor motorL,
                           DcMotor motorR,
                           Gamepad gamepad,
                           Telemetry telemetry,
                           double maxSpeed,
                           int maxTicks)
    {
        this.motorL = motorL;
        this.motorR = motorR;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
        this.MAX_SPEED = maxSpeed;
        this.MAX_TICKS = maxTicks;

        stickInput = 0;
        a = x = b = y = a_last = x_last = b_last = y_last = false;
    }

    // Should only be used for intake slide.
    public void updateVelocity()
    {
        int avgPosition = (int) Math.round((motorL.getCurrentPosition() + motorR.getCurrentPosition()) / 2.0);

        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        stickInput = -1 * gamepad.left_stick_y;

        /*
        The maximum speed the motors can safely travel at
        (it tends towards 0 as slides approach minimum and maximum extension).
         */
        double smartMaxSpeed = MathUtils.calcSmartMaxSpeed(
                avgPosition,
                MAX_SPEED,
                MAX_TICKS,
                RobotParameters.INTAKE_SLIDE_SLOWDOWN_THRESHOLD
        );

        /*
        If the slide is approaching an endpoint, the lesser of two speeds' magnitudes
        (stickInput and smartMaxSpeed) is assigned to stickInput.
         */
        if (stickInput < 0 && avgPosition < RobotParameters.INTAKE_SLIDE_SLOWDOWN_THRESHOLD)
        {
            stickInput = Math.max(stickInput, -smartMaxSpeed);
        }
        if (stickInput > 0 && avgPosition > MAX_TICKS - RobotParameters.INTAKE_SLIDE_SLOWDOWN_THRESHOLD)
        {
            stickInput = Math.min(stickInput, smartMaxSpeed);
        }

        /*
        Covers cases when slide is not within "approaching" threshold of an endpoint
        but stickInput exceeds MAX_SPEED.
         */
        stickInput = MathUtils.clamp(stickInput, -MAX_SPEED, MAX_SPEED);

        motorL.setPower(stickInput);
        motorR.setPower(stickInput);
    }

    public void simpleUpdateVelocity()
    {
        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        stickInput = -1 * gamepad.left_stick_y;

        motorL.setPower(stickInput);
        motorR.setPower(stickInput);
    }

    // Should only be used for outtake slide.
    // TODO: Not usable until scoring heights are determined (check RobotParameters).
    public void useButtonAction()
    {
        a = gamepad.a;
        x = gamepad.x;
        b = gamepad.b;
        y = gamepad.y;

        if ((a_last != a) && a) goTo(RobotParameters.LOW_CHAMBER_HEIGHT);
        else if ((x_last != x) && x) goTo(RobotParameters.HIGH_CHAMBER_HEIGHT);
        else if ((b_last != b) && b) goTo(RobotParameters.LOW_BASKET_HEIGHT);
        else if ((y_last != y) && y) goTo(RobotParameters.HIGH_BASKET_HEIGHT);

        a_last = gamepad.a;
        x_last = gamepad.x;
        b_last = gamepad.b;
        y_last = gamepad.y;
    }

    // Should only be used for outtake slide.
    public void goTo(int ticks) throws IllegalArgumentException
    {
        if (ticks > MAX_TICKS) throw new IllegalArgumentException("Target height exceeds this linear slide's maximum extension.");

        motorL.setTargetPosition(ticks);
        motorR.setTargetPosition(ticks);

        motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorL.setPower(MAX_SPEED);
        motorR.setPower(MAX_SPEED);
    }
}