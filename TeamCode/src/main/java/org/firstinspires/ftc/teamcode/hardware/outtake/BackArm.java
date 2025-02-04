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
        BUCKET, // From above.
        REST, // Perpendicular to ground.
        TRANSFER,
        CLIP, // From under.
        WALL,
        HOOK_UP
    }

    public BackArm(HardwareMap hardwareMap)
    {
        super(new StageServoPairBuilder<>(hardwareMap, "bal",
                "bar", Stage.class, POSITION_DIFF)
                .add(Stage.BUCKET, 0.03)
                .add(Stage.REST, 0.16)
                .add(Stage.TRANSFER, 0.46)
                .add(Stage.CLIP, 0.975)
                .add(Stage.WALL, 1)
                .add(Stage.HOOK_UP, 0.4)
        );
    }

    public void bucket()
    {
        setStage(Stage.BUCKET);
    }

    public void rest()
    {
        setStage(Stage.REST);
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

    public void hookUp()
    {
        setStage(Stage.HOOK_UP);
    }

    public Action bucket(int timeInMilliseconds)
    {
        return getTimedAction(this::bucket, timeInMilliseconds);
    }

    public Action rest(int timeInMilliseconds)
    {
        return getTimedAction(this::rest, timeInMilliseconds);
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

    public Action hookUp(int timeInMilliseconds)
    {
        return getTimedAction(this::hookUp, timeInMilliseconds);
    }
}
