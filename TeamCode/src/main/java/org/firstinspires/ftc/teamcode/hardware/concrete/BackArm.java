package org.firstinspires.ftc.teamcode.hardware.concrete;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.StageServo;

public class BackArm extends StageServo<BackArm.Stage>
{
    public enum Stage
    {
        RETRACT, EXTEND
    }

    public BackArm(HardwareMap hardwareMap)
    {
        super(
                new StageServoBuilder<>(hardwareMap, "ba", Stage.class)
                        .add(Stage.RETRACT, 0.1)
                        .add(Stage.EXTEND, 0.2)
        );
    }
}
