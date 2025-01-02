package org.firstinspires.ftc.teamcode.hardware.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class FrontSwivel extends StageServoMono<FrontSwivel.Stage>
{
    public enum Stage
    {
        WEST,
        NORTHWEST,
        NORTH,
        NORTHEAST,
        EAST
    }

    public FrontSwivel(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "fs", Stage.class)
                .add(Stage.WEST, 0)
                .add(Stage.NORTHWEST, 0.25)
                .add(Stage.NORTH, 0.5)
                .add(Stage.NORTHEAST, 0.75)
                .add(Stage.EAST, 1)
        );
    }

    public void center()
    {
        setStage(Stage.NORTH);
    }

    public void rotateCCW()
    {
        Stage newStage = switch (getStage())
        {
            case WEST, NORTHWEST -> Stage.WEST;
            case NORTH -> Stage.NORTHWEST;
            case NORTHEAST -> Stage.NORTH;
            case EAST -> Stage.NORTHEAST;
        };
        setStage(newStage);
    }

    public void rotateCW()
    {
        Stage newStage = switch (getStage())
        {
            case WEST -> Stage.NORTHWEST;
            case NORTHWEST -> Stage.NORTH;
            case NORTH -> Stage.NORTHEAST;
            case NORTHEAST, EAST -> Stage.EAST;
        };
        setStage(newStage);
    }
}
