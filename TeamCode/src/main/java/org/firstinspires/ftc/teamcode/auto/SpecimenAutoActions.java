package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public class SpecimenAutoActions {

    public Intake intake;
    public Outtake outtake;

    public MecanumDrive drive;
    public Pose2d initPose;

    public SpecimenAutoActions(Intake intake, Outtake outtake, MecanumDrive drive, Pose2d initPose){
        this.intake = intake;
        this.outtake = outtake;
        this.drive = drive;
        this.initPose = initPose;
    }

    public Action setBackClawClose() { return outtake.getClaw().close(120); };
    public Action setElbowHook(){ return outtake.getElbow().frontHook(300); };
    public Action setArmHook(){ return outtake.getArm().transfer(450); };
    public Action setIntakeUpright(){ return intake.getArm().upright(400); };
    public Action setSlidesToUnderChamber(){ return outtake.getExtendo().hc2(); }; // TODO: TUNE PIDF FOR THE SLIDES TO BE FASTER
    public Action setSlidesToAboveChamber(){ return outtake.getExtendo().hc3(); };
    public Action setBackClawOpen(){ return outtake.getClaw().open(120); };
    public Action setElbowWall(){ return outtake.getElbow().wall(300); };
    public Action setArmWall(){ return outtake.getArm().wall(450); };
    public Action setSlidesToBottom(){ return outtake.getExtendo().lowerFully(); };

    public TrajectoryActionBuilder moveToChamberPath;
    public TrajectoryActionBuilder pushSamplesPath;
    public TrajectoryActionBuilder pickupFromPushingPath;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath1;
    public TrajectoryActionBuilder toPickupFromChamberPath1;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath2;

    public Action moveToChamberPathAction;
    public Action pushSamplesPathAction;
    public Action pickupFromPushingPathAction;
    public Action hookFromPickupWithTimedElbowAndArmPathAction1;
    public Action toPickupFromChamberPathAction1;
    public Action hookFromPickupWithTimedElbowAndArmPathAction2;

    // At end of auto, transitioning into TeleOp
    public Action resetHorizontalSlides(){ return intake.getExtendo().retract(); };
    public Action resetVerticalSlides(){ return outtake.getExtendo().lowerFully(); };
    public Action resetIntakeArm(){ return intake.getArm().retract(300); };
    public Action resetOuttakeArm(){ return outtake.getArm().rest(400); };
    public Action resetElbow(){ return outtake.getElbow().transfer(300); };

    public SequentialAction initPreload()
    {
        return new SequentialAction
        (
            setBackClawClose(), setElbowHook(), setArmHook(), setIntakeUpright()
        );
    }

    public ParallelAction approachChamberPreload()
    {
        return new ParallelAction
        (
                setSlidesToUnderChamber(), moveToChamberPathAction
        );
    }

    public SequentialAction atChamber()
    {
        return new SequentialAction
        (
                setSlidesToAboveChamber(), setBackClawOpen()
        );
    }

    public ParallelAction pushingActions()
    {
        return new ParallelAction
                (
                        pushSamplesPathAction, setSlidesToBottom(), setElbowWall(), setArmWall()
                );
    }

    public SequentialAction toPickupFromPushing()
    {
        return new SequentialAction
                (
                        pickupFromPushingPathAction
                );
    }

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

    //plan - return a sequential action that contains everything - pass in trajectory action
    // init should have a separate one


    public void createTrajectories()
    {
        // TODO: MAKE SURE TO TUNE THE Y POSITION OF THE CHAMBER - SHOULD CHANGE IT FOR ALL FOLLOWING TRAJECTORIES TOO
        moveToChamberPath = drive.actionBuilder(initPose).strafeToConstantHeading(new Vector2d(0, -28));
        Pose2d moveToChamberPathFinalPose = new Pose2d(0, -28, tR(90));
        // TODO: MAKE THE ROBOT GO STRAIGHT TO THE BAR FOR THE PRELOAD HOOK AND
        //       MAKE THE ROBOT GO AS FAR TO THE RIGHT AS POSSIBLE WITHOUT OVERLAPPING THE HOOKS
        //       FOR EACH OF THE SPECIMENS (WHEN THE ODO GETS FIXED)

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
}
