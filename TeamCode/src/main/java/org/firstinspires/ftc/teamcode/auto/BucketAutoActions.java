package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.Utils;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public class BucketAutoActions {
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
     */

    public Intake intake;
    public Outtake outtake;

    public MecanumDrive drive;
    public Pose2d initPose;

    public BucketAutoActions(Intake intake, Outtake outtake, MecanumDrive drive, Pose2d initPose){
        this.intake = intake;
        this.outtake = outtake;
        this.drive = drive;
        this.initPose = initPose;
    }

    public Action setIntakeSlidesRetract() { return intake.getExtendo().retract(); };
    public Action setIntakeArmRetract() { return intake.getArm().retract(300); };
    public Action setIntakeClawOpen() { return intake.getClaw().open(120); };
    public Action setIntakeSwivelCenter() { return intake.getSwivel().center(250); };
    public Action setOuttakeClawClose() { return outtake.getClaw().close(120); };
    public Action setOuttakeElbowBucket() { return outtake.getElbow().bucket(250); };
    public Action setOuttakeArmBucket() { return outtake.getArm().bucket(450); };
    public Action setOuttakeSlidesToBucket() { return outtake.getExtendo().highBucket(); }; // TODO: TUNE THE OUTTAKE SLIDES TO BE FASTER
    public Action setOuttakeClawOpen() { return outtake.getClaw().open(120); };
    public Action setOuttakeSlidesToBottom() { return outtake.getExtendo().lowerFully(); };
    public Action setOuttakeElbowRest() { return outtake.getElbow().transfer(250); };
    public Action setOuttakeArmRest() { return outtake.getArm().rest(300); };
    public Action setIntakeArmPickup() { return intake.getArm().probe(350); };
    public Action setIntakeClawClose() { return intake.getClaw().close(120); };
    public Action setIntakeArmTransfer() { return intake.getArm().retract(350); };
    public Action setOuttakeArmTransfer() { return outtake.getArm().transfer(350); };

    public TrajectoryActionBuilder preloadToBucketPath;
    public TrajectoryActionBuilder bucketToSample1Path;
    public TrajectoryActionBuilder sample1ToBucketPath;
    public TrajectoryActionBuilder bucketToSample2Path;
    public TrajectoryActionBuilder sample2ToBucketPath;
    public TrajectoryActionBuilder bucketToSample3Path;
    public TrajectoryActionBuilder sample3ToBucketPath;
    public TrajectoryActionBuilder bucketToParkPath;

    public Action preloadToBucketPathAction;
    public Action bucketToSample1PathAction;
    public Action sample1ToBucketPathAction;
    public Action bucketToSample2PathAction;
    public Action sample2ToBucketPathAction;
    public Action bucketToSample3PathAction;
    public Action sample3ToBucketPathAction;
    public Action bucketToParkPathAction;


    // At end of auto, transitioning into TeleOp
    public Action resetHorizontalSlides(){ return intake.getExtendo().retract(); };
    public Action resetVerticalSlides(){ return outtake.getExtendo().lowerFully(); };
    public Action resetIntakeArm(){ return intake.getArm().retract(300); };
    public Action resetOuttakeArm(){ return outtake.getArm().rest(400); };
    public Action resetElbow(){ return outtake.getElbow().transfer(300); };

    // TODO: REMOVE ENUMS

    public SequentialAction initPreload()
    {
        return new SequentialAction
        (
            new ParallelAction(setIntakeSlidesRetract(), setIntakeArmRetract(), setIntakeClawOpen(), setIntakeSwivelCenter(), setOuttakeClawClose()),
            setOuttakeElbowBucket(),
            setOuttakeArmBucket()
        );
    }

    public ParallelAction approachBucketPreload()
    {
        return new ParallelAction(setOuttakeSlidesToBucket(), preloadToBucketPathAction);
    }

    public SequentialAction atBucket()
    {
        return new SequentialAction(setOuttakeClawOpen());
    }

    public ParallelAction pickUpSampleFromBucket(Action toSampleAction)
    {
        return new ParallelAction
                (
                        toSampleAction, setOuttakeSlidesToBottom(), setOuttakeElbowRest(), setOuttakeArmRest()
                );
    }

    public SequentialAction atSample()
    {
        return new SequentialAction
                (
                        setIntakeArmPickup(), setIntakeClawClose()
                );
    }


    public ParallelAction fromPickupToBucket(Action pathAction)
    {
        return new ParallelAction(pathAction,
                        new SequentialAction(
                                        new ParallelAction(setIntakeArmTransfer(), setOuttakeArmTransfer()),
                                        new ParallelAction(setIntakeClawOpen(), setOuttakeClawClose()),
                                        new ParallelAction(setOuttakeArmBucket(), setOuttakeSlidesToBucket())
                                ));



    }

    public ParallelAction resetForTeleOp(Action parkAction)
    {
        return new ParallelAction
                (
                        parkAction, resetElbow(), resetOuttakeArm(), resetIntakeArm(), resetHorizontalSlides(), resetVerticalSlides()
                );
    }


    public void createTrajectories()
    {
        // TODO: MAKE SURE TO TUNE THE Y POSITION OF THE CHAMBER - SHOULD CHANGE IT FOR ALL FOLLOWING TRAJECTORIES TOO
        preloadToBucketPath = drive.actionBuilder(initPose)
                .setTangent(Utils.iR(135))
                .splineToLinearHeading(new Pose2d(-56, -56, Utils.iR(45)), Utils.iR(225));
        Pose2d preloadToBucketPathFinalPose = new Pose2d(-56, -56, Utils.iR(45));

        bucketToSample1Path = drive.actionBuilder(preloadToBucketPathFinalPose)
                .splineToSplineHeading(new Pose2d(-48, -38, Utils.iR(90)), Utils.iR(90));
        Pose2d bucketToSample1PathFinalPose = new Pose2d(-48, -38, Utils.iR(90));

        sample1ToBucketPath = drive.actionBuilder(bucketToSample1PathFinalPose)
                .strafeToSplineHeading(new Vector2d(-56, -56), Utils.iR(45));
        Pose2d sample1ToBucketPathFinalPose = new Pose2d(-56, -56, Utils.iR(45));

        bucketToSample2Path = drive.actionBuilder(sample1ToBucketPathFinalPose)
                .strafeToSplineHeading(new Vector2d(-58, -38), Utils.iR(90));
        Pose2d bucketToSample2PathFinalPose = new Pose2d(-58, -38, Utils.iR(90));

        sample2ToBucketPath = drive.actionBuilder(bucketToSample2PathFinalPose)
                .strafeToSplineHeading(new Vector2d(-56, -56), Utils.iR(45));
        Pose2d sample2ToBucketPathFinalPose = new Pose2d(-56, -56, Utils.iR(45));

        bucketToSample3Path = drive.actionBuilder(sample2ToBucketPathFinalPose)
                .strafeToSplineHeading(new Vector2d(-54, -28), Utils.iR(170));
        Pose2d bucketToSample3PathFinalPose = new Pose2d(-54, -28, Utils.iR(170));

        sample3ToBucketPath = drive.actionBuilder(bucketToSample3PathFinalPose)
                .strafeToSplineHeading(new Vector2d(-56, -56), Utils.iR(45));
        Pose2d sample3ToBucketPathFinalPose = new Pose2d(-56, -56, Utils.iR(45));

        bucketToParkPath = drive.actionBuilder(sample3ToBucketPathFinalPose)
                .splineTo(new Vector2d(56, -56), 0);
        Pose2d bucketToParkPathFinalPose = new Pose2d(56, -56, 0);

    }

    public void buildPaths()
    {
        preloadToBucketPathAction = preloadToBucketPath.build();
        bucketToSample1PathAction = bucketToSample1Path.build();
        sample1ToBucketPathAction = sample1ToBucketPath.build();
        bucketToSample2PathAction = bucketToSample2Path.build();
        sample2ToBucketPathAction = sample2ToBucketPath.build();
        bucketToSample3PathAction = bucketToSample3Path.build();
        sample3ToBucketPathAction = sample3ToBucketPath.build();
        bucketToParkPathAction = bucketToParkPath.build();
    }

    public SequentialAction fullSequence()
    {
        return new SequentialAction
        (
            approachBucketPreload(),
            atBucket(),
            pickUpSampleFromBucket(bucketToSample1PathAction),
            atSample(),
            fromPickupToBucket(sample1ToBucketPathAction),
            atBucket(),
            pickUpSampleFromBucket(bucketToSample2PathAction),
            atSample(),
            fromPickupToBucket(sample2ToBucketPathAction),
            atBucket(),
            pickUpSampleFromBucket(bucketToSample3PathAction),
            atSample(),
            fromPickupToBucket(sample3ToBucketPathAction),
            atBucket(),
            resetForTeleOp(bucketToParkPathAction)
        );
    }


    public static double tR(double degrees)
    {
        return Math.toRadians(degrees);
    }


}
