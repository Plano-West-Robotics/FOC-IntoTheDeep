package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.control.PowerManager;
import org.firstinspires.ftc.teamcode.helpers.SlideUtils;

public class OuttakeSlides
{
    public static final double MAX_POSITION = 3750;
    public static final double POSITION_BUFFER = 100;
    public static final double SPEED_MULTIPLIER = 0.65;
    public static final double GRAVITY_FEEDFORWARD = 0.0006;

    public static final int SLOWDOWN = 200;
    public static final int ERROR_BUFFER = 30;
    public static final double MIN_POWER = 0.2; // Has to be greater than 0
    public static final double MAX_POWER = 0.8;

    public static final int REST_HEIGHT = 0;
    public static final int L_CHAMBER_HEIGHT = 670;
    public static final int L_BASKET_HEIGHT = 1600;
    public static final int H_CHAMBER_HEIGHT = 2070;
    public static final int H_BASKET_HEIGHT = 3720;

    public DcMotor motorL, motorR;
    public PowerManager powerManager;
    public Height height;
    public boolean inMotion;

    public enum Height
    {
        UNINITIALIZED,
        REST,
        L_CHAMBER,
        L_BASKET,
        H_CHAMBER,
        H_BASKET
    }

    public OuttakeSlides(HardwareMap hardwareMap)
    {
        motorL = hardwareMap.get(DcMotor.class, "oSlideL");
        motorR = hardwareMap.get(DcMotor.class, "oSlideR");

        motorL.setDirection(DcMotorSimple.Direction.REVERSE);

        motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorL.setPower(0);
        motorR.setPower(0);

        height = Height.UNINITIALIZED;
        inMotion = false;
    }

    public boolean isExtended()
    {
        return Math.round((motorL.getCurrentPosition() + motorR.getCurrentPosition()) / 2.0) >= MAX_POSITION - POSITION_BUFFER;
    }

    public boolean isRetracted()
    {
        return Math.round((motorL.getCurrentPosition() + motorR.getCurrentPosition()) / 2.0) <= POSITION_BUFFER;
    }

    public boolean isDangerousInput(double input)
    {
        return (isExtended() && input > 0) || (isRetracted() && input < 0);
    }

    public void setHeight(Height newHeight)
    {
        if (newHeight == height || newHeight == Height.UNINITIALIZED) return;

        int target = getHeightInTicks(newHeight);
        int initialError = Math.abs(target - SlideUtils.getAveragePosition(motorL, motorR));

        powerManager = new PowerManager(SLOWDOWN, initialError, MIN_POWER, MAX_POWER);

        motorL.setTargetPosition(target);
        motorR.setTargetPosition(target);

        motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        height = newHeight;
        inMotion = true;
    }

    public void updatePower()
    {
        int currError = Math.abs(getHeightInTicks(height) - SlideUtils.getAveragePosition(motorL, motorR));
        double power = powerManager.getPower(currError);

        if (!inMotion) return;
        if (currError <= ERROR_BUFFER)
        {
            motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            setPower(0);
            inMotion = false;
            return;
        }

        motorL.setPower(power);
        motorR.setPower(power);
    }

    public int getHeightInTicks(Height height)
    {
        switch (height)
        {
            case REST: return REST_HEIGHT;
            case L_CHAMBER: return L_CHAMBER_HEIGHT;
            case L_BASKET: return L_BASKET_HEIGHT;
            case H_CHAMBER: return H_CHAMBER_HEIGHT;
            case H_BASKET: return H_BASKET_HEIGHT;
            default: throw new RuntimeException("Unreachable code entered");
        }
    }

    public void incrementHeight()
    {
        switch (height)
        {
            case REST:
                setHeight(Height.L_CHAMBER);
                break;
            case L_CHAMBER:
                setHeight(Height.L_BASKET);
                break;
            case L_BASKET:
                setHeight(Height.H_CHAMBER);
                break;
            case H_CHAMBER:
                setHeight(Height.H_BASKET);
                break;
        }
    }

    public void decrementHeight()
    {
        switch (height)
        {
            case L_CHAMBER:
                setHeight(Height.REST);
                break;
            case L_BASKET:
                setHeight(Height.L_CHAMBER);
                break;
            case H_CHAMBER:
                setHeight(Height.L_BASKET);
                break;
            case H_BASKET:
                setHeight(Height.H_CHAMBER);
                break;
        }
    }

    public void setPower(double input)
    {
        motorL.setPower(input * SPEED_MULTIPLIER + GRAVITY_FEEDFORWARD);
        motorR.setPower(input * SPEED_MULTIPLIER + GRAVITY_FEEDFORWARD);
    }
}
