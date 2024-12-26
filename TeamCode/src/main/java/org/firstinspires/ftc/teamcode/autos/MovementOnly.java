package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
@Autonomous(name = "MovementOnly", group = "Roadrunner")
public class MovementOnly extends LinearOpMode {


    @Override
    public void runOpMode() {
        double ninety = Math.toRadians(90);
        double oneEighty = Math.toRadians(180);
        double twoSeventy = Math.toRadians(270);

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(24, -61, ninety); // needs manual testing II

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


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

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose).
                splineToSplineHeading(new Pose2d(5, -27, twoSeventy), ninety);

        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(5, -27, twoSeventy))
                .strafeToSplineHeading(new Vector2d(40, -36), ninety)
                .strafeToConstantHeading(new Vector2d(46, 0))
                .strafeToConstantHeading(new Vector2d(58, 0))
                .setTangent(ninety)
                .lineToY(-50) // pushes the block
                .lineToY(-16) // goes up to try and reach the second block
                .splineToConstantHeading(new Vector2d(62, -14), Math.toRadians(300)) // goes north of the second block
                .setTangent(ninety)
                .lineToY(-50); //pushes the second block down

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(58, -50, ninety))
                .strafeToConstantHeading(new Vector2d(36, -61)); // goes to the pickup position for the first block

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(36, -61, ninety))
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety); // goes to hook the first picked up block

        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(10, -34, twoSeventy))
                .strafeToSplineHeading(new Vector2d(36, -61), ninety); // goes to the pickup position for the second block

        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(36, -61, ninety))
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety); // goes to the bar

        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(10, -34, twoSeventy))
                .strafeTo(new Vector2d(61, -61)); // parks


        Action act1 = tab1.build();
        Action act2 = tab2.build();
        Action act3 = tab3.build();
        Action act4 = tab4.build();
        Action act5 = tab5.build();
        Action act6 = tab6.build();
        Action park = tab7.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(act1, act2, act3, act4
                        , act5, act6, park)
        );


    }

}