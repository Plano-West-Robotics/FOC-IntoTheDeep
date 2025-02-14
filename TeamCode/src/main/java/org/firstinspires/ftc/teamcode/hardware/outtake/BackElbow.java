package org.firstinspires.ftc.teamcode.hardware.outtake;

import static org.firstinspires.ftc.teamcode.roadrunner.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackElbow extends StageServoMono<BackElbow.Stage>
{
    public enum Stage
    {
        TRANSFER,
        SQUEEZE,
        CLIP,
        WALL,
        BUCKET,
        FRONT_HOOK,
    }

    public BackElbow(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "be", Stage.class)
                .add(Stage.TRANSFER, 0.57)
                .add(Stage.SQUEEZE, 0.5)
                .add(Stage.CLIP, 0.85)
                .add(Stage.WALL, 0.85)
                .add(Stage.BUCKET, 0.85)
                .add(Stage.FRONT_HOOK, 0.92)
        );
    }

    public void transfer()
    {
        setStage(Stage.TRANSFER);
    }

    public void squeeze()
    {
        setStage(Stage.SQUEEZE);
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

    public void frontHook()
    {
        setStage(Stage.FRONT_HOOK);
    }

    public Action transfer(int timeInMilliseconds)
    {
        return getTimedAction(this::transfer, timeInMilliseconds);
    }

    public Action squeeze(int timeInMilliseconds)
    {
        return getTimedAction(this::squeeze, timeInMilliseconds);
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

    public Action frontHook(int timeInMilliseconds)
    {
        return getTimedAction(this::frontHook, timeInMilliseconds);
    }
}
