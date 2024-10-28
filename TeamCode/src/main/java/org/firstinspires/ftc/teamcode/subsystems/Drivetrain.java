package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drivetrain {
    private final DcMotor motorFR, motorFL, motorBR, motorBL;
    private final Gamepad g;

    private double drive, strafe, turn, speed;
    private boolean last_left_bumper, last_right_bumper;

    public Drivetrain(DcMotor motorFR, DcMotor motorFL, DcMotor motorBR, DcMotor motorBL, Gamepad g)
    {
        this.motorFR = motorFR;
        this.motorFL = motorFL;
        this.motorBR = motorBR;
        this.motorBL = motorBL;
        this.g = g;

        drive = strafe = turn = 0;
        speed = 0.4;
        last_left_bumper = last_right_bumper = false;

        initMotors();
    }

    /**
     * Applies power to motors after calculating amount of power needed
     */
    public void drive()
    {
        // Motor power variables
        double powerFR = drive - strafe;
        double powerFL = drive + strafe;
        double powerBR = drive + strafe;
        double powerBL = drive - strafe;

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

    /**
     * Gets input from controller and increments/decrements speed multiplier value
     */
    public void takeControllerInput()
    {
        drive = -1 * g.left_stick_y;
        strafe = g.left_stick_x;
        turn = g.right_stick_x;

        if (g.left_bumper && last_left_bumper != g.left_bumper) speed = Math.max(0.15, speed - 0.15); // Decrements speed by 0.15 but can't go lower than 0.15
        if (g.right_bumper && last_right_bumper != g.right_bumper) speed = Math.min(1, speed + 0.15); // Increments speed by 0.15 but can't go higher than 1

        last_left_bumper = g.left_bumper;
        last_right_bumper = g.right_bumper;
    }

    private void initMotors()
    {
        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFR.setPower(0);
        motorFL.setPower(0);
        motorBR.setPower(0);
        motorBL.setPower(0);
    }
}