package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontArm;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontClaw;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontSwivel;
import org.firstinspires.ftc.teamcode.hardware.intake.HorizontalExtendo;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackClaw;
import org.firstinspires.ftc.teamcode.wrappers.MotorPair;

public class Intake
{
    public static final int RETRACT_THRESHOLD = 460;
    public static final int EXTEND_THRESHOLD = 520; // Must be greater than RETRACT_THRESHOLD.

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

    public void stopSlides()
    {
        ((MotorPair) extendo).setPower(0);
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

    public void retractArm()
    {
        arm.setStage(FrontArm.Stage.RETRACT);
    }

    public void extendArm()
    {
        arm.setStage(FrontArm.Stage.EXTEND);
    }

    public void probeArm()
    {
        arm.setStage(FrontArm.Stage.PROBE);
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

    public void closeClaw()
    {
        claw.setStage(FrontClaw.Stage.CLOSE);
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
