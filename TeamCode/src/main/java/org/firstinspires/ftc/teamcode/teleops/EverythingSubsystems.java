package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.IntakeArm;
import org.firstinspires.ftc.teamcode.subsystems.IntakeClaw;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSlides;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeArm;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeClaw;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSlides;
import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;
import org.firstinspires.ftc.teamcode.subsystems.BooleanLogic;

/**
 * One servo for the pickup claw
 * One servo for the pickup rotation
 * Two servos for the intake arm
 */

@TeleOp(name="EverythingSubsystems")
public class EverythingSubsystems extends OpMode
{
    public IntakeArm iArm;
    public boolean iArmAutoRetractTriggered;

    public IntakeSlides iSlides;

    public IntakeClaw iClaw;

    public boolean iWheelsAutoEjectTriggered;

    public OuttakeArm oArm;
    public boolean oArmAutoExtendTriggered;

    public OuttakeClaw oClaw;

    public OuttakeSlides oSlides;

    public TeleDrive drive;

    public BooleanLogic boolLogic;

    public ElapsedTime autoOuttakeSequenceTimer;

    public boolean g1_a, g1_x, g1_b, g1_y, g1_l_bumper, g1_r_bumper;
    public boolean g2_a, g2_x, g2_b, g2_y, g2_l_bumper, g2_r_bumper;
    public boolean g1_a_last, g1_x_last, g1_b_last, g1_y_last, g1_l_bumper_last, g1_r_bumper_last;
    public boolean g2_a_last, g2_x_last, g2_b_last, g2_y_last, g2_l_bumper_last, g2_r_bumper_last;

    public double g1_l_stick_x, g1_l_stick_y, g1_r_stick_x, g1_r_stick_y;
    public double g2_l_stick_x, g2_l_stick_y, g2_r_stick_x, g2_r_stick_y;

    public double iSlidePower;
    public double oSlidePower;

    @Override
    public void init()
    {
        iArm = new IntakeArm(hardwareMap);
        iSlides = new IntakeSlides(hardwareMap);
        iClaw = new IntakeClaw(hardwareMap);
        oArm = new OuttakeArm(hardwareMap);
        oClaw = new OuttakeClaw(hardwareMap);
        oSlides = new OuttakeSlides(hardwareMap);
        drive = new TeleDrive(hardwareMap);
        boolLogic = new BooleanLogic();
        autoOuttakeSequenceTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    @Override
    public void start()
    {
        // IntakeArm
        {
            iArm.retract();
            iArmAutoRetractTriggered = false;
        }

        // IntakeSlides
        {
            iSlidePower = 0;
        }

        // OuttakeArm
        {
            oArm.retract();
            oArmAutoExtendTriggered = false;
        }

        // OuttakeClaw
        {
            oClaw.close();
        }

        // OuttakeSlides
        {
            oSlidePower = 0;
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
            if (justPressed(g2_b, g2_b_last))
            {
                iArm.switchPositions(); // makes it so the intake arm extends if it's retracted and retracts if it's extended
            }

            else if (boolLogic.triggerAutoRetract(g2_l_stick_y, iSlides.motorL) && iArm.isExtended && !oArmAutoExtendTriggered) // the last boolean here (!oArmAutoExtendTriggered) checks for whether the claw has extended yet in the logic sequence
            {
                    autoOuttakeSequenceTimer.reset();
                    iArmAutoRetractTriggered = true;
                    iArm.retractIfPossible();
                    if (!oArm.isExtended) oClaw.open();
            }

            else if (boolLogic.triggerAutoExtend(g2_l_stick_y, iSlides.motorL))
            {
                iArm.extendIfPossible();
            }

            // If the auto retract was triggered and it has been 1.3 seconds, set the wheels to eject mode
            if (boolLogic.timerStageMS(1300, iArmAutoRetractTriggered, autoOuttakeSequenceTimer))
            {
                boolLogic.switchAndSetNext(iArmAutoRetractTriggered, iWheelsAutoEjectTriggered);
            }

            // If it has been 2.1 seconds since the auto retract was triggered and the block was ejected from the intake arm,
            // extend the intake so it doesn't get in the way and close the claw to hold the block,
            else if (boolLogic.timerStageMS(2100, iWheelsAutoEjectTriggered, autoOuttakeSequenceTimer))
            {
                boolLogic.switchAndSetNext(iWheelsAutoEjectTriggered, oArmAutoExtendTriggered);
                iArm.extendIfPossible();
                oClaw.closeIfPossible();
            }

            // If it has been 3 seconds since the auto retract was triggered and the claw has been closed,
            // extend the arm
            else if (boolLogic.timerStageMS(3000, oArmAutoExtendTriggered, autoOuttakeSequenceTimer))
            {
                oArmAutoExtendTriggered = false;
                oArm.extendIfPossible();
            }

        }

        // IntakeSlides
        {
            // When joystick is negative but either of the slides is fully retracted.
            if (!boolLogic.stickIsPositive(g2_l_stick_y) && boolLogic.motorMinPosReached(iSlides.motorL, iSlides.motorR))
            {
                iSlidePower = 0;
            }

            // When joystick says extend but slides are fully extended.
            else if (boolLogic.stickIsPositive(g2_l_stick_y) && boolLogic.motorMaxPosReached(iSlides.motorL, iSlides.motorR, iSlides.MAX_POSITION))
            {
                iSlidePower = 0;
            }
            else iSlidePower = g2_l_stick_y * -1;

            iSlides.setPower(iSlidePower);
        }

        // OuttakeArm
        {
            if (justPressed(g2_y, g2_y_last))
            {
                oArm.switchStates();
            }
        }

        // OuttakeClaw
        {
            if (justPressed(g2_a, g2_a_last))
            {
                oClaw.switchStates();
            }
        }

        // OuttakeSlides
        {
            // When joystick says retract but slides are fully retracted.
            if (!boolLogic.stickIsPositive(g2_r_stick_y) && boolLogic.motorMinPosReached(oSlides.motorL, oSlides.motorR))
            {
                oSlidePower = 0;
            }
            // When joystick says extend but slides are fully extended.
            else if (boolLogic.stickIsPositive(g2_r_stick_y) && boolLogic.motorMaxPosReached(oSlides.motorL, oSlides.motorR, oSlides.MAX_POSITION))
            {
                oSlidePower = 0;
            }
            else oSlidePower = g2_r_stick_y * -1;

            oSlides.setPower(oSlidePower);
        }

        // TeleDrive
        {
            if (justPressed(g1_a, g1_a_last)) drive.toggleSlowMode();
            drive.updateMotorPowers(-1 * g1_l_stick_y, g1_l_stick_x, g1_r_stick_x);
        }

        assignLastInputValues();
    }

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

    public boolean justPressed(boolean currentInput, boolean lastInput)
    {
        return currentInput && !lastInput;
    }

    public boolean justReleased(boolean currentInput, boolean lastInput)
    {
        return !currentInput && lastInput;
    }
}
