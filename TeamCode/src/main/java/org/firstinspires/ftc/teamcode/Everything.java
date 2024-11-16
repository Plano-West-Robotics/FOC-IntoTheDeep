package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Everything")
public class Everything extends OpMode
{
    public Servo iArmL, iArmR;
    public double iArmLExtendPosition, iArmRExtendPosition, iArmLRetractPosition, iArmRRetractPosition;
    public boolean iArmIsExtended;

    public DcMotor iSlideL, iSlideR;
    public int I_SLIDE_MAX_POSITION;
    public double I_SLIDE_SPEED_MULTIPLIER;

    public CRServo iWheelL, iWheelR;
    public boolean iWheelsAreIntaking, iWheelsAreStopped;

    public DcMotor fr, fl, br, bl;
    public double frPower, flPower, brPower, blPower;
    public double drive, strafe, turn;
    public double MECANUM_DRIVE_SPEED, MECANUM_DRIVE_TURN_SPEED;

    public Servo oArm;
    public double oArmExtendPosition, oArmRetractPosition;
    public boolean oArmIsExtended;

    public Servo oClaw;
    public double oClawClosePosition, oClawOpenPosition;
    public boolean oClawIsOpen;

    public DcMotor oSlideL, oSlideR;
    public int O_SLIDE_MAX_POSITION;
    public double O_SLIDE_SPEED_MULTIPLIER;

    public boolean g1_a, g1_x, g1_b, g1_y, g1_l_bumper, g1_r_bumper;
    public boolean g2_a, g2_x, g2_b, g2_y, g2_l_bumper, g2_r_bumper;
    public boolean g1_a_last, g1_x_last, g1_b_last, g1_y_last, g1_l_bumper_last, g1_r_bumper_last;
    public boolean g2_a_last, g2_x_last, g2_b_last, g2_y_last, g2_l_bumper_last, g2_r_bumper_last;

    public double g1_l_stick_x, g1_l_stick_y, g1_r_stick_x, g1_r_stick_y;
    public double g2_l_stick_x, g2_l_stick_y, g2_r_stick_x, g2_r_stick_y;

    public void readControllerInput()
    {
        g1_a = gamepad1.a;
        g1_b = gamepad1.b;
        g1_x = gamepad1.x;
        g1_y = gamepad1.y;
        g1_l_bumper = gamepad1.left_bumper;
        g1_r_bumper = gamepad1.right_bumper;
        g2_a = gamepad2.a;
        g2_b = gamepad2.b;
        g2_x = gamepad2.x;
        g2_y = gamepad2.y;
        g2_l_bumper = gamepad2.left_bumper;
        g2_r_bumper = gamepad2.right_bumper;

        g1_l_stick_x = gamepad1.left_stick_x;
        g1_l_stick_y = gamepad1.left_stick_y;
        g1_r_stick_x = gamepad1.right_stick_x;
        g1_r_stick_y = gamepad1.right_stick_y;
        g2_l_stick_x = gamepad2.left_stick_x;
        g2_l_stick_y = gamepad2.left_stick_y;
        g2_r_stick_x = gamepad2.right_stick_x;
        g2_r_stick_y = gamepad2.right_stick_y;

    }

    public void assignLastInputValues()
    {
        g1_a_last = g1_a;
        g1_b_last = g1_b;
        g1_x_last = g1_x;
        g1_y_last = g1_y;
        g1_l_bumper_last = g1_l_bumper;
        g1_r_bumper_last = g1_r_bumper;
        g2_a_last = g2_a;
        g2_b_last = g2_b;
        g2_x_last = g2_x;
        g2_y_last = g2_y;
        g2_l_bumper_last = g2_l_bumper;
        g2_r_bumper_last = g2_r_bumper;
    }

    @Override
    public void init()
    {
        // IntakeArm
        {
            iArmL = hardwareMap.get(Servo.class, "L1");
            iArmR = hardwareMap.get(Servo.class, "R1");
            iArmLExtendPosition = 0.08;
            iArmRExtendPosition = 0.92;
            iArmLRetractPosition = 0.96;
            iArmRRetractPosition = 0.04;
            iArmIsExtended = false;
        }

        // IntakeSlides
        {
            iSlideL = hardwareMap.get(DcMotor.class, "iSlideL");
            iSlideR = hardwareMap.get(DcMotor.class, "iSlideR");

            iSlideR.setDirection(DcMotorSimple.Direction.REVERSE);

            iSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            iSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            iSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            iSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            iSlideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            iSlideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            iSlideL.setPower(0);
            iSlideR.setPower(0);

            I_SLIDE_MAX_POSITION = 1100;
            I_SLIDE_SPEED_MULTIPLIER = 0.4;
        }

        // IntakeWheels
        {
            iWheelL = hardwareMap.get(CRServo.class, "L2");
            iWheelR = hardwareMap.get(CRServo.class, "R2");

            iWheelL.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        // MecanumDrive
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

            MECANUM_DRIVE_SPEED = 0.5;
            MECANUM_DRIVE_TURN_SPEED = 0.7;
        }

        // OuttakeArm
        {
            oArm = hardwareMap.get(Servo.class, "oArm");
            oArmExtendPosition = 0.04;
            oArmRetractPosition = 0.85;
            oArmIsExtended = false;
        }

        // OuttakeClaw
        {
            oClaw = hardwareMap.get(Servo.class, "oClaw");
            oClawClosePosition = 1;
            oClawOpenPosition = 0.93;
            oClawIsOpen = false;
        }

        // OuttakeSlides
        {
            oSlideL = hardwareMap.get(DcMotor.class, "oSlideL");
            oSlideR = hardwareMap.get(DcMotor.class, "oSlideR");

            oSlideL.setDirection(DcMotorSimple.Direction.REVERSE);

            oSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            oSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            oSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            oSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            oSlideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            oSlideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            oSlideL.setPower(0);
            oSlideR.setPower(0);

            O_SLIDE_MAX_POSITION = 2700;
            O_SLIDE_SPEED_MULTIPLIER = 0.5;
        }
    }

    @Override
    public void start()
    {
        // IntakeArm
        {
            iArmL.setPosition(iArmLRetractPosition);
            iArmR.setPosition(iArmRRetractPosition);
        }

        // IntakeWheels
        {
            iWheelsAreIntaking = false;
            iWheelsAreStopped = false;
        }

        // OuttakeArm
        {
            oArm.setPosition(oArmRetractPosition);
        }

        // OuttakeClaw
        {
            oClaw.setPosition(oClawClosePosition);
        }
    }

    @Override
    public void loop()
    {
        readControllerInput();

        // IntakeArm
        {
            if (ControllerUtils.justPressed(g2_b, g2_b_last))
            {
                if (iArmIsExtended)
                {
                    iArmL.setPosition(iArmLRetractPosition);
                    iArmR.setPosition(iArmRRetractPosition);
                }
                else
                {
                    iArmL.setPosition(iArmLExtendPosition);
                    iArmR.setPosition(iArmRExtendPosition);
                }
                iArmIsExtended = !iArmIsExtended;
            }
            else if (g2_l_stick_y * -1 < -0.1 && (iSlideL.getCurrentPosition() < 300 && iSlideR.getCurrentPosition() < 300))
            {
                if (iArmIsExtended)
                {
                    iArmIsExtended = false;
                    iArmL.setPosition(iArmLRetractPosition);
                    iArmR.setPosition(iArmRRetractPosition);
                    if (!oArmIsExtended)
                    {
                        oClaw.setPosition(oClawOpenPosition);
                        oClawIsOpen = true;
                    }
                }
            } else if (g2_l_stick_y * -1 > 0.1 && (iSlideL.getCurrentPosition() > 300 && iSlideR.getCurrentPosition() > 300))
            {
                if (!iArmIsExtended)
                {
                    iArmIsExtended = true;
                    iArmL.setPosition(iArmLExtendPosition);
                    iArmR.setPosition(iArmRExtendPosition);
                }
            }
        }

        // IntakeSlides
        {
            // When joystick says retract but slides are fully retracted.
            if (g2_l_stick_y * -1 < 0 && (iSlideL.getCurrentPosition() <= 0 || iSlideR.getCurrentPosition() <= 0))
            {
                iSlideL.setPower(0);
                iSlideR.setPower(0);
            }
            // When joystick says extend but slides are fully extended.
            else if (g2_l_stick_y * -1 > 0 && (iSlideL.getCurrentPosition() >= I_SLIDE_MAX_POSITION || iSlideR.getCurrentPosition() >= I_SLIDE_MAX_POSITION))
            {
                iSlideL.setPower(0);
                iSlideR.setPower(0);
            }
            else
            {
                iSlideL.setPower(g2_l_stick_y * -1 * I_SLIDE_SPEED_MULTIPLIER);
                iSlideR.setPower(g2_l_stick_y * -1 * I_SLIDE_SPEED_MULTIPLIER);
            }
        }

        // IntakeWheels
        {
            if (ControllerUtils.justPressed(g2_r_bumper, g2_r_bumper_last)) iWheelsAreStopped = !iWheelsAreStopped;

            if (ControllerUtils.justPressed(g2_x, g2_x_last))
            {

                if (iWheelsAreIntaking)
                {
                    iWheelL.setPower(-1);
                    iWheelR.setPower(-1);
                    iWheelsAreIntaking = !iWheelsAreIntaking;
                }
                else
                {
                    iWheelL.setPower(1);
                    iWheelR.setPower(1);
                    iWheelsAreIntaking = !iWheelsAreIntaking;
                }
            }

            if (ControllerUtils.justPressed(g2_r_bumper, g2_r_bumper_last))
            {
                iWheelL.setPower(0);
                iWheelR.setPower(0);
            }
        }

        // MecanumDrive
        {
            drive = -1 * g1_l_stick_y;
            strafe = g1_l_stick_x;
            turn = MECANUM_DRIVE_TURN_SPEED * g1_r_stick_x;

            // Motor power variables
            frPower = drive - strafe;
            flPower = drive + strafe;
            brPower = drive + strafe;
            blPower = drive - strafe;

            // Adds turn
            frPower -= turn;
            brPower -= turn;
            flPower += turn;
            blPower += turn;

            // Multiplies by speed
            frPower *= MECANUM_DRIVE_SPEED;
            flPower *= MECANUM_DRIVE_SPEED;
            brPower *= MECANUM_DRIVE_SPEED;
            blPower *= MECANUM_DRIVE_SPEED;

            // Sets the power
            fr.setPower(frPower);
            fl.setPower(flPower);
            br.setPower(brPower);
            bl.setPower(blPower);
        }

        // OuttakeArm
        {
            if (ControllerUtils.justPressed(g2_y, g2_y_last))
            {
                if (oArmIsExtended) oArm.setPosition(oArmRetractPosition);
                else oArm.setPosition(oArmExtendPosition);
                oArmIsExtended = !oArmIsExtended;
            }
        }

        // OuttakeClaw
        {
            if (ControllerUtils.justPressed(g2_a, g2_a_last))
            {
                if (oClawIsOpen) oClaw.setPosition(oClawClosePosition);
                else oClaw.setPosition(oClawOpenPosition);
                oClawIsOpen = !oClawIsOpen;
            }
        }

        // OuttakeSlides
        {
            // When joystick says retract but slides are fully retracted.
            if (g2_r_stick_y * -1 < 0 && (oSlideL.getCurrentPosition() <= 0 || oSlideR.getCurrentPosition() <= 0))
            {
                oSlideL.setPower(0);
                oSlideR.setPower(0);
            }
            // When joystick says extend but slides are fully extended.
            else if (g2_r_stick_y * -1 > 0 && (oSlideL.getCurrentPosition() >= O_SLIDE_MAX_POSITION || oSlideR.getCurrentPosition() >= O_SLIDE_MAX_POSITION))
            {
                oSlideL.setPower(0);
                oSlideR.setPower(0);
            }
            else
            {
                oSlideL.setPower(g2_r_stick_y * -1 * O_SLIDE_SPEED_MULTIPLIER);
                oSlideR.setPower(g2_r_stick_y * -1 * O_SLIDE_SPEED_MULTIPLIER);
            }
        }

        telemetry.addData("Gamepad 2 Right Stick Y", g2_r_stick_y);
        telemetry.addData("Left Outtake Slide Motor Position", oSlideL.getCurrentPosition());
        telemetry.addData("Right Outtake Slide Motor Position", oSlideR.getCurrentPosition());
        telemetry.update();

        assignLastInputValues();
    }
}
