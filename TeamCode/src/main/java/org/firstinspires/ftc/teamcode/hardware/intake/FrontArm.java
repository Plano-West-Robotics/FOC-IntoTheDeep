package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class FrontArm extends StageServoPair<FrontArm.Stage>
{
    public static final double POSITION_DIFF = 0.02;

    public enum Stage
    {
        PROBE, // Parallel to ground; arm is outside of robot.
        EXTEND, // A little above parallel to ground; arm is outside of robot.
        UPRIGHT, // Perpendicular to ground.
        RETRACT, // Parallel to ground; arm is within the robot.
        CLEAR, // A little below parallel to ground; arm is within the robot.
    }

    public FrontArm(HardwareMap hardwareMap)
    {
        super(new StageServoPairBuilder<>
                (hardwareMap, "fal", "far", Stage.class, POSITION_DIFF)
                .add(Stage.PROBE, 0.11)
                .add(Stage.EXTEND, 0.25)
                .add(Stage.UPRIGHT, 0.46)
                .add(Stage.RETRACT, 0.79)
                .add(Stage.CLEAR, 0.84)
        );
    }
}
