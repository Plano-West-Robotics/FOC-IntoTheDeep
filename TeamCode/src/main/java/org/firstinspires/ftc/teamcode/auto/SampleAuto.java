package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@Config
@Autonomous
public class SampleAuto extends BaseAuto
{
    public Intake intake;
    public Outtake outtake;

    public Pose2d initialPose;
    public MecanumDrive drive;

    public Action spawnToChamber, chamberToPushLeftmostSampleToObservationZone;

    public Action openBackClaw, closeBackClaw;
    public Action vExtendoToHC, vExtendoToClipHC, vExtendoToRest;

    @Override
    public void setup()
    {
        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        initialPose = new Pose2d(24, -60, tR(90));
        drive = new MecanumDrive(hardwareMap, initialPose);

        spawnToChamber = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(0.00, -38.00, Math.toRadians(270.00)), Math.toRadians(90.00))
                .build();

        chamberToPushLeftmostSampleToObservationZone = drive.actionBuilder(new Pose2d(0.00, -38.00, Math.toRadians(270.00)))
                .splineTo(new Vector2d(36.00, -38.00), Math.toRadians(90.00))
                .splineToLinearHeading(new Pose2d(40.00, -14.00, Math.toRadians(65.00)), Math.toRadians(65.00))
                .splineToConstantHeading(new Vector2d(48.00, -10.00), Math.toRadians(0.00))
                .turnTo(Math.toRadians(90.00))
                .splineTo(new Vector2d(48.00, -60.00), Math.toRadians(270.00))
                .build();


        openBackClaw = outtake.getClaw().open(150);
        closeBackClaw = outtake.getClaw().close(150);
        vExtendoToHC = outtake.getExtendo().getSlideAction(VerticalExtendo.HC_POSITION);
        vExtendoToClipHC = outtake.getExtendo().getSlideAction(VerticalExtendo.HC_POSITION - 100);
        vExtendoToRest = outtake.getExtendo().getSlideAction((int) outtake.getExtendo().minPosition);

        Actions.runBlocking(closeBackClaw);
    }

    @Override
    public void log()
    {
        telemetry.addData("VL Position", outtake.getExtendo().getLeft().getPosition());
        telemetry.addData("VR Position", outtake.getExtendo().getRight().getPosition());
    }

    @Override
    public void run()
    {
        Actions.runBlocking(new SequentialAction(
                spawnToChamber,
                vExtendoToHC,
                new ParallelAction(vExtendoToClipHC, openBackClaw),
                chamberToPushLeftmostSampleToObservationZone
        ));
    }

    public static double tR(double degrees)
    { return Math.toRadians(degrees); }

}


/* Plan:
start the back arm in the rest position and close the claw on init & set elbow to transfer
move the robot to the high bar while moving the back arm to the clip position and the elbow to clip while the robot is moving
when the bar has been reached, move the vert slides down, let go of the claw, then move the robot to push 2 samples down
once that is done, go to the pickup zone and close the claw, go to the bar and do the same thing
 */
