package org.firstinspires.ftc.teamcode.hardware.concrete;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.StageServo;

public class FrontArm extends StageServo<FrontArm.Stage>
{
    public enum Stage
    {
        RETRACT, EXTEND
    }

    public FrontArm(HardwareMap hardwareMap)
    {
        super(
                new StageServoBuilder<>(hardwareMap, "fa", Stage.class)
                        .add(Stage.RETRACT, 0.2)
                        .add(Stage.EXTEND, 0.3)
        );
    }
}
