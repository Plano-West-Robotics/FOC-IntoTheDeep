package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BooleanLogic {

    public static final double L_STICK_THRESH = 0.1;
    public static final int MIN_SLIDE_ENCODER_VAL = 400;

    public BooleanLogic() {
    }

    /**
     * Checks whether to trigger the auto retract sequence
     * @param lStickY the Y-Axis of the 2nd gamepad's left stick
     * @param motorL the left side motor of the intake linear slide
     * @return whether the stick is being moved downwards and whether the motors are close to the starting position
     */
    public boolean triggerAutoRetract(double lStickY, DcMotor motorL)
    {
        return (lStickY >= L_STICK_THRESH) && (motorL.getCurrentPosition() <= MIN_SLIDE_ENCODER_VAL);
    }

    /**
     * Checks whether to automatically extend the intake arm
     * @param lStickY the Y-Axis of the 2nd gamepad's left stick
     * @param motorL the left side motor of the intake linear slide
     * @return whether the stick is being moved upwards and whether the motors are a certain amount of ticks away from the starting position
     */
    public boolean triggerAutoExtend(double lStickY, DcMotor motorL)
    {
        return (lStickY <= (-1 * L_STICK_THRESH)) && (motorL.getCurrentPosition() >= MIN_SLIDE_ENCODER_VAL);
    }

    /**
     * Checks whether to trigger the next part of the automatic sequence based on the time since the last reset
     * @param timeInMilliseconds the time threshold that needs to be reached
     * @param pastBool whether the boolean from the last part of the sequence has been activated
     * @param timer the running timer that was reset at the beginning of the pipeline
     * @return whether the boolean from the last part is true and if the time threshold was passed
     */
    public boolean timerStageMS(double timeInMilliseconds, boolean pastBool, ElapsedTime timer)
    {
        return (pastBool && (timer.milliseconds() > timeInMilliseconds));
    }

    public void switchAndSetNext(boolean pastBoolean, boolean nextBoolean)
    {
        pastBoolean = false;
        nextBoolean = true;
    }

    public boolean stickIsPositive(double lJoystick)
    {
        return (lJoystick < 0);
    }

    public boolean motorMinPosReached(DcMotor motL, DcMotor motR)
    {
        return (motL.getCurrentPosition() <= 0 || motR.getCurrentPosition() <= 0);
    }

    public boolean motorMaxPosReached(DcMotor motL, DcMotor motR, double MAX_POSITION)
    {
        return (motL.getCurrentPosition() >= MAX_POSITION || motR.getCurrentPosition() >= MAX_POSITION);
    }
}
