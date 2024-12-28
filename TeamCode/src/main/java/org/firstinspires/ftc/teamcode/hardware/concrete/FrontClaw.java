package org.firstinspires.ftc.teamcode.hardware.concrete;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.StageServo;

public class FrontClaw extends StageServo<FrontClaw.Stage>
{
    public enum Stage
    {
        OPEN, CLOSE
    }

    public FrontClaw(HardwareMap hardwareMap)
    {
        super(
                new StageServoBuilder<>(hardwareMap, "fc", Stage.class)
                        .add(Stage.OPEN, 0.5)
                        .add(Stage.CLOSE, 0.6)
        );
    }
}
