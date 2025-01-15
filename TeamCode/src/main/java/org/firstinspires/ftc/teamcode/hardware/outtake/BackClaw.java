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
        CLOSE
    }

    public BackClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "bc", Stage.class)
                .add(Stage.OPEN, 0.35)
                .add(Stage.CLOSE, 0.08)
        );
    }

    public void open()
    {
        setStage(Stage.OPEN);
    }

    public void close()
    {
        setStage(Stage.CLOSE);
    }

    public Action open(int timeInMilliseconds)
    {
        return getTimedAction(this::open, timeInMilliseconds);
    }

    public Action close(int timeInMilliseconds)
    {
        return getTimedAction(this::close, timeInMilliseconds);
    }
}
