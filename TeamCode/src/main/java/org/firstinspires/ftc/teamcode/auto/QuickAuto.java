package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@Config
@Autonomous(name = "QuickAuto")
public class QuickAuto extends LinearOpMode {

    Hardware hardware;

    public Intake intake;
    public Outtake outtake;

    public Pose2d initialPose;
    public MecanumDrive drive;

    public Action spawnToChamber, chamberToPushLeftmostSampleToObservationZone;

    public Action openBackClaw, closeBackClaw;
    public Action vExtendoToHC, vExtendoToClipHC, vExtendoToRest;

    @Override
    public void runOpMode() {
        double ninety = Math.toRadians(90);
        double oneEighty = Math.toRadians(180);
        double twoSeventy = Math.toRadians(270);

        hardware = new Hardware(hardwareMap);
        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(24, -61, twoSeventy); // needs manual testing II

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        /*

        //https://learnroadrunner.com/trajectories.html#slowing-down-a-trajectory to adjust trajectory speed
        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety) // position at the bar for the preload
                .waitSeconds(1)
                .splineToSplineHeading(new Pose2d(36, -30, ninety), ninety) // intermediate stage in the path to first block
                .splineToConstantHeading(new Vector2d(48, -10), 0) // arrives north of the first block
                .setTangent(ninety)
                .lineToY(-56) // pushes the block
                .lineToY(-16) // goes up to try and reach the second block
                .splineToConstantHeading(new Vector2d(58, -14), Math.toRadians(300)) // goes north of the second block
                .setTangent(ninety)
                .lineToY(-56) // pushes the second block down
                .strafeToConstantHeading(new Vector2d(36, -61)) // goes to the pickup position for the first block
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety) // goes to the bar
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(36, -61), ninety) // goes to the pickup position for the second block
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety) // goes to the bar
                .waitSeconds(1)
                .strafeTo(new Vector2d(61, -61));// parks

         */

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(0, -31));

        TrajectoryActionBuilder tabIntermediate = drive.actionBuilder(new Pose2d(0, -31, twoSeventy))
                .strafeToConstantHeading(new Vector2d(0, -29));

        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(0, -29, twoSeventy))
                .splineToSplineHeading(new Pose2d(36, -30, ninety), ninety) // intermediate stage in the path to first block
                .splineToConstantHeading(new Vector2d(50, -8), 0) // arrives north of the first block
                .setTangent(ninety)
                .lineToY(-50) // pushes the block
                .lineToY(-16) // goes up to try and reach the second block
                .splineToConstantHeading(new Vector2d(56, -8), Math.toRadians(300)) // goes north of the second block
                .setTangent(ninety)
                .lineToY(-50); //pushes the second block down

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(56, -50, ninety))
                .strafeToConstantHeading(new Vector2d(36, -61)); // goes to the pickup position for the first block

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(36, -61, ninety))
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety); // goes to hook the first picked up block

        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(10, -34, twoSeventy))
                .strafeToSplineHeading(new Vector2d(36, -61), ninety); // goes to the pickup position for the second block

        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(36, -61, ninety))
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety); // goes to the bar

        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(10, -34, twoSeventy))
                .strafeTo(new Vector2d(61, -61)); // parks

        //init actions
        Actions.runBlocking(
                new SequentialAction(outtake.getClaw().close(300),
                        outtake.getElbow().clip(300),
                        outtake.getArm().clip(200))
        );

        waitForStart();

        if (isStopRequested()) return;

        Action act1 = tab1.build();
        Action actInt = tabIntermediate.build();
        Action act2 = tab2.build();
        Action act3 = tab3.build();
        Action act4 = tab4.build();
        Action act5 = tab5.build();
        Action act6 = tab6.build();
        Action park = tab7.build();

        Actions.runBlocking(
                new SequentialAction(new ParallelAction(act1, outtake.getExtendo().highChamber())
                        , actInt, outtake.getExtendo().getSlideAction(1200),
                        outtake.getClaw().open(100), new ParallelAction(act2, outtake.getExtendo().wallGrab())
                )
                //new ParallelAction(act1, outtake.getExtendo().highChamber());

                //new SequentialAction(act1, outtake.getExtendo().highChamber(), outtake.getArm().clip(600),
                //        outtake.getElbow().clip(400), actInt, outtake.getExtendo().getSlideAction(1200),
                //        outtake.getClaw().open(100), act2
                //
                );


    }

}