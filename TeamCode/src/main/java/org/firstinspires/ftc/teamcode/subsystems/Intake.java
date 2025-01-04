package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontArm;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontClaw;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontSwivel;
import org.firstinspires.ftc.teamcode.hardware.intake.HorizontalExtendo;

public class Intake
{
    // TeleOp HorizontalExtendo parameters.
    public static final int RETRACT_THRESHOLD = 460;
    public static final int EXTEND_THRESHOLD = 520; // Must be greater than RETRACT_THRESHOLD.

    // Auto HorizontalExtendo parameters.
    public static final int RETRACT_POSITION = 200;
    public static final int EXTEND_POSITION = 800;

    public HorizontalExtendo extendo;
    public FrontArm arm;
    public FrontClaw claw;
    public FrontSwivel swivel;

    public Intake(Hardware hardware)
    {
        extendo = hardware.horizontalExtendo;
        arm = hardware.frontArm;
        claw = hardware.frontClaw;
        swivel = hardware.frontSwivel;
    }

    public void updateExtendoPower(Gamepads gamepads)
    {
        extendo.setPower(gamepads.getAnalogValue(Analog.GP2_LEFT_STICK_Y));
    }

    public void haltSlides()
    {
        extendo.setPower(0, false);
    }

    public Action retractSlides()
    {
        return extendo.getSlideAction(RETRACT_POSITION);
    }

    public Action extendSlides()
    {
        return extendo.getSlideAction(EXTEND_POSITION);
    }

    public boolean inRetractZone()
    {
        return extendo.getAveragePosition() < RETRACT_THRESHOLD;
    }

    public boolean inExtendZone()
    {
        return extendo.getAveragePosition() > EXTEND_THRESHOLD;
    }

    public void clearArm()
    {
        arm.setStage(FrontArm.Stage.CLEAR);
    }

    public Action clearArm(int timeInMilliseconds)
    {
        return getTimedAction(this::clearArm, timeInMilliseconds);
    }

    public void retractArm()
    {
        arm.setStage(FrontArm.Stage.RETRACT);
    }

    public Action retractArm(int timeInMilliseconds)
    {
        return getTimedAction(this::retractArm, timeInMilliseconds);
    }

    public void extendArm()
    {
        arm.setStage(FrontArm.Stage.EXTEND);
    }

    public Action extendArm(int timeInMilliseconds)
    {
        return getTimedAction(this::extendArm, timeInMilliseconds);
    }

    public void probeArm()
    {
        arm.setStage(FrontArm.Stage.PROBE);
    }

    public Action probeArm(int timeInMilliseconds)
    {
        return getTimedAction(this::probeArm, timeInMilliseconds);
    }

    public void updateClaw(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP2_X))
        {
            if (clawIsOpen()) closeClaw();
            else openClaw();
        }
    }

    public void openClaw()
    {
        claw.setStage(FrontClaw.Stage.OPEN);
    }

    public Action openClaw(int timeInMilliseconds)
    {
        return getTimedAction(this::openClaw, timeInMilliseconds);
    }

    public void closeClaw()
    {
        claw.setStage(FrontClaw.Stage.CLOSE);
    }

    public Action closeClaw(int timeInMilliseconds)
    {
        return getTimedAction(this::closeClaw, timeInMilliseconds);
    }

    public boolean clawIsOpen()
    {
        return claw.isStage(FrontClaw.Stage.OPEN);
    }

    public boolean clawIsClosed()
    {
        return claw.isStage(FrontClaw.Stage.CLOSE);
    }

    public void updateSwivel(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP2_LEFT_BUMPER)) swivel.rotateCCW();
        else if (gamepads.justPressed(Button.GP2_RIGHT_BUMPER)) swivel.rotateCW();
    }

    public void centerSwivel()
    {
        swivel.center();
    }
}
