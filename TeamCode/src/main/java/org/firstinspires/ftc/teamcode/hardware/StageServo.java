package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.Servo;

import java.util.EnumMap;

public class StageServo<T extends Enum<T>> extends Servo
{
    public EnumMap<T, Double> positionMap;

    public StageServo(StageServoBuilder<T> stageServoBuilder)
    {
        super(stageServoBuilder.hardwareMap, stageServoBuilder.name);
        positionMap = stageServoBuilder.positionMap;
    }

    public void setPosition(T stage)
    {
        if (!positionMap.containsKey(stage))
            throw new RuntimeException("Tried to index positionMap with a nonexistent key.");
        double position = positionMap.get(stage);
        setPosition(position);
    }

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
}
