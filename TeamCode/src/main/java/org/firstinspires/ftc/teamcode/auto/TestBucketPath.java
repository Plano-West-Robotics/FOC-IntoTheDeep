package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
@Autonomous
public class TestBucketPath extends LinearOpMode {
    Pose2d initialPose;
    @Override
    public void runOpMode() throws InterruptedException {

        double ninety = Math.toRadians(90);
        double oneEighty = Math.toRadians(180);
        double twoSeventy = Math.toRadians(270);

        initialPose = new Pose2d(-24, -64, ninety);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose); //0, -34

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .setTangent(iR(135))

                .splineToLinearHeading(new Pose2d(-56, -56, iR(45)), iR(225)) // from preload to bucket

                .waitSeconds(1)

                .splineToSplineHeading(new Pose2d(-48, -38, iR(90)), iR(90)) // from bucket to first ground sample

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)) // from first ground sample to bucket

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-58, -38), iR(90)) // from bucket to picking up the second ground sample

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)) // from picking up the second sample to the bucket

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-54, -28), iR(170)) // from the bucket to picking up the third sample

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)) // from picking up the third sample to the bucket

                .waitSeconds(1)

                .splineTo(new Vector2d(56, -56), 0);

        Action tab1Act = tab1.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                tab1Act
        );
    }

    public static double iR(double degrees)
    {
        return Math.toRadians(degrees);
    }
}
