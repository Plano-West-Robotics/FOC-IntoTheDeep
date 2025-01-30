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
@Autonomous(name = "TempAuto")
public class TempAuto extends LinearOpMode {

    Hardware hardware;

    public Intake intake;
    public Outtake outtake;

    public Pose2d initialPose;
    public MecanumDrive drive;


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
                .strafeToConstantHeading(new Vector2d(0, -31)); // goes from the start (middle of 2nd tile) to the chambers

        TrajectoryActionBuilder tabIntermediate = drive.actionBuilder(new Pose2d(0, -31, twoSeventy))
                .strafeToConstantHeading(new Vector2d(0, -28)); // goes in closer to make sure it will actually hook

        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(0, -28, twoSeventy))
                .splineToSplineHeading(new Pose2d(36, -30, ninety), ninety) // intermediate stage in the path to first block
                .splineToConstantHeading(new Vector2d(42, -10), 0)
                .splineToConstantHeading(new Vector2d(44, -14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(44, -40), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(44, -14), ninety)
                .splineToConstantHeading(new Vector2d(49, -8), 0)
                .splineToConstantHeading(new Vector2d(53, -14), twoSeventy)
                .splineToConstantHeading(new Vector2d(53, -44), twoSeventy);
                //.splineToConstantHeading(new Vector2d(54, -46), twoSeventy)
                /*.splineToConstantHeading(new Vector2d(46, -8), 0) // arrives north of the first block
                .setTangent(ninety)
                .lineToY(-50) // pushes the block down into the zone
                .lineToY(-16) // goes up to try and reach the second block
                .splineToConstantHeading(new Vector2d(52, -8), Math.toRadians(300)) // goes north of the second block
                .setTangent(ninety)
                .lineToY(-50); //pushes the second block down into the zone*/

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(53, -44, ninety))
                //.strafeToConstantHeading(new Vector2d(38, -61)); // goes to the wall pickup position for the first pushed block
                .splineToConstantHeading(new Vector2d(42, -62), twoSeventy);

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(42, -62, ninety))
                .splineToSplineHeading(new Pose2d(-8, -31, twoSeventy), ninety); // goes to hook the first picked up block

        TrajectoryActionBuilder tabIntermediate2 = drive.actionBuilder(new Pose2d(-8, -31, twoSeventy))
                .strafeToConstantHeading(new Vector2d(-8, -28)); // goes in closer to properly hook

        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(-8, -28, twoSeventy))
                .strafeToSplineHeading(new Vector2d(38, -64), ninety); // goes to the pickup position for the second block

        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(38, -64, ninety))
                .splineToSplineHeading(new Pose2d(-2, -31, twoSeventy), ninety); // goes to the bar

        TrajectoryActionBuilder tabIntermediate3 = drive.actionBuilder(new Pose2d(-2, -31, twoSeventy))
                .strafeToConstantHeading(new Vector2d(-2, -28)); // goes in closer to properly hook

        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(-2, -28, twoSeventy))
                .strafeTo(new Vector2d(61, -61)); // parks

        //init actions - first close the claw around the preload then shift it as necessary when the arm is in position
        Actions.runBlocking(
                new SequentialAction(outtake.getClaw().close(300),
                        outtake.getElbow().clip(300),
                        outtake.getArm().clip(200))
        );

        Action act1 = tab1.build();
        Action actInt = tabIntermediate.build();
        Action act2 = tab2.build();
        Action act3 = tab3.build();
        Action act4 = tab4.build();
        Action actInt2 = tabIntermediate2.build();
        Action act5 = tab5.build();
        Action act6 = tab6.build();
        Action park = tab7.build();
        Action actInt3 = tabIntermediate3.build();

        waitForStart();

        if (isStopRequested()) return;


        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(act1, outtake.getExtendo().highChamber()), // go to the chamber while extending slides

                        actInt, // get close enough to hook
                        outtake.getExtendo().getSlideAction(1200), // lower slides to hook - should probably add an actual method for this
                        outtake.getClaw().open(100), // open claw so that the bot can move away
                        new ParallelAction(act2, outtake.getExtendo().getSlideAction(10)), // lower slides to pickup position while
                        //pushing samples back

                        new ParallelAction(act3, outtake.getArm().wall(100), outtake.getElbow().wall(100)),
                        // go to pickup position while moving elbow and arm to pickup position

                        outtake.getClaw().close(100), // close claw to hold specimen from the wall
                        new ParallelAction(act4, outtake.getExtendo().highChamber(), outtake.getElbow().clip(200), outtake.getArm().clip(200)),
                        // go to the chamber again while raising the slides and resetting the elbow and arm to clip position

                        actInt2, // go in closer to actually be able to clip
                        outtake.getExtendo().getSlideAction(1000), // lower slides to hook
                        outtake.getClaw().open(100), // release the specimen
                        new ParallelAction(act5, outtake.getExtendo().getSlideAction(10),
                                outtake.getElbow().wall(100), outtake.getArm().wall(100)),
                        outtake.getClaw().close(100),
                        new ParallelAction(act6, outtake.getExtendo().highChamber(), outtake.getElbow().clip(100), outtake.getArm().clip(100)),
                        actInt3,
                        outtake.getExtendo().getSlideAction(1000), // lower slides to hook - should probably add an actual method for this
                        outtake.getClaw().open(100), // open claw so that the bot can move away
                        outtake.getExtendo().getSlideAction(10),
                        intake.getExtendo().retract(),
                        intake.getArm().upright(400),
                        outtake.getArm().rest(400),
                        outtake.getElbow().transfer(200)
                )
                );


    }

}


/*
 * Plan for auto improvements:
 * Make two state machines for the auto (one for spec and one for bucket) - will make transitions easy and
 * I won't have to remember what components need to move with each path
 * Make optimized, continuous paths for 4 specimen and 4 bucket - look at video examples
 */