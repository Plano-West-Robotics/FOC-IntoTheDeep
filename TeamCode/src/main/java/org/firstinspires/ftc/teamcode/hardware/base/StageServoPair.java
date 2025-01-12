package org.firstinspires.ftc.teamcode.hardware.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.ServoPair;

import java.util.EnumMap;

public abstract class StageServoPair<T extends Enum<T>> extends StageServo<T>
{
    public ServoPair inner;

    public static class StageServoPairBuilder<T extends Enum<T>>
    {
        public HardwareMap hardwareMap;
        public String leftServoName, rightServoName;
        public EnumMap<T, Double> positionMap;
        public double positionDiff;

        public StageServoPairBuilder(HardwareMap hardwareMap, String leftServoName,
                                     String rightServoName, Class<T> enumClass, double positionDiff)
        {
            this.hardwareMap = hardwareMap;
            this.leftServoName = leftServoName;
            this.rightServoName = rightServoName;
            this.positionMap = new EnumMap<>(enumClass);
            this.positionDiff = positionDiff;
        }

        public StageServoPairBuilder<T> add(T stage, double leftServoPosition)
        {
            if (leftServoPosition < 0 || leftServoPosition > 1)
                throw new IllegalArgumentException(
                        "Servo position values cannot be outside the range [0, 1]."
                );
            positionMap.put(stage, leftServoPosition);
            return this;
        }
    }

    public StageServoPair(StageServoPairBuilder<T> builder)
    {
        super(builder.positionMap);
        inner = new ServoPair(
                builder.hardwareMap, builder.leftServoName, builder.rightServoName,
                builder.positionDiff
        );
    }

    public ServoPair getServoPair()
    {
        return inner;
    }

    @Override
    public void setPosition(double position)
    {
        inner.setPosition(position);
    }
}