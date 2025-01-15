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
        BUCKET
    }

    public BackElbow(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "be", Stage.class)
                .add(Stage.TRANSFER, 0.37)
                .add(Stage.CLIP, 0.527)
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

    public Action bucket(int timeInMilliseconds)
    {
        return getTimedAction(this::bucket, timeInMilliseconds);
    }
}
