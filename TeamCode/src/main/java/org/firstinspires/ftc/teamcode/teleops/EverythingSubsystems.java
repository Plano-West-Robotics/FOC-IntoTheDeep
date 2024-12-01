package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.IntakeArm;
import org.firstinspires.ftc.teamcode.subsystems.IntakeClaw;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSwivel;
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
    public enum AutoRetractStates {
        WAIT_FOR_INPUT,
        RETRACT_INTAKE_ARM,
        TRANSFER_INTO_STORAGE,
        GRAB_BLOCK_FROM_STORAGE,
        EXTEND_OUTPUT_ARM
    }

    AutoRetractStates autoRetract = AutoRetractStates.WAIT_FOR_INPUT;

    public IntakeArm iArm;
    public boolean iArmAutoRetractTriggered;

    public IntakeSlides iSlides;

    public IntakeClaw iClaw;

    public boolean iWheelsAutoEjectTriggered;

    public IntakeSwivel iSwivel;

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

    public float g2_l_trigger, g2_r_trigger;
    public float g2_l_trigger_last, g2_r_trigger_last;

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
        iSwivel = new IntakeSwivel(hardwareMap);
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
            iArm.isStandby = false;
            iArmAutoRetractTriggered = false;
        }

        // IntakeSlides
        {
            iSlidePower = 0;
        }

        // Intake Claw
        {
            iClaw.openIfPossible();
        }

        // Intake Swivel
        {
            iSwivel.moveTo(0.5);
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
            if (triggerToggle(g2_l_trigger, g2_l_trigger_last))
            {
                iArm.switchPositions(); // makes it so the intake arm extends if it's retracted and retracts if it's extended
            }

            switch (autoRetract)
            {
                case WAIT_FOR_INPUT:
                    if (justPressed(g2_y, g2_y_last) && iArm.isExtended && !oArm.isExtended)
                        autoRetract = AutoRetractStates.RETRACT_INTAKE_ARM;
                    break;

                case RETRACT_INTAKE_ARM:
                    if (triggerToggle(g2_l_trigger, g2_l_trigger_last)) {
                        autoRetract = AutoRetractStates.WAIT_FOR_INPUT;
                        break;
                    }

                    oClaw.openIfPossible();
                    iSwivel.moveTo(0.5);
                    iArm.retractIfPossible();

                    autoOuttakeSequenceTimer.reset();
                    autoRetract = AutoRetractStates.TRANSFER_INTO_STORAGE;
                    break;

                case TRANSFER_INTO_STORAGE:
                    if (triggerToggle(g2_l_trigger, g2_l_trigger_last)) {
                        autoRetract = AutoRetractStates.WAIT_FOR_INPUT;
                        break;
                    }

                    if (autoOuttakeSequenceTimer.milliseconds() > 1200) {
                        iClaw.open();
                        autoOuttakeSequenceTimer.reset();
                        autoRetract = AutoRetractStates.GRAB_BLOCK_FROM_STORAGE;
                    }
                    break;

                case GRAB_BLOCK_FROM_STORAGE:
                    if (triggerToggle(g2_l_trigger, g2_l_trigger_last)) {
                        autoRetract = AutoRetractStates.WAIT_FOR_INPUT;
                        break;
                    }
                    if (autoOuttakeSequenceTimer.milliseconds() > 400) {
                        oClaw.close();
                        iArm.extend();
                        autoOuttakeSequenceTimer.reset();
                        autoRetract = AutoRetractStates.EXTEND_OUTPUT_ARM;
                    }
                    break;

                case EXTEND_OUTPUT_ARM:
                    if (autoOuttakeSequenceTimer.milliseconds() > 400) {
                        oArm.extend();
                        autoRetract = AutoRetractStates.WAIT_FOR_INPUT;
                    }
                    break;
            }

            if (boolLogic.triggerAutoExtend(g2_l_stick_y, iSlides.motorL))
            {
                iArm.extendIfPossible();
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

        // Intake Claw
        {
            if (justPressed(g2_x, g2_x_last)) {
                iClaw.switchStates();
            }
        }

        // Intake Swivel
        {
            if (justPressed(g2_l_bumper, g2_l_bumper_last)) {
                iSwivel.rotCCW();
            } else if (justPressed(g2_r_bumper, g2_r_bumper_last)) {
                iSwivel.rotCW();
            }
        }

        // OuttakeArm
        {
            if (triggerToggle(g2_r_trigger, g2_r_trigger_last))
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
        g2_l_trigger = gamepad2.left_trigger;
        g2_r_trigger = gamepad2.right_trigger;

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
        g2_l_trigger_last = g2_l_trigger;
        g2_r_trigger_last = g2_r_trigger;
    }

    public boolean justPressed(boolean currentInput, boolean lastInput)
    {
        return currentInput && !lastInput;
    }

    public boolean justReleased(boolean currentInput, boolean lastInput)
    {
        return !currentInput && lastInput;
    }

    public boolean triggerToggle(float curTrigger, float lastTrigger) {
        return (curTrigger >= 0.2 && lastTrigger < 0.2);
    }


}
