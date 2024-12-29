package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.Servo;

import java.util.EnumMap;

public class StageServo<T extends Enum<T>> extends Servo
{
    public T stage;
    public EnumMap<T, Double> positionMap;

    public static class StageServoBuilder<T extends Enum<T>>
    {
        public HardwareMap hardwareMap;
        public String name;
        public EnumMap<T, Double> positionMap;

        public StageServoBuilder(HardwareMap hardwareMap, String name, Class<T> enumClass)
        {
            this.hardwareMap = hardwareMap;
            this.name = name;
            this.positionMap = new EnumMap<>(enumClass);
        }

        public StageServoBuilder<T> add(T stage, double position)
        {
            if (position < 0 || position > 1)
                throw new IllegalArgumentException("Servo position values cannot be outside [0, 1].");
            positionMap.put(stage, position);
            return this;
        }

        public StageServo<T> build()
        {
            return new StageServo<>(this);
        }
    }

    public StageServo(StageServoBuilder<T> stageServoBuilder)
    {
        super(stageServoBuilder.hardwareMap, stageServoBuilder.name);
        positionMap = stageServoBuilder.positionMap;
    }

    public T getStage()
    {
        return stage;
    }

    public void setStage(T stage)
    {
        if (!positionMap.containsKey(stage))
        {
            throw new RuntimeException(
                    String.format(
                            "The stage (\"%s\") does not exist in the servo's (\"%s\") positionMap (\"%s\").",
                            stage, servo.getDeviceName(), positionMap
                    )
            );
        }
        double position = positionMap.get(stage);
        setPosition(position);
        this.stage = stage;
    }
}
