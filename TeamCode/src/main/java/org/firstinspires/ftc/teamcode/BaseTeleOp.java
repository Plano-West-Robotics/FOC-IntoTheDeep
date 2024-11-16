package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

public abstract class BaseTeleOp extends OpMode
{
    public MecanumDrive drivetrain;

    public DcMotor motorFR, motorFL, motorBR, motorBL;

    public void initHardware()
    {
        motorFR = hardwareMap.get(DcMotor.class, "frontRight");
        motorFL = hardwareMap.get(DcMotor.class, "frontLeft");
        motorBR = hardwareMap.get(DcMotor.class, "backRight");
        motorBL = hardwareMap.get(DcMotor.class, "backLeft");
        drivetrain = new MecanumDrive(motorFR, motorFL, motorBR, motorBL, gamepad1);
    }

    /**
     * Abstract methods are needed for our OpModes to inherit the superclass init and loop methods
     */
    @Override
    public abstract void init();

    @Override
    public abstract void loop();
}