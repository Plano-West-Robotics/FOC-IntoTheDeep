package org.firstinspires.ftc.teamcode.auto.experimental;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.SpecimenAutoActions;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;


@Config
@Autonomous(name = "Specimen Auto for Last Call", group = "CompPrograms")
public class LastChanceSpecimenAutoExperimental extends LinearOpMode {

    Hardware hardware;

    public Intake intake;
    public Outtake outtake;

    public Pose2d initialPose;
    public MecanumDrive drive;

    public SpecimenAutoActionsExperimental specMethods;


    @Override
    public void runOpMode() {

        hardware = new Hardware(hardwareMap);

        initialPose = new Pose2d(0, -63, Math.toRadians(90));

        drive = new MecanumDrive(hardwareMap, initialPose);

        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        specMethods = new SpecimenAutoActionsExperimental(intake, outtake, drive, initialPose);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        specMethods.createTrajectories();

        // init actions
        Actions.runBlocking(
                specMethods.initPreload()
        );

        // build paths on init
        specMethods.buildPaths();

        waitForStart();
        // after start is pressed

        if (isStopRequested()) return; // necessary to be able to stop program

        Actions.runBlocking(
                specMethods.fullSequence()
        );

    }

}
