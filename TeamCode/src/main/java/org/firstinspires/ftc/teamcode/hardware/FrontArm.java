package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class FrontArm extends StageServoPair<FrontArm.Stage>
{
    public enum Stage
    {
        RETRACT, HOVER, EXTEND
    }

    public FrontArm(HardwareMap hardwareMap)
    {
        super(new StageServoPairBuilder<>
                (hardwareMap, "fal", "far",Stage.class)
                .add(Stage.RETRACT, 0.54)
                .add(Stage.EXTEND, 0.77)
                .add(Stage.HOVER, 0.6)
        );
    }
}
