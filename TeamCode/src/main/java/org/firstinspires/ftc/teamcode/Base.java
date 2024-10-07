package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.Claw;

public abstract class Base extends OpMode
{
    public Claw clawModule;

    public void initHardware()
    {
        Servo clawServo = hardwareMap.get(Servo.class, "Claw Servo");

        clawModule = new Claw(clawServo);
    }

    /**
     * Needed in order for MainTeleOp class to inherit OpMode methods
     */
    @Override
    public abstract void init();

    @Override
    public abstract void loop();
}