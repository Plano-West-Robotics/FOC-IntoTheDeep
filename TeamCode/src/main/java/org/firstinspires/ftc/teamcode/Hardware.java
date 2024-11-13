package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public final class Hardware
{
    public final DcMotor motorFR, motorFL, motorBR, motorBL;
    public final DcMotor intakeSlideMotorL, intakeSlideMotorR, outtakeSlideMotorL, outtakeSlideMotorR;
    public final Servo intakeServoL, intakeServoR;
    public final CRServo intakeWheelL, intakeWheelR;

    public Hardware(HardwareMap hwMap)
    {
        // Mecanum drive motors.
        {
            motorFR = hwMap.get(DcMotor.class, "frontRight");
            motorFL = hwMap.get(DcMotor.class, "frontLeft");
            motorBR = hwMap.get(DcMotor.class, "backRight");
            motorBL = hwMap.get(DcMotor.class, "backLeft");

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

        // Intake linear slide motors.
        {
            intakeSlideMotorL = hwMap.get(DcMotor.class, "intakeLeft");
            intakeSlideMotorR = hwMap.get(DcMotor.class, "intakeRight");

            intakeSlideMotorR.setDirection(DcMotorSimple.Direction.REVERSE);

            intakeSlideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intakeSlideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            intakeSlideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            intakeSlideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            intakeSlideMotorL.setPower(0);
            intakeSlideMotorR.setPower(0);
        }

        // Outtake linear slide motors.
        {
            outtakeSlideMotorL = hwMap.get(DcMotor.class, "outtakeLeft");
            outtakeSlideMotorR = hwMap.get(DcMotor.class, "outtakeRight");

            outtakeSlideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            outtakeSlideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            outtakeSlideMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            outtakeSlideMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            outtakeSlideMotorL.setPower(0);
            outtakeSlideMotorR.setPower(0);
        }

        // Intake servos.
        {
            intakeServoL = hwMap.get(Servo.class, "intakeLeft");
            intakeServoR = hwMap.get(Servo.class, "intakeRight");

            // TODO: May need to one of the servo's direction.
            // intakeServoL.setDirection(Servo.Direction.REVERSE);
            // intakeServoR.setDirection(Servo.Direction.REVERSE);

            intakeWheelL = hwMap.get(CRServo.class, "intakeWheelLeft");
            intakeWheelR = hwMap.get(CRServo.class, "intakeWheelRight");
        }
    }
}
