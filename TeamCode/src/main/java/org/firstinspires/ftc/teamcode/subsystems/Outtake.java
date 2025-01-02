package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackClaw;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackElbow;
import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;
import org.firstinspires.ftc.teamcode.wrappers.MotorPair;

public class Outtake
{
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

    public void stopSlides()
    {
        ((MotorPair) extendo).setPower(0);
    }

    public void retractArm()
    {
        arm.setStage(BackArm.Stage.RETRACT);
    }

    public void handoverArm()
    {
        arm.setStage(BackArm.Stage.HANDOVER);
    }

    public void extendArm()
    {
        arm.setStage(BackArm.Stage.EXTEND);
    }

    public void restElbow()
    {
        elbow.setStage(BackElbow.Stage.REST);
    }

    public void scoreElbow()
    {
        elbow.setStage(BackElbow.Stage.SCORE);
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

    public void closeClaw()
    {
        claw.setStage(BackClaw.Stage.CLOSE);
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
