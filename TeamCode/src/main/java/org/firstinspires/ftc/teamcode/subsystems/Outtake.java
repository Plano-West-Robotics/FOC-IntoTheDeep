package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackClaw;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackElbow;
import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;

public class Outtake
{
    // Auto VerticalExtendo parameters.
    public static final int LB_POSITION = 123; // Low basket.
    public static final int HB_POSITION = 234; // High basket.
    public static final int LC_POSITION = 345; // Low chamber.
    public static final int HC_POSITION = 456; // High chamber.
    public static final int LR_POSITION = 567; // Low rung.
    public static final int HR_POSITION = 678; // High rung.
    public static final int POSITION_ERROR_MARGIN = 35;

    public VerticalExtendo extendo;
    public BackArm arm;
    public BackElbow elbow;
    public BackClaw claw;

    public Outtake(Hardware hardware)
    {
        extendo = hardware.verticalExtendo;
        arm = hardware.backArm;
        elbow = hardware.backElbow;
        claw = hardware.backClaw;
    }

    public void updateExtendoPower(Gamepads gamepads)
    {
        extendo.setPower(gamepads.getAnalogValue(Analog.GP2_LEFT_STICK_Y));
    }

    public void haltSlides()
    {
        extendo.setPower(0, false);
    }

    public Action slidesToLowBasket()
    {
        return extendo.getSlideAction(LB_POSITION, POSITION_ERROR_MARGIN);
    }

    public Action slidesToHighBasket()
    {
        return extendo.getSlideAction(HB_POSITION, POSITION_ERROR_MARGIN);
    }

    public Action slidesToLowChamber()
    {
        return extendo.getSlideAction(LC_POSITION, POSITION_ERROR_MARGIN);
    }

    public Action slidesToHighChamber()
    {
        return extendo.getSlideAction(HC_POSITION, POSITION_ERROR_MARGIN);
    }

    public Action slidesToLowRung()
    {
        return extendo.getSlideAction(LR_POSITION, POSITION_ERROR_MARGIN);
    }

    public Action slidesToHighRung()
    {
        return extendo.getSlideAction(HR_POSITION, POSITION_ERROR_MARGIN);
    }

    public void retractArm()
    {
        arm.setStage(BackArm.Stage.RETRACT);
    }

    public Action retractArm(int timeInMilliseconds)
    {
        return getTimedAction(this::retractArm, timeInMilliseconds);
    }

    public void handoverArm()
    {
        arm.setStage(BackArm.Stage.HANDOVER);
    }

    public Action handoverArm(int timeInMilliseconds)
    {
        return getTimedAction(this::handoverArm, timeInMilliseconds);
    }

    public void extendArm()
    {
        arm.setStage(BackArm.Stage.EXTEND);
    }

    public Action extendArm(int timeInMilliseconds)
    {
        return getTimedAction(this::extendArm, timeInMilliseconds);
    }

    public void restElbow()
    {
        elbow.setStage(BackElbow.Stage.REST);
    }

    public Action restElbow(int timeInMilliseconds)
    {
        return getTimedAction(this::restElbow, timeInMilliseconds);
    }

    public void scoreElbow()
    {
        elbow.setStage(BackElbow.Stage.SCORE);
    }

    public Action scoreElbow(int timeInMilliseconds)
    {
        return getTimedAction(this::scoreElbow, timeInMilliseconds);
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
        claw.setStage(BackClaw.Stage.OPEN);
    }

    public Action openClaw(int timeInMilliseconds)
    {
        return getTimedAction(this::openClaw, timeInMilliseconds);
    }

    public void closeClaw()
    {
        claw.setStage(BackClaw.Stage.CLOSE);
    }

    public Action closeClaw(int timeInMilliseconds)
    {
        return getTimedAction(this::closeClaw, timeInMilliseconds);
    }

    public boolean clawIsOpen()
    {
        return claw.isStage(BackClaw.Stage.OPEN);
    }

    public boolean clawIsClosed()
    {
        return claw.isStage(BackClaw.Stage.CLOSE);
    }
}
