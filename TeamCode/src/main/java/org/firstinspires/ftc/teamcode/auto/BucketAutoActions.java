package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public class BucketAutoActions {

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
    public Action setIntakeArmUpright() { return intake.getArm().upright(300); };
    public Action setIntakeClawOpen() { return intake.getClaw().open(120); };
    public Action setIntakeSwivelCenter() { return intake.getSwivel().center(250); };
    public Action setOuttakeClawClose() { return outtake.getClaw().close(120); };
    public Action setOuttakeElbowBucket() { return outtake.getElbow().bucket(250); };
    public Action setOuttakeArmBucket() { return outtake.getArm().bucket(450); };
    public Action setOuttakeSlidesToBucket() { return outtake.getExtendo().highBasket(); }; // TODO: TUNE THE OUTTAKE SLIDES TO BE FASTER
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
            new ParallelAction(setIntakeSlidesRetract(), setIntakeArmUpright(), setIntakeClawOpen(), setIntakeSwivelCenter(), setOuttakeClawClose()),
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

    /*
    public SequentialAction atPickup()
    {
        return new SequentialAction
                (
                        setBackClawClose()
                );
    }

    public ParallelAction nonPreloadHook(Action pathAction)
    {
        return new ParallelAction
                (
                        setSlidesToUnderChamber(), pathAction
                );
    }

    public ParallelAction toPickupFromChamber(Action pathAction)
    {
        return new ParallelAction
                (
                        setElbowWall(), setArmWall(), setSlidesToBottom(), pathAction
                );
    }

    public ParallelAction resetForTeleOp()
    {
        return new ParallelAction
                (
                        resetElbow(), resetOuttakeArm(), resetIntakeArm(), resetHorizontalSlides(), resetVerticalSlides()
                );
    }


    public void createTrajectories()
    {
        // TODO: MAKE SURE TO TUNE THE Y POSITION OF THE CHAMBER - SHOULD CHANGE IT FOR ALL FOLLOWING TRAJECTORIES TOO
        moveToChamberPath = drive.actionBuilder(initPose).strafeToConstantHeading(new Vector2d(0, -28));
        Pose2d moveToChamberPathFinalPose = new Pose2d(0, -28, tR(90));

        pushSamplesPath = drive.actionBuilder(moveToChamberPathFinalPose)
                .setTangent(Math.toRadians(315))
                .splineToSplineHeading(new Pose2d(36, -40, tR(90)), tR(90))
                .splineToConstantHeading(new Vector2d(42, -10), 0)
                .splineToConstantHeading(new Vector2d(48, -14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -46), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -16), tR(90))
                .splineToConstantHeading(new Vector2d(51, -10), 0)
                .splineToConstantHeading(new Vector2d(54, -14), tR(270))
                .splineToConstantHeading(new Vector2d(54, -46), tR(270));
        Pose2d pushSamplesPathFinalPose = new Pose2d(54, -46, tR(90));

        pickupFromPushingPath = drive.actionBuilder(pushSamplesPathFinalPose)
                .splineToConstantHeading(new Vector2d(42, -58), Math.toRadians(270));
        Pose2d pickupFromPushingPathFinalPose = new Pose2d(42, -58, tR(90));

        hookFromPickupWithTimedElbowAndArmPath1 = drive.actionBuilder(pickupFromPushingPathFinalPose)
                .strafeToConstantHeading(new Vector2d(-8, -27))
                .afterTime(0.4, new ParallelAction(setElbowHook(), setArmHook()));
        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose1 = new Pose2d(-8, -27, tR(90));

        toPickupFromChamberPath1 = drive.actionBuilder(hookFromPickupWithTimedElbowAndArmPathFinalPose1)
                .strafeToConstantHeading(new Vector2d(38, -63));
        Pose2d toPickupFromChamberPathFinalPose1 = new Pose2d(38, -63, tR(90));

        hookFromPickupWithTimedElbowAndArmPath2 = drive.actionBuilder(toPickupFromChamberPathFinalPose1)
                .strafeToConstantHeading(new Vector2d(4, -27))
                .afterTime(0.4, new ParallelAction(setElbowHook(), setArmHook()));
        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose2 = new Pose2d(4, -27, tR(90));

    }

    public void buildPaths()
    {
        moveToChamberPathAction = moveToChamberPath.build();
        pushSamplesPathAction = pushSamplesPath.build();
        pickupFromPushingPathAction = pickupFromPushingPath.build();
        hookFromPickupWithTimedElbowAndArmPathAction1 = hookFromPickupWithTimedElbowAndArmPath1.build();
        toPickupFromChamberPathAction1 = toPickupFromChamberPath1.build();
        hookFromPickupWithTimedElbowAndArmPathAction2 = hookFromPickupWithTimedElbowAndArmPath2.build();
    }

    public SequentialAction fullSequence()
    {
        return new SequentialAction
        (
            approachChamberPreload(),
            atChamber(),
            pushingActions(),
            toPickupFromPushing(),
            atPickup(),
            nonPreloadHook(hookFromPickupWithTimedElbowAndArmPathAction1),
            atChamber(),
            toPickupFromChamber(toPickupFromChamberPathAction1),
            atPickup(),
            nonPreloadHook(hookFromPickupWithTimedElbowAndArmPathAction2),
            atChamber(),
            // TODO: ADD 4TH SPECIMEN PICKUP AND HOOK
            resetForTeleOp()
        );
    }


    public static double tR(double degrees)
    {
        return Math.toRadians(degrees);
    }

     */
}
