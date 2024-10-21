package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public abstract class Base extends OpMode
{
    public Drivetrain drive;

    public DcMotor motorFR, motorFL, motorBR, motorBL;

    public void initHardware()
    {
        motorFR = hardwareMap.get(DcMotor.class, "frontRight");
        motorFL = hardwareMap.get(DcMotor.class, "frontLeft");
        motorBR = hardwareMap.get(DcMotor.class, "rearRight");
        motorBL = hardwareMap.get(DcMotor.class, "rearLeft");
        drive = new Drivetrain(motorFR, motorFL, motorBR, motorBL, gamepad1);
    }

    public void updateDrive()
    {
        drive.takeControllerInput();
        drive.drive();
    }

    /**
     * Abstract methods are needed for our OpModes to inherit the superclass init and loop methods
     */
    @Override
    public abstract void init();

    @Override
    public abstract void loop();
}