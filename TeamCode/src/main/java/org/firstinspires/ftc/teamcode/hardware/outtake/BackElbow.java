package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;

public class BackElbow extends StageServoMono<BackElbow.Stage>
{
    public enum Stage
    {
        REST, // Position when transferring a sample.
        SCORE // Position when scoring a sample/specimen; 45 degrees off from REST.
    }

    public BackElbow(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "be", Stage.class)
                .add(Stage.REST, 0.123)
                .add(Stage.SCORE, 0.456)
        );
    }
}
