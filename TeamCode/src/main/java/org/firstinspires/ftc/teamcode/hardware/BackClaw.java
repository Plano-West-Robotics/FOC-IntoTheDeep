package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackClaw extends StageServoMono<BackClaw.Stage>
{
    public enum Stage
    {
        OPEN, CLOSE, CLAMP
    }

    public BackClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "bc", Stage.class)
                .add(Stage.OPEN, 0.54)
                .add(Stage.CLOSE, 0.77)
                .add(Stage.CLAMP, 0.82)
        );
    }
}
