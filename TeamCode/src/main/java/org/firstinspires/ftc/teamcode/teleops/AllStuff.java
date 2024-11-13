package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.RobotParameters;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Intake.IntakeState;
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

        drive = new MecanumDrive(hw, telemetry);

        intake = new Intake(hw, gamepad2);

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

        intake.startIntake();
        intake.intakeState = IntakeState.EXTENDED_SLIDE;
    }

    @Override
    public void loop()
    {
        drive.takeControllerInput(gamepad1);
        drive.updateMotorPowers();

        switch (intake.intakeState)
        {
            case EXTENDED_SLIDE:
                intakeSlidePair.pidUpdateVelocity(gamepad2);
                intake.b = gamepad2.b;
                if ((intake.b_last != intake.b) && intake.b)
                {
                    intake.retract();
                    intake.intakeState = IntakeState.RETRACT;
                }
                intake.b_last = intake.b;
                break;

            case RETRACTED_SLIDE:
                intakeSlidePair.pidUpdateVelocity(gamepad2);
                intake.a = gamepad2.a;
                intake.b = gamepad2.b;
                if ((intake.b_last != intake.b) && intake.b)
                {
                    intake.extend();
                    intake.intakeState = IntakeState.EXTEND;
                }
                else if ((intake.a_last != intake.a) && intake.a)
                {
                    intakeSlidePair.goTo(0);
                    intake.intakeState = IntakeState.RETREAT;
                }
                intake.a_last = intake.a;
                intake.b_last = intake.b;
                break;

            case RETRACT:
                if (intake.servoL.getPosition() == 0 && intake.servoR.getPosition() == 0)
                {
                    intake.haltWheels();
                    intake.intakeState = IntakeState.RETRACTED_SLIDE;
                }
                break;

            case EXTEND:
                if (intake.servoL.getPosition() == 1 && intake.servoR.getPosition() == 1)
                {
                    intake.startIntake();
                    intake.intakeState = IntakeState.EXTENDED_SLIDE;
                }
                break;

            case RETREAT:
                if (intakeSlidePair.motorL.getCurrentPosition() == 0 && intakeSlidePair.motorR.getCurrentPosition() == 0)
                {
                    intakeSlidePair.holdMotors();
                    intake.startDump();
                    intake.intakeState = IntakeState.DUMP;
                }
                break;

            case DUMP:
                intake.b = gamepad2.b;
                if ((intake.b_last != intake.b) && intake.b)
                {
                    intake.haltWheels();
                    intake.intakeState = IntakeState.RETRACTED_SLIDE;
                }
                intake.b_last = intake.b;
                break;

            default:
                intake.intakeState = IntakeState.RETRACTED_SLIDE; // Should never be reached
        }
    }
}