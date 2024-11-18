package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.IntakeArm;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSlides;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWheels;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeArm;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeClaw;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSlides;
import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;

@TeleOp(name="EverythingSubsystems")
public class EverythingSubsystems extends OpMode
{
    public IntakeArm iArm;

    public ElapsedTime iArmRetractTimer;

    public IntakeSlides iSlides;
    public boolean iSlidesAutoRetractTriggered;

    public IntakeWheels iWheels;
    public boolean iWheelsAutoEjectTriggered;

    public OuttakeArm oArm;
    public boolean autoOArmExtendTriggered;

    public OuttakeClaw oClaw;

    public OuttakeSlides oSlides;

    public TeleDrive drive;

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
        iArm = new IntakeArm(hardwareMap);
        iSlides = new IntakeSlides(hardwareMap);
        iWheels = new IntakeWheels(hardwareMap);
        oArm = new OuttakeArm(hardwareMap);
        oClaw = new OuttakeClaw(hardwareMap);
        oSlides = new OuttakeSlides(hardwareMap);
        drive = new TeleDrive(hardwareMap);
    }

    @Override
    public void start()
    {
        // IntakeArm
        {
            iArm.retract();
            iArmRetractTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        }

        // IntakeSlides
        {
            iSlidesAutoRetractTriggered = false;
        }

        // IntakeWheels
        {
            iWheels.areIntaking = false;
            iWheels.areStopped = false;
            iWheelsAutoEjectTriggered = false;
        }

        // OuttakeArm
        {
            oArm.retract();
            autoOArmExtendTriggered = false;
        }

        // OuttakeClaw
        {
            oClaw.close();
        }

        // OuttakeSlides
        {

        }

        // TeleDrive
        {

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
                if (iArm.isExtended) iArm.retract();
                else iArm.extend();
            }
            else if (g2_l_stick_y * -1 < -0.1 && iSlides.motorL.getCurrentPosition() < 400 && iSlides.motorR.getCurrentPosition() < 400)
            {
                if (iArm.isExtended && !autoOArmExtendTriggered)
                {
                    iArmRetractTimer.reset();
                    iSlidesAutoRetractTriggered = true;
                    iArm.retract();
                    if (!oArm.isExtended) oClaw.open();
                }
            }
            else if (g2_l_stick_y * -1 < -0.1 && iSlides.motorL.getCurrentPosition() > 300 && iSlides.motorR.getCurrentPosition() > 300)
            {
                if (!iArm.isExtended)
                {
                    iArm.extend();
                    iWheels.intake();
                }
            }

            if (iSlidesAutoRetractTriggered && iArmRetractTimer.milliseconds() >= 1300)
            {
                iWheelsAutoEjectTriggered = true;
                iSlidesAutoRetractTriggered = !iSlidesAutoRetractTriggered;
                iWheels.eject();
            }
            else if (iWheelsAutoEjectTriggered && iArmRetractTimer.milliseconds() >= 2100)
            {
                iWheelsAutoEjectTriggered = !iWheelsAutoEjectTriggered;
                autoOArmExtendTriggered = true;
                if (!iArm.isExtended) iArm.extend();
                if (oClaw.isOpen) oClaw.close();
            }
            else if (autoOArmExtendTriggered && iArmRetractTimer.milliseconds() >= 3000)
            {
                autoOArmExtendTriggered = !autoOArmExtendTriggered;
                if (!oArm.isExtended) oArm.extend();
            }
        }

        // IntakeSlides
        {
            // When joystick says retract but slides are fully retracted.
            if (g2_l_stick_y * -1 < 0)
            {
                if (iSlides.motorL.getCurrentPosition() <= 0 || iSlides.motorL.getCurrentPosition() <= 0)
                {
                    iSlides.setPower(0);
                }
            }
            // When joystick says extend but slides are fully extended.
            else if (g2_l_stick_y * -1 > 0)
            {
                if (iSlides.motorL.getCurrentPosition() >= iSlides.MAX_POSITION || iSlides.motorR.getCurrentPosition() >= iSlides.MAX_POSITION)
                {
                    iSlides.setPower(0);
                }
            }
            else iSlides.setPower(g2_l_stick_y * -1);
        }

        // IntakeWheels
        {
            if (ControllerUtils.justPressed(g2_r_bumper, g2_r_bumper_last))
            {
                iWheels.areStopped = !iWheels.areStopped;
                if (iWheels.areStopped) iWheels.stop();
                else
                {
                    if (iWheels.areIntaking) iWheels.intake();
                    else iWheels.eject();
                }
            }

            if (ControllerUtils.justPressed(g2_x, g2_x_last) && !iWheels.areStopped)
            {
                if (iWheels.areIntaking) iWheels.eject();
                else iWheels.intake();
            }
        }

        // OuttakeArm
        {
            if (ControllerUtils.justPressed(g2_y, g2_y_last))
            {
                if (oArm.isExtended) oArm.retract();
                else oArm.extend();
            }
        }

        // OuttakeClaw
        {
            if (ControllerUtils.justPressed(g2_a, g2_a_last))
            {
                if (oClaw.isOpen) oClaw.close();
                else oClaw.open();
            }
        }

        // OuttakeSlides
        {
            // When joystick says retract but slides are fully retracted.
            if (g2_r_stick_y * -1 < 0)
            {
                if (oSlides.motorL.getCurrentPosition() <= 0 || oSlides.motorR.getCurrentPosition() <= 0)
                {
                    oSlides.setPower(0);
                }
            }
            // When joystick says extend but slides are fully extended.
            else if (g2_r_stick_y * -1 > 0)
            {
                if (oSlides.motorL.getCurrentPosition() >= oSlides.MAX_POSITION || oSlides.motorR.getCurrentPosition() >= oSlides.MAX_POSITION)
                {
                    oSlides.setPower(0);
                }
            }
            else oSlides.setPower(g2_r_stick_y * -1);
        }

        // TeleDrive
        {
            drive.updateMotorPowers(-1 * g1_l_stick_y, g1_l_stick_x, g1_r_stick_x);
        }

        assignLastInputValues();
    }
}
