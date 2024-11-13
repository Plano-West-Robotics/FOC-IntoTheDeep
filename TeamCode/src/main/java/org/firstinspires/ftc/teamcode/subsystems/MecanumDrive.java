package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.RobotParameters;

public class MecanumDrive
{
    public final DcMotor motorFR, motorFL, motorBR, motorBL;
    public final Telemetry telemetry;
    public double drive, strafe, turn, speed;
    public double powerFR, powerFL, powerBR, powerBL;
    public boolean last_left_bumper, last_right_bumper;

    public MecanumDrive(Hardware hw, Telemetry telemetry)
    {
        this.motorFR = hw.motorFR;
        this.motorFL = hw.motorFL;
        this.motorBR = hw.motorBR;
        this.motorBL = hw.motorBL;
        this.telemetry = telemetry;

        drive = strafe = turn = 0;
        speed = RobotParameters.INITIAL_DRIVE_SPEED;
        last_left_bumper = last_right_bumper = false;
    }

    /**
     * Reads input from the gamepad to get drive variables;
     * increments/decrements speed multiplier value upon valid input.
     */
    public void takeControllerInput(Gamepad gamepad)
    {
        drive = -1 * gamepad.left_stick_y;
        strafe = gamepad.left_stick_x;
        turn = gamepad.right_stick_x;

        if ((last_left_bumper != gamepad.left_bumper) && gamepad.left_bumper)
        {
            // Decrements speed by 0.15 but can't go lower than 0.15
            speed = Math.max(0.15, speed - 0.15);
        }
        if ((last_right_bumper != gamepad.right_bumper) && gamepad.right_bumper)
        {
            // Increments speed by 0.15 but can't go higher than 1
            speed = Math.min(1, speed + 0.15);
        }

        last_left_bumper = gamepad.left_bumper;
        last_right_bumper = gamepad.right_bumper;
    }

    /**
     * Updates power values for each motor based on drive variables.
     */
    public void updateMotorPowers()
    {
        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Motor power variables
        powerFR = drive - strafe;
        powerFL = drive + strafe;
        powerBR = drive + strafe;
        powerBL = drive - strafe;

        // Adds turn
        powerFR -= turn;
        powerBR -= turn;
        powerFL += turn;
        powerBL += turn;

        // Multiplies by speed
        powerFR *= speed;
        powerFL *= speed;
        powerBR *= speed;
        powerBL *= speed;

        // Sets the power
        motorFR.setPower(powerFR);
        motorFL.setPower(powerFL);
        motorBR.setPower(powerBR);
        motorBL.setPower(powerBL);
    }
}