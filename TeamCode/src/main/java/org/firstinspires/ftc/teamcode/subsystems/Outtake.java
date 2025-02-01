package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackClaw;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackElbow;
import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;

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

    public void updateClaw(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP2_X))
        {
            if (arm.atStage(BackArm.Stage.BUCKET))
            {
                if (claw.atStage(BackClaw.Stage.BUCKET_OPEN)) claw.close();
                else if (claw.atStage(BackClaw.Stage.CLOSE)) claw.bucketOpen();
            }

            else
            {
                if (claw.atStage(BackClaw.Stage.OPEN)) claw.close();
                else if (claw.atStage(BackClaw.Stage.CLOSE)) claw.open();
            }
        }
    }

    public VerticalExtendo getExtendo()
    {
        return extendo;
    }

    public BackArm getArm()
    {
        return arm;
    }

    public BackElbow getElbow()
    {
        return elbow;
    }

    public BackClaw getClaw()
    {
        return claw;
    }
}
