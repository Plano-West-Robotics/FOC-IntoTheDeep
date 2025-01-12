package org.firstinspires.ftc.teamcode.hardware.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wrappers.Servo;

import java.util.EnumMap;

public abstract class StageServoMono<T extends Enum<T>> extends StageServo<T>
{
    public Servo inner;

    public static class StageServoMonoBuilder<T extends Enum<T>>
    {
        public HardwareMap hardwareMap;
        public String servoName;
        public EnumMap<T, Double> positionMap;

        public StageServoMonoBuilder(HardwareMap hardwareMap, String servoName, Class<T> enumClass)
        {
            this.hardwareMap = hardwareMap;
            this.servoName = servoName;
            this.positionMap = new EnumMap<>(enumClass);
        }

        public StageServoMonoBuilder<T> add(T stage, double position)
        {
            if (position < 0 || position > 1)
                throw new IllegalArgumentException(
                        "Servo position values cannot be outside the range [0, 1]."
                );
            positionMap.put(stage, position);
            return this;
        }
    }

    public StageServoMono(StageServoMonoBuilder<T> builder)
    {
        super(builder.positionMap);
        inner = new Servo(builder.hardwareMap, builder.servoName);
    }

    public Servo getServo()
    {
        return inner;
    }

    @Override
    public void setPosition(double position)
    {
        inner.setPosition(position);
    }
}
