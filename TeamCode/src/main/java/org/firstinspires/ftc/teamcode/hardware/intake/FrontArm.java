package org.firstinspires.ftc.teamcode.hardware.intake;

import static org.firstinspires.ftc.teamcode.roadrunner.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class FrontArm extends StageServoPair<FrontArm.Stage>
{
    public static final double POSITION_DIFF = 0.02;

    public enum Stage
    {
        PROBE, // Parallel to ground; arm is outside of robot.
        EXTEND, // A little above parallel to ground; arm is outside of robot.
        EXTEND_EXPERIMENTAL,
        UPRIGHT, // Perpendicular to ground.
        RETRACT, // Parallel to ground; arm is within the robot.
    }

    public FrontArm(HardwareMap hardwareMap)
    {
        super (new StageServoPairBuilder<>
                (hardwareMap, "fal", "far", Stage.class, POSITION_DIFF)
                .add(Stage.PROBE, 0.09)
                .add(Stage.EXTEND, 0.25)
                .add(Stage.EXTEND_EXPERIMENTAL, 0.23)
                .add(Stage.UPRIGHT, 0.46)
                .add(Stage.RETRACT, 0.8)
        );
    }

    public void probe()
    {
        setStage(Stage.PROBE);
    }

    public void extend()
    {
        setStage(Stage.EXTEND);
    }

    public void extendExperimental()
    {
        setStage(Stage.EXTEND_EXPERIMENTAL);
    }

    public void upright()
    {
        setStage(Stage.UPRIGHT);
    }

    public void retract()
    {
        setStage(Stage.RETRACT);
    }

    public Action probe(int timeInMilliseconds)
    {
        return getTimedAction(this::probe, timeInMilliseconds);
    }

    public Action extend(int timeInMilliseconds)
    {
        return getTimedAction(this::extend, timeInMilliseconds);
    }

    public Action extendExperimental(int timeInMilliseconds)
    {
        return getTimedAction(this::extendExperimental, timeInMilliseconds);
    }

    public Action upright(int timeInMilliseconds)
    {
        return getTimedAction(this::upright, timeInMilliseconds);
    }

    public Action retract(int timeInMilliseconds)
    {
        return getTimedAction(this::retract, timeInMilliseconds);
    }
}
