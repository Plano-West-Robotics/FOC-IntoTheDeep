package org.firstinspires.ftc.teamcode.hardware.concrete;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.StageServo;

public class BackClaw extends StageServo<BackClaw.Stage>
{
    public enum Stage
    {
        OPEN, CLOSE
    }

    public BackClaw(HardwareMap hardwareMap)
    {
        super(
                new StageServoBuilder<>(hardwareMap, "bc", Stage.class)
                        .add(Stage.OPEN, 0.7)
                        .add(Stage.CLOSE, 0.8)
        );
    }
}
