package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.RobotParameters;
import org.firstinspires.ftc.teamcode.subsystems.LinearSlidePair;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "LinearSlideTest")
public class LinearSlideTest extends OpMode
{
    Hardware hw;
    MecanumDrive drive;
    LinearSlidePair intakeSlidePair, outtakeSlidePair;

    @Override
    public void init()
    {
        hw = new Hardware(hardwareMap);

        drive = new MecanumDrive(
                hw.motorFR,
                hw.motorFL,
                hw.motorBR,
                hw.motorBL,
                gamepad1,
                telemetry,
                RobotParameters.INITIAL_DRIVE_SPEED
        );

        intakeSlidePair = new LinearSlidePair(
                hw.intakeSlideMotorL,
                hw.intakeSlideMotorR,
                gamepad2,
                telemetry,
                RobotParameters.INTAKE_SLIDE_SPEED,
                RobotParameters.INTAKE_SLIDE_MAX_TICKS
        );

        outtakeSlidePair = new LinearSlidePair(
                hw.outtakeSlideMotorL,
                hw.outtakeSlideMotorR,
                gamepad2,
                telemetry,
                RobotParameters.OUTTAKE_SLIDE_SPEED,
                RobotParameters.OUTTAKE_SLIDE_MAX_TICKS
        );

        telemetry.addLine("Init finished.");
        telemetry.update();
    }

    @Override
    public void loop()
    {
        drive.takeControllerInput();
        drive.calculateMotorPowers();
        drive.powerMotors();

        intakeSlidePair.updateVelocity();

        // TODO: Implement outtakeSlidePair.
    }
}
