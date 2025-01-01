package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Gamepads
{
    public Gamepad currGP1, prevGP1, currGP2, prevGP2;

    public Gamepads(Gamepad gp1, Gamepad gp2)
    {
        this();
        currGP1.copy(gp1);
        currGP2.copy(gp2);
    }

    public Gamepads()
    {
        prevGP1 = new Gamepad();
        currGP1 = new Gamepad();
        prevGP2 = new Gamepad();
        currGP2 = new Gamepad();
    }

    public boolean justEnteredThreshold(Analog analog, double threshold)
    {
        return (getAnalogValue(analog, currGP1, currGP2) >= threshold)
                && (getAnalogValue(analog, prevGP1, prevGP2) < threshold);
    }

    public boolean justExitedThreshold(Analog analog, double threshold)
    {
        return (getAnalogValue(analog, currGP1, currGP2) <= threshold)
                && (getAnalogValue(analog, prevGP1, prevGP2) > threshold);
    }

    public double getAnalogValue(Analog analog)
    {
        return getAnalogValue(analog, currGP1, currGP2);
    }

    public double getAnalogValue(Analog analog, Gamepad gp1, Gamepad gp2)
    {
        return switch (analog)
        {
            case GP1_LEFT_STICK_X -> gp1.left_stick_x;
            case GP1_LEFT_STICK_Y -> -gp1.left_stick_y;
            case GP1_RIGHT_STICK_X -> gp1.right_stick_x;
            case GP1_RIGHT_STICK_Y -> -gp1.right_stick_y;
            case GP1_LEFT_TRIGGER -> gp1.left_trigger;
            case GP1_RIGHT_TRIGGER -> gp1.right_trigger;

            case GP2_LEFT_STICK_X -> gp2.left_stick_x;
            case GP2_LEFT_STICK_Y -> -gp2.left_stick_y;
            case GP2_RIGHT_STICK_X -> gp2.right_stick_x;
            case GP2_RIGHT_STICK_Y -> -gp2.right_stick_y;
            case GP2_LEFT_TRIGGER -> gp2.left_trigger;
            case GP2_RIGHT_TRIGGER -> gp2.right_trigger;
        };
    }

    public boolean justPressed(Button button)
    {
        return isPressed(button, currGP1, currGP2) && !isPressed(button, prevGP1, prevGP2);
    }

    public boolean justReleased(Button button)
    {
        return !isPressed(button, currGP1, currGP2) && isPressed(button, prevGP1, prevGP2);
    }

    public boolean isPressed(Button button)
    {
        return isPressed(button, currGP1, currGP2);
    }

    public boolean isPressed(Button button, Gamepad gp1, Gamepad gp2)
    {
        return switch (button)
        {
            case GP1_A -> gp1.a;
            case GP1_X -> gp1.x;
            case GP1_B -> gp1.b;
            case GP1_Y -> gp1.y;
            case GP1_LEFT_BUMPER -> gp1.left_bumper;
            case GP1_RIGHT_BUMPER -> gp1.right_bumper;
            case GP1_LEFT_STICK_BUTTON -> gp1.left_stick_button;
            case GP1_RIGHT_STICK_BUTTON -> gp1.right_stick_button;
            case GP1_DPAD_UP -> gp1.dpad_up;
            case GP1_DPAD_DOWN -> gp1.dpad_down;
            case GP1_DPAD_LEFT -> gp1.dpad_left;
            case GP1_DPAD_RIGHT -> gp1.dpad_right;

            case GP2_A -> gp2.a;
            case GP2_X -> gp2.x;
            case GP2_B -> gp2.b;
            case GP2_Y -> gp2.y;
            case GP2_LEFT_BUMPER -> gp2.left_bumper;
            case GP2_RIGHT_BUMPER -> gp2.right_bumper;
            case GP2_LEFT_STICK_BUTTON -> gp2.left_stick_button;
            case GP2_RIGHT_STICK_BUTTON -> gp2.right_stick_button;
            case GP2_DPAD_UP -> gp2.dpad_up;
            case GP2_DPAD_DOWN -> gp2.dpad_down;
            case GP2_DPAD_LEFT -> gp2.dpad_left;
            case GP2_DPAD_RIGHT -> gp2.dpad_right;
        };
    }

    public void update(Gamepad gp1, Gamepad gp2)
    {
        prevGP1.copy(currGP1);
        currGP1.copy(gp1);
        prevGP2.copy(currGP2);
        currGP2.copy(gp2);
    }
}