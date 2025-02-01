package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@Disabled
@Config
@Autonomous(name = "Bucket Auto", group = "CompPrograms")
public class LastChanceBucketAuto extends LinearOpMode {

    Hardware hardware;

    public Intake intake;
    public Outtake outtake;

    public Pose2d initialPose;
    public MecanumDrive drive;


    @Override
    public void runOpMode() {

        hardware = new Hardware(hardwareMap);

        /* TODO: if the odo pods get fixed the initial y position should be measured to make sure the center is actually
                 9 inches from the back of the robot - it probably actually isn't - also look at TODOS in the other class
                  also fix the dimensions in MeepMeep too*/
        Pose2d initialPose = new Pose2d(24, -61, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        SpecimenAutoActions specMethods = new SpecimenAutoActions(intake, outtake, drive, initialPose);

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
