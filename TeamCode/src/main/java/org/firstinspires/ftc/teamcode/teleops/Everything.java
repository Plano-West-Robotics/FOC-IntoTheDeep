package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.subsystems.IntakeArm;
import org.firstinspires.ftc.teamcode.subsystems.IntakeClaw;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSlides;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSwivel;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeArm;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeClaw;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSlides;
import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;

@TeleOp(name = "Everything")
public class Everything extends OpMode
{
    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final int I_ARM_EXTEND_TO_RETRACT_TIME = 800;
    public static final int I_ARM_RETRACT_TO_HOVER_TIME = 400;
    public static final int STORAGE_SETTLE_TIME = 400;

    public IntakeArm iArm;
    public IntakeClaw iClaw;
    public IntakeSwivel iSwivel;
    public IntakeSlides iSlides;
    public OuttakeArm oArm;
    public OuttakeClaw oClaw;
    public OuttakeSlides oSlides;
    public TeleDrive teleDrive;
    public Gamepads gamepads;

    public ElapsedTime intakeProbeTimer;
    public IntakeProbe intakeProbeState;
    public ElapsedTime autoTransferTimer;
    public AutoTransfer autoTransferState;

    public enum IntakeProbe
    {
        HOVER, // iArm is hovering and iClaw is open, ready for X to be pressed
        DESCENDING, // iArm is descending and will wait until it is fully extended (determined via timer) before moving on to closing iClaw
        PROBE, // iArm is fully extended and iClaw is closed/gripping
    }

    public enum AutoTransfer
    {
        AWAIT_INPUT,
        RETRACT,
        PREPARE_TRANSFER,
        RELEASE,
        HOVER, // This state only exists to prevent the oArm from extending while the iArm is in the way
    }

    @Override
    public void init()
    {
        iArm = new IntakeArm(hardwareMap);
        iClaw = new IntakeClaw(hardwareMap);
        iSwivel = new IntakeSwivel(hardwareMap);
        iSlides = new IntakeSlides(hardwareMap);
        oArm = new OuttakeArm(hardwareMap);
        oClaw = new OuttakeClaw(hardwareMap);
        oSlides = new OuttakeSlides(hardwareMap);
        teleDrive = new TeleDrive(hardwareMap);
        gamepads = new Gamepads(gamepad1, gamepad2);

        intakeProbeState = IntakeProbe.HOVER;
        autoTransferState = AutoTransfer.AWAIT_INPUT;

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void start()
    {
        /*
        Initial robot pose; initializes a value for each subsystem's state enum (if there is one);
        makes iSwivel find center position
         */
        iArm.hover();
        iClaw.open();
        iSwivel.center();
        oArm.retract();
        oClaw.open();
    }

    @Override
    public void loop()
    {
        if (gamepads.justPressed(Button.GP1_LEFT_BUMPER)) teleDrive.toggleSlowMode();

        double drive = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_Y);
        double strafe = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_X);
        double turn = gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_X);
        teleDrive.updateMotorPowers(drive, strafe, turn);

        if (gamepads.justPressed(Button.GP2_A)) oClaw.togglePosition();

        // The auto transfer sequence must be inactive to use intake probing.
        if (autoTransferState == AutoTransfer.AWAIT_INPUT)
        {
            switch (intakeProbeState)
            {
                case HOVER:
                    /*
                    Additional check (iArm.getPosition() == IntakeArm.Position.HOVER) requires the
                    arm to be in hover position for GP2_X to work, because manual iArm control
                    (GP2_LEFT_TRIGGER) can put iArm in the retracted position while intakeProbeState
                    is simultaneously HOVER. This is done because intakeProbeTimer cuts off when it
                    exceeds IntakeArm.HOVER_TO_EXTEND_DESCENT_TIME.
                     */
                    if (gamepads.justPressed(Button.GP2_X) && iArm.getPosition() == IntakeArm.Position.HOVER)
                    {
                        iArm.extend();
                        iClaw.open();
                        intakeProbeTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
                        intakeProbeState = IntakeProbe.DESCENDING;
                    }
                    break;

                case DESCENDING:
                    if (intakeProbeTimer.time() > IntakeArm.TIME_BETWEEN_HOVER_EXTEND_MS)
                    {
                        iClaw.close();
                        intakeProbeState = IntakeProbe.PROBE;
                    }
                    break;

                case PROBE:
                    if (gamepads.justPressed(Button.GP2_X))
                    {
                        iArm.hover();
                        iClaw.open();
                        intakeProbeState = IntakeProbe.HOVER;
                    }
                    break;
            }
        }

        // Option for exiting auto transfer sequence while it is active
        if (gamepads.justPressed(Button.GP2_Y) && autoTransferState != AutoTransfer.AWAIT_INPUT)
        {
            iArm.hover();
            iClaw.open();
            autoTransferState = AutoTransfer.AWAIT_INPUT;
            intakeProbeState = IntakeProbe.HOVER;
        }

        switch (autoTransferState)
        {
            case AWAIT_INPUT:
                if (gamepads.justPressed(Button.GP2_Y) && intakeProbeState == IntakeProbe.PROBE)
                {
                    iClaw.close();
                    iSwivel.center();
                    iArm.retract();
                    oArm.retract();
                    autoTransferTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
                    autoTransferState = AutoTransfer.RETRACT;
                }
                break;

            case RETRACT:
                if (autoTransferTimer.time() > I_ARM_EXTEND_TO_RETRACT_TIME && iSlides.isRetracted())
                {
                    oClaw.open();
                    autoTransferTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
                    autoTransferState = AutoTransfer.PREPARE_TRANSFER;
                }
                break;

            case PREPARE_TRANSFER:
                if (autoTransferTimer.time() > OuttakeClaw.TIME_TO_TOGGLE_CLAW_MS)
                {
                    iClaw.open();
                    autoTransferTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
                    autoTransferState = AutoTransfer.RELEASE;
                }
                break;

            case RELEASE:
                if (autoTransferTimer.time() > STORAGE_SETTLE_TIME)
                {
                    oClaw.close();
                    iArm.hover();
                    autoTransferTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
                    autoTransferState = AutoTransfer.HOVER;
                }
            case HOVER:
                if (autoTransferTimer.time() > I_ARM_RETRACT_TO_HOVER_TIME)
                {
                    autoTransferState = AutoTransfer.AWAIT_INPUT;
                }
                break;
        }

        if (gamepads.justPressed(Button.GP2_LEFT_BUMPER)) iSwivel.rotCCW();

        if (gamepads.justPressed(Button.GP2_RIGHT_BUMPER)) iSwivel.rotCW();

        if (gamepads.justEnteredThreshold(Analog.GP2_LEFT_TRIGGER, TRIGGER_THRESHOLD))
        {
            iArm.togglePosition(IntakeArm.Position.RETRACT, IntakeArm.Position.HOVER);
        }

        if (gamepads.justEnteredThreshold(Analog.GP2_RIGHT_TRIGGER, TRIGGER_THRESHOLD) && autoTransferState == AutoTransfer.AWAIT_INPUT)
        {
            oArm.togglePosition();
        }

        double iSlidesInput = gamepads.getAnalogValue(Analog.GP2_LEFT_STICK_Y);
        if (!iSlides.isDangerousInput(iSlidesInput)) iSlides.setPower(iSlidesInput);
        else iSlides.setPower(0);

        double oSlidesInput = gamepads.getAnalogValue(Analog.GP2_RIGHT_STICK_Y);
        if (!oSlides.isDangerousInput(oSlidesInput)) oSlides.setPower(oSlidesInput);
        else oSlides.setPower(0);

        telemetry.addData("Raw Intake Slide Input", iSlidesInput);
        telemetry.addData("In L Position", iSlides.motorL.getCurrentPosition());
        telemetry.addData("In R Position", iSlides.motorR.getCurrentPosition());
        telemetry.addData("Out L Position", oSlides.motorL.getCurrentPosition());
        telemetry.addData("Out R Position", oSlides.motorR.getCurrentPosition());

        telemetry.update();
        gamepads.update(gamepad1, gamepad2); // SUPER DUPER IMPORTANT!
    }
}
