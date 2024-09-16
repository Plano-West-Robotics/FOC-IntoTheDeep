package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * DriveTeleOps utilizes inheritance by extending to
 * OpMode. Initalizing variables for the drive constants.
 */
@TeleOp(name = "TELEOP - USE THIS ONE")
public class  DriveTeleOp extends OpMode {
    // Drive Variables
    private DcMotor motorFR, motorFL, motorBR, motorBL;
    private double powerFR, powerFL, powerBR, powerBL;
    private double drive = 0, strafe = 0, turn = 0;
    private double speed = 0.4;
    private boolean last_left_bumper = false;
    private boolean last_right_bumper = false;

    @Override
    public void loop() {
        takeControllerInput();
        drive();
    }

    /**
     * Utilizes inout given from the controller.
     */
    private void takeControllerInput() {
        drive = -1 * gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        if (gamepad1.left_bumper && last_left_bumper != gamepad1.left_bumper) speed = Math.max(0.15, speed - 0.15); // Decrements speed by 0.15 but can't go lower than 0.15
        if (gamepad1.right_bumper && last_right_bumper != gamepad1.right_bumper) speed = Math.min(1, speed + 0.15); // Increments speed by 0.15 but can't go higher than 1

        last_left_bumper = gamepad1.left_bumper;
        last_right_bumper = gamepad1.right_bumper;
    }

    private void drive() {
        powerFR = drive - strafe;
        powerFL = drive + strafe;
        powerBR = drive + strafe;
        powerBL = drive - strafe;

        // adds turn
        powerFR -= turn;
        powerBR -= turn;
        powerFL += turn;
        powerBL += turn;

        // multiplies by speed
        powerFR *= speed;
        powerFL *= speed;
        powerBR *= speed;
        powerBL *= speed;

        // applies the power
        motorFR.setPower(powerFR);
        motorFL.setPower(powerFL);
        motorBR.setPower(powerBR);
        motorBL.setPower(powerBL);
    }

    @Override
    public void init() {
        //initializes the drive motors
        motorFR = hardwareMap.get(DcMotor.class, "frontRight");
        motorFL = hardwareMap.get(DcMotor.class, "frontLeft");
        motorBR = hardwareMap.get(DcMotor.class, "rearRight");
        motorBL = hardwareMap.get(DcMotor.class, "rearLeft");

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
