package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSwivel
{
    public static final double MIN_POSITION = 0.17;
    public static final double MAX_POSITION = 0.83;
    public static final double CENTER_POSITION = 0.5;
    public static final double POSITION_INCREMENT = 0.25;

    public Servo servoSwivel;
    public double position;
    public boolean isInitialized; // Ensures the position field is not null when rotCCW() or rotCW() is called

    public IntakeSwivel(HardwareMap hardwareMap)
    {
        servoSwivel = hardwareMap.get(Servo.class, "R2");
        servoSwivel.scaleRange(MIN_POSITION, MAX_POSITION);
        isInitialized = false;
    }

    public void rotCCW()
    {
        if (!isInitialized) center();
        setPosition(Math.min(position + POSITION_INCREMENT, 1));
    }

    public void rotCW()
    {
        if (!isInitialized) center();
        setPosition(Math.max(position - POSITION_INCREMENT, 0));
    }

    public void center()
    {
        setPosition(CENTER_POSITION);
    }

    public double getPosition()
    {
        return position;
    }

    public void setPosition(double newPosition)
    {
        servoSwivel.setPosition(newPosition);
        position = newPosition;
        isInitialized = true; // Because position is not null
    }
}
