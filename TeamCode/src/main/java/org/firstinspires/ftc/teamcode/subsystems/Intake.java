package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontArm;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontClaw;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontSwivel;
import org.firstinspires.ftc.teamcode.hardware.intake.HorizontalExtendo;

public class Intake
{
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

    public void updateExtendoPowerExperimental(Gamepads gamepads)
    {
        extendo.setPower(gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_Y));
    }

    public void updateClaw(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP2_X))
        {
            if (claw.atStage(FrontClaw.Stage.OPEN)) claw.close();
            else if (claw.atStage(FrontClaw.Stage.CLOSE)) claw.open();
        }
    }

    public void updateSwivel(Gamepads gamepads)
    {
        if (gamepads.justPressed(Button.GP2_LEFT_BUMPER)) swivel.rotateCCW();
        else if (gamepads.justPressed(Button.GP2_RIGHT_BUMPER)) swivel.rotateCW();
    }

    public HorizontalExtendo getExtendo()
    {
        return extendo;
    }

    public FrontArm getArm()
    {
        return arm;
    }

    public FrontClaw getClaw()
    {
        return claw;
    }

    public FrontSwivel getSwivel()
    {
        return swivel;
    }
}
