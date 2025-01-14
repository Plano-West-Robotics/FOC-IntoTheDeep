package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class FrontClaw extends StageServoMono<FrontClaw.Stage>
{
    public enum Stage
    {
        OPEN,
        LOOSE_CLOSE, // Not maximum clamping force; should allow sample/specimen to slide back and forth.
        CLOSE
    }

    public FrontClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "fc", Stage.class)
                .add(Stage.OPEN, 0.5)
                .add(Stage.LOOSE_CLOSE, 0.34)
                .add(Stage.CLOSE, 0.3)
        );
    }
}
