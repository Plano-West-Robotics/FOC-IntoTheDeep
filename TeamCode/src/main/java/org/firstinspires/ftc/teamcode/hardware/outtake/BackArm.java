package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class BackArm extends StageServoPair<BackArm.Stage>
{
    public enum Stage
    {
        RETRACT, // Rest position; 90 degrees above HANDOVER.
        HANDOVER, // Position when transferring a sample.
        EXTEND // Position when scoring a sample/specimen; 135 degrees below HANDOVER.
    }

    public BackArm(HardwareMap hardwareMap)
    {
        super(new StageServoPair.StageServoPairBuilder<>(hardwareMap, "bal",
                "bar", Stage.class, 0)
                .add(Stage.RETRACT, 0.123)
                .add(Stage.HANDOVER, 0.456)
                .add(Stage.EXTEND, 0.789)
        );
    }
}
