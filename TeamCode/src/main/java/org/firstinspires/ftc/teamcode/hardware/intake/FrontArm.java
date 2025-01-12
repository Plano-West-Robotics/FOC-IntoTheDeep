package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class FrontArm extends StageServoPair<FrontArm.Stage>
{
    public static final double POSITION_DIFF = 0.02;

    public enum Stage
    {
        CLEAR, // Below parallel with the ground; arm makes clearance for outtake.
        RETRACT, // Parallel with ground; arm is on the side within the robot's space.
        EXTEND, // Outside the robot's space and a little above the ground (maybe 15 degrees?).
        PROBE // Parallel with ground; arm is on the side beyond the robot's space.
    }

    public FrontArm(HardwareMap hardwareMap)
    {
        super(new StageServoPairBuilder<>
                (hardwareMap, "fal", "far", Stage.class, POSITION_DIFF)
                .add(Stage.CLEAR, 0.123)
                .add(Stage.RETRACT, 0.456)
                .add(Stage.EXTEND, 0.789)
                .add(Stage.PROBE, 0.890)
        );
    }
}
