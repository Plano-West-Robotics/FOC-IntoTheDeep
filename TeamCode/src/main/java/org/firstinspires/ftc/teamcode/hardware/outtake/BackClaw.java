package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackClaw extends StageServoMono<BackClaw.Stage>
{
    public enum Stage
    {
        OPEN,
        CLOSE
    }

    public BackClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "bc", Stage.class)
                .add(Stage.OPEN, 0.35)
                .add(Stage.CLOSE, 0.08)
        );
    }
}
