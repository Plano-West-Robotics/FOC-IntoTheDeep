package org.firstinspires.ftc.teamcode.hardware.base;

import java.util.EnumMap;

public abstract class StageServo<T extends Enum<T>>
{
    public EnumMap<T, Double> positionMap;
    public T stage;

    public StageServo(EnumMap<T, Double> positionMap)
    {
        this.positionMap = positionMap;
    }

    public T getStage()
    {
        return stage;
    }

    public void setStage(T stage)
    {
        if (!positionMap.containsKey(stage))
            throw new RuntimeException(
                    String.format(
                            "The stage (\"%s\") does not exist in the positionMap: %n%s",
                            stage, positionMap
                    )
            );
        double position = positionMap.get(stage);
        applyPosition(position);
        this.stage = stage;
    }

    public abstract void applyPosition(double position);
}
