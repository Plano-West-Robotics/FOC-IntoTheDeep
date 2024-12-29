package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackArm extends StageServoMono<BackArm.Stage>
{
    public enum Stage
    {
        RETRACT, MIDDLE, EXTEND
    }

    public BackArm(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "ba", Stage.class)
                .add(Stage.RETRACT, 0.1)
                .add(Stage.MIDDLE, 0.4)
                .add(Stage.EXTEND, 0.75)
        );
    }
}
