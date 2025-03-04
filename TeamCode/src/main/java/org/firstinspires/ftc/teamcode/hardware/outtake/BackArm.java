package org.firstinspires.ftc.teamcode.hardware.outtake;

import static org.firstinspires.ftc.teamcode.roadrunner.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoPair;

public class BackArm extends StageServoPair<BackArm.Stage>
{
    public static final double POSITION_DIFF = 0; // Not a placeholder.

    public enum Stage
    {
        REST, // Perpendicular to ground.
        TRANSFER,
        HOOK_UP, // (Almost} parallel to the ground.,
        CLIP,
        WALL,
        BUCKET
    }

    public BackArm(HardwareMap hardwareMap)
    {
        super(new StageServoPairBuilder<>(hardwareMap, "bal",
                "bar", Stage.class, POSITION_DIFF)
                .add(Stage.REST, 0)
                .add(Stage.TRANSFER, 0.33)
                .add(Stage.HOOK_UP, 0.33)
                .add(Stage.CLIP, 0.83)
                .add(Stage.WALL, 0.83)
                .add(Stage.BUCKET, 0.99)
        );
    }

    public void rest()
    {
        setStage(Stage.REST);
    }

    public void transfer()
    {
        setStage(Stage.TRANSFER);
    }

    public void hookUp()
    {
        setStage(Stage.HOOK_UP);
    }

    public void clip()
    {
        setStage(Stage.CLIP);
    }

    public void wall()
    {
        setStage(Stage.WALL);
    }

    public void bucket()
    {
        setStage(Stage.BUCKET);
    }

    public Action rest(int timeInMilliseconds)
    {
        return getTimedAction(this::rest, timeInMilliseconds);
    }

    public Action transfer(int timeInMilliseconds)
    {
        return getTimedAction(this::transfer, timeInMilliseconds);
    }

    public Action hookUp(int timeInMilliseconds)
    {
        return getTimedAction(this::hookUp, timeInMilliseconds);
    }

    public Action clip(int timeInMilliseconds)
    {
        return getTimedAction(this::clip, timeInMilliseconds);
    }

    public Action wall(int timeInMilliseconds)
    {
        return getTimedAction(this::wall, timeInMilliseconds);
    }

    public Action bucket(int timeInMilliseconds)
    {
        return getTimedAction(this::bucket, timeInMilliseconds);
    }
}
