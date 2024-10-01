package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw
{
    Servo servo;

    public Claw(Servo servo)
    {
        this.servo = servo;
        this.servo.scaleRange(0.3, 0.9);
        this.servo.setDirection(Servo.Direction.REVERSE);
    }

    public void open() {servo.setPosition(1.0);}

    public void close() {servo.setPosition(0.0);}
}
