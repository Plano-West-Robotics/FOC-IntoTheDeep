package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class FrontClaw extends StageServoMono<FrontClaw.Stage>
{
    public enum Stage
    {
        OPEN, CLOSE
    }

    public FrontClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "fc", Stage.class)
                .add(Stage.OPEN, 0.5)
                .add(Stage.CLOSE, 0.6)
        );
    }
}
