package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class FrontArm extends StageServoPair<FrontArm.Stage>
{
    public enum Stage
    {
        RETRACT, // Parallel with ground; arm is tucked into robot.
        EXTEND, // A little above the ground; maybe 15 degrees?
        PROBE // Parallel with ground; arm is extend out of robot.
    }

    public FrontArm(HardwareMap hardwareMap)
    {
        super(new StageServoPairBuilder<>
                (hardwareMap, "fal", "far", Stage.class)
                .add(Stage.RETRACT, 0.123)
                .add(Stage.EXTEND, 0.456)
                .add(Stage.PROBE, 0.789)
        );
    }
}
