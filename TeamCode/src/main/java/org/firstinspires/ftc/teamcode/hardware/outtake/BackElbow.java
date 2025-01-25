package org.firstinspires.ftc.teamcode.hardware.outtake;

import static org.firstinspires.ftc.teamcode.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackElbow extends StageServoMono<BackElbow.Stage>
{
    public enum Stage
    {
        TRANSFER,
        CLIP,
        WALL,
        FRONT_HOOK,
        BUCKET
    }

    public BackElbow(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "be", Stage.class)
                .add(Stage.TRANSFER, 0.37)
                .add(Stage.CLIP, 0.527)
                .add(Stage.WALL, 0.68)
                .add(Stage.FRONT_HOOK, 0.7)
                .add(Stage.BUCKET, 0.925)
        );
    }

    public void transfer()
    {
        setStage(Stage.TRANSFER);
    }

    public void clip()
    {
        setStage(Stage.CLIP);
    }

    public void wall()
    {
        setStage(Stage.WALL);
    }

    public void frontHook()
    {
        setStage(Stage.FRONT_HOOK);
    }

    public void bucket()
    {
        setStage(Stage.BUCKET);
    }

    public Action transfer(int timeInMilliseconds)
    {
        return getTimedAction(this::transfer, timeInMilliseconds);
    }

    public Action clip(int timeInMilliseconds)
    {
        return getTimedAction(this::clip, timeInMilliseconds);
    }

    public Action wall(int timeInMilliseconds)
    {
        return getTimedAction(this::wall, timeInMilliseconds);
    }

    public Action frontHook(int timeInMilliseconds)
    {
        return getTimedAction(this::frontHook, timeInMilliseconds);
    }

    public Action bucket(int timeInMilliseconds)
    {
        return getTimedAction(this::bucket, timeInMilliseconds);
    }
}
