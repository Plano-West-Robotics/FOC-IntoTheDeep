package org.firstinspires.ftc.teamcode.hardware.outtake;

import static org.firstinspires.ftc.teamcode.Utils.getTimedAction;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackClaw extends StageServoMono<BackClaw.Stage>
{
    public enum Stage
    {
        OPEN,
        BUCKET_OPEN,
        CLOSE
    }

    public BackClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "bc", Stage.class)
                .add(Stage.OPEN, 0.35)
                .add(Stage.BUCKET_OPEN, 0.42)
                .add(Stage.CLOSE, 0.51)
        );
    }

    public void open()
    {
        setStage(Stage.OPEN);
    }

    public void bucketOpen()
    {
        setStage(Stage.BUCKET_OPEN);
    }

    public void close()
    {
        setStage(Stage.CLOSE);
    }

    public Action open(int timeInMilliseconds)
    {
        return getTimedAction(this::open, timeInMilliseconds);
    }

    public Action bucketOpen(int timeInMilliseconds)
    {
        return getTimedAction(this::bucketOpen, timeInMilliseconds);
    }

    public Action close(int timeInMilliseconds)
    {
        return getTimedAction(this::close, timeInMilliseconds);
    }
}
