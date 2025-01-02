package org.firstinspires.ftc.teamcode.hardware.outtake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.base.StageServoMono;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontClaw;

public class BackClaw extends StageServoMono<BackClaw.Stage>
{
    public enum Stage
    {
        OPEN,
        CLOSE // Not maximum clamping force; should allow sample to slide back and forth.
    }

    public BackClaw(HardwareMap hardwareMap)
    {
        super(new StageServoMonoBuilder<>(hardwareMap, "bc", Stage.class)
                .add(Stage.OPEN, 0.54)
                .add(Stage.CLOSE, 0.77)
        );
    }

    public void toggle()
    {
        switch (getStage())
        {
            case OPEN -> setStage(Stage.CLOSE);
            case CLOSE -> setStage(Stage.OPEN);
        }
    }
}
