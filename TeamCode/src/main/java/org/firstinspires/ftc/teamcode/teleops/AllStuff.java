package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.RobotParameters;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.LinearSlidePair;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "AllStuff")
public class AllStuff extends OpMode
{
    Hardware hw;
    MecanumDrive drive;
    LinearSlidePair intakeSlidePair, outtakeSlidePair;
    Intake intake;

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
                telemetry
        );

        intake = new Intake(
                hw.intakeServoL,
                hw.intakeServoR,
                hw.intakeWheelL,
                hw.intakeWheelR,
                gamepad1
        );

        intakeSlidePair = new LinearSlidePair(
                hw.intakeSlideMotorL,
                hw.intakeSlideMotorR,
                gamepad2,
                telemetry,
                RobotParameters.INTAKE_SLIDE_MAX_SPEED,
                RobotParameters.INTAKE_SLIDE_MAX_TICKS
        );

        outtakeSlidePair = new LinearSlidePair(
                hw.outtakeSlideMotorL,
                hw.outtakeSlideMotorR,
                gamepad2,
                telemetry,
                RobotParameters.OUTTAKE_SLIDE_MAX_SPEED,
                RobotParameters.OUTTAKE_SLIDE_MAX_TICKS
        );

        telemetry.addLine("Init finished.");
        telemetry.update();
    }

    @Override
    public void loop()
    {
        drive.takeControllerInput();
        drive.updateMotorPowers();
        drive.powerMotors();

        intakeSlidePair.updateVelocity();

        intake.takeControllerInput();
    }
}