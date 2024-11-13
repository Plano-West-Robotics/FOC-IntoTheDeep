package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.RobotParameters;
import org.firstinspires.ftc.teamcode.subsystems.LinearSlidePair;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "LinearSlides")
public class LinearSlides extends OpMode
{
    Hardware hw;
    MecanumDrive drive;
    LinearSlidePair intakeSlidePair, outtakeSlidePair;

    @Override
    public void init()
    {
        hw = new Hardware(hardwareMap);

        drive = new MecanumDrive(hw, telemetry);

        intakeSlidePair = new LinearSlidePair(
                hw.intakeSlideMotorL,
                hw.intakeSlideMotorR,
                telemetry,
                RobotParameters.INTAKE_SLIDE_MAX_SPEED,
                RobotParameters.INTAKE_SLIDE_MAX_TICKS
        );

        outtakeSlidePair = new LinearSlidePair(
                hw.outtakeSlideMotorL,
                hw.outtakeSlideMotorR,
                telemetry,
                RobotParameters.OUTTAKE_SLIDE_MAX_SPEED,
                RobotParameters.OUTTAKE_SLIDE_MAX_TICKS
        );

        telemetry.addLine("Init finished.");
        telemetry.update();
    }

    @Override
    public void start()
    {
        intakeSlidePair.initPIDTimer();
    }

    @Override
    public void loop()
    {
        drive.takeControllerInput(gamepad1);
        drive.updateMotorPowers();

        intakeSlidePair.pidUpdateVelocity(gamepad2);

        // TODO: Implement outtakeSlidePair.

        // TODO: Debug intake linear slide system.
        {
            telemetry.addData("Left Joystick Raw", gamepad2.left_stick_y);
            telemetry.addData("Intake Slide Ticks", "L=%d R=%d",
                    intakeSlidePair.motorL.getCurrentPosition(),
                    intakeSlidePair.motorR.getCurrentPosition());
            telemetry.addData("Right Motor Error (+ => behind; - => ahead)",
                    intakeSlidePair.currError);
        }
    }
}