package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@Config
@Autonomous
public class BucketAuto extends LinearOpMode {

    Hardware hardware;

    public Intake intake;
    public Outtake outtake;

    public Pose2d initialPose;
    public MecanumDrive drive;

    public static double iR(double degrees)
    {
        return Math.toRadians(degrees);
    }

    @Override
    public void runOpMode() {
        double ninety = Math.toRadians(90);
        double oneEighty = Math.toRadians(180);
        double twoSeventy = Math.toRadians(270);

        hardware = new Hardware(hardwareMap);
        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-24, -61, ninety); // needs manual testing II

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
                .setTangent(iR(135))

                .splineToLinearHeading(new Pose2d(-56, -56, iR(45)), iR(225)); // go to bucket using preload sample


        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(-56, -56, iR(225)))
                .splineToSplineHeading(new Pose2d(-48, -38, iR(90)), iR(90)); //pickup first block

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(-48, -38, ninety))
                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)); //at bucket

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(-56, -56, iR(45)))
                .strafeToSplineHeading(new Vector2d(-58, -38), iR(90)); // pickup second


        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(-58, -38, ninety))
                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)); //at bucket
/*
        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(-56, -61, iR(45)))
                .splineToSplineHeading(new Pose2d(-4, -31, twoSeventy), ninety); // goes to the bar

        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(-4, -29, twoSeventy))
                .strafeTo(new Vector2d(61, -61)); // parks
*/
        //init actions - first close the claw around the preload then shift it as necessary when the arm is in position
        Actions.runBlocking(
                new SequentialAction(outtake.getClaw().close(300),
                        outtake.getElbow().clip(300),
                        outtake.getArm().clip(200))
        );

        Action act1 = tab1.build();
        //Action actInt = tabIntermediate.build();
        Action act2 = tab2.build();
        Action act3 = tab3.build();
        Action act4 = tab4.build();
        //Action actInt2 = tabIntermediate2.build();
        Action act5 = tab5.build();
        /*Action act6 = tab6.build();
        Action park = tab7.build();
        Action actInt3 = tabIntermediate3.build();*/


        Actions.runBlocking(
                new SequentialAction(outtake.getClaw().close(300),
                        outtake.getElbow().bucket(300), outtake.getArm().bucket(300)
                        )
        );

        waitForStart();

        if (isStopRequested()) return;


        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(act1, outtake.getExtendo().highBasket()),
                        outtake.getClaw().bucketOpen(100),
                        new ParallelAction(act2, outtake.getExtendo().wallGrab(),
                                outtake.getArm().rest(300), outtake.getElbow().transfer(200))
        )
        );
    }

}

/*
 * Plan:
 * On init close the claw and set the elbow and arm to the bucket position
 * Go from the left starting position to the bucket position while extending the slides
 * Once reached, open the claw,
 * move back, then lower the slides, move the outtake elbow and arm and slides to rest as you move to to the first sample on the ground
 * move the intake arm outward, move the elbow down
 * close the claw, move the arm and elbow into transfer position,
 * move the back arm and elbow into transfer position, close back claw and open front one
 * move the back arm and elbow into bucket position, repeat
 *
 */