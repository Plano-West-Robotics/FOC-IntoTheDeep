package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class BackArm extends StageServoPair<BackArm.Stage>
{
    public static final double POSITION_DIFF = 0; // Not a placeholder.

    public enum Stage
    {
        BUCKET, // From above.
        REST, // Perpendicular to ground.
        TRANSFER,
        CLIP // From under.
    }

    public BackArm(HardwareMap hardwareMap)
    {
        super(new StageServoPair.StageServoPairBuilder<>(hardwareMap, "bal",
                "bar", Stage.class, POSITION_DIFF)
                .add(Stage.BUCKET, 0.03)
                .add(Stage.REST, 0.314)
                .add(Stage.TRANSFER, 0.485)
                .add(Stage.CLIP, 0.975)
        );
    }
}
