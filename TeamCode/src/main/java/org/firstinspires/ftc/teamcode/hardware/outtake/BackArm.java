package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackArm extends StageServoMono<BackArm.Stage>
{
    public enum Stage
    {
        RETRACT, // Above the two C-channels that house the outtake arm.
        PERPENDICULAR, // 180 degrees from RETRACT.
        EXTEND // 45 degrees beyond PERPENDICULAR; position for scoring.
    }

    public BackArm(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "ba", Stage.class)
                .add(Stage.RETRACT, 0.123)
                .add(Stage.PERPENDICULAR, 0.456)
                .add(Stage.EXTEND, 0.789)
        );
    }
}
