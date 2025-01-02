package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class FrontClaw extends StageServoMono<FrontClaw.Stage>
{
    public enum Stage
    {
        OPEN,
        CLOSE // Not maximum clamping force; should allow sample to slide back and forth.
    }

    public FrontClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "fc", Stage.class)
                .add(Stage.OPEN, 0.123)
                .add(Stage.CLOSE, 0.456)
        );
    }
}
