package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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

    public Action setIntakeExtend() { return intake.getArm().extend(250); }
    public Action setSwivelCenter() { return intake.getSwivel().center(300); }
    public Action setBackClawClose() { return outtake.getClaw().close(120); }
    public Action setElbowHook(){ return outtake.getElbow().frontHook(300); }
    public Action setArmHook(){ return outtake.getArm().transfer(450); }
    public Action setIntakeRetract(){ return intake.getArm().retract(400); }
    public Action setSlidesToUnderChamber(){ return outtake.getExtendo().hc2(); }
    public Action setSlidesToAboveChamber(){ return outtake.getExtendo().hc3(); }
    public Action setBackClawOpen(){ return outtake.getClaw().open(120); }
    public Action setElbowWall(){ return outtake.getElbow().wall(300); }
    public Action setArmWall(){ return outtake.getArm().wall(450); }
    public Action setSlidesToBottom(){ return outtake.getExtendo().lowerFully(); }

    public TrajectoryActionBuilder moveToChamberPath;
    public TrajectoryActionBuilder pushSamplesPath;
    public TrajectoryActionBuilder pickupFromPushingPath;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath1;
    public TrajectoryActionBuilder toPickupFromChamberPath1;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath2;
    public TrajectoryActionBuilder toPickupFromChamberPath2;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath3;

    public Action moveToChamberPathAction;
    public Action pushSamplesPathAction;
    public Action pickupFromPushingPathAction;
    public Action hookFromPickupWithTimedElbowAndArmPathAction1;
    public Action toPickupFromChamberPathAction1;
    public Action hookFromPickupWithTimedElbowAndArmPathAction2;
    public Action toPickupFromChamberPathAction2;
    public Action hookFromPickupWithTimedElbowAndArmPathAction3;

    // At end of auto, transitioning into TeleOp
    public Action resetHorizontalSlides(){ return intake.getExtendo().retract(); }
    public Action resetVerticalSlides(){ return outtake.getExtendo().lowerFully(); }
    public Action resetIntakeArm(){ return intake.getArm().retract(300); }
    public Action resetOuttakeArm(){ return outtake.getArm().rest(400); }
    public Action resetElbow(){ return outtake.getElbow().transfer(300); }

    public SequentialAction initPreload()
    {
        return new SequentialAction
        (
                new ParallelAction(setSwivelCenter(), setIntakeExtend(), setBackClawClose()),
                new ParallelAction(setElbowHook(), setArmHook()),
                setIntakeRetract()
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
        moveToChamberPath = drive.actionBuilder(initPose).strafeToConstantHeading(new Vector2d(-4, -28));
        Pose2d moveToChamberPathFinalPose = new Pose2d(-4, -28, tR(90));
        // TODO: MAKE THE ROBOT GO STRAIGHT TO THE BAR FOR THE PRELOAD HOOK AND
        //       MAKE THE ROBOT GO AS FAR TO THE RIGHT AS POSSIBLE WITHOUT OVERLAPPING THE HOOKS
        //       FOR EACH OF THE SPECIMENS (WHEN THE ODO GETS FIXED)

        pushSamplesPath = drive.actionBuilder(moveToChamberPathFinalPose)
                .setTangent(Math.toRadians(315))
                .splineToSplineHeading(new Pose2d(33, -36, tR(90)), tR(90))
                .splineToConstantHeading(new Vector2d(42, -16), 0)
                .splineToConstantHeading(new Vector2d(48, -20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -36), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -16), tR(90))
                .splineToConstantHeading(new Vector2d(51, -10), 0)
                .splineToConstantHeading(new Vector2d(54, -14), tR(270))
                .splineToConstantHeading(new Vector2d(54, -46), tR(270));
        Pose2d pushSamplesPathFinalPose = new Pose2d(54, -46, tR(90));

        pickupFromPushingPath = drive.actionBuilder(pushSamplesPathFinalPose)

                .splineToConstantHeading(new Vector2d(42, -64.3), Math.toRadians(270), new TranslationalVelConstraint(30), new ProfileAccelConstraint(-15, 20));
        Pose2d pickupFromPushingPathFinalPose = new Pose2d(42, -64.3, tR(90));

        hookFromPickupWithTimedElbowAndArmPath1 = drive.actionBuilder(pickupFromPushingPathFinalPose)
                .afterTime(1.1, new ParallelAction(setElbowHook(), setArmHook()))
                .strafeToConstantHeading(new Vector2d(-1, -27));

        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose1 = new Pose2d(-1, -27, tR(90));

        toPickupFromChamberPath1 = drive.actionBuilder(hookFromPickupWithTimedElbowAndArmPathFinalPose1)
                .strafeToConstantHeading(new Vector2d(38, -64.3));
        Pose2d toPickupFromChamberPathFinalPose1 = new Pose2d(38, -64.3, tR(90));

        hookFromPickupWithTimedElbowAndArmPath2 = drive.actionBuilder(toPickupFromChamberPathFinalPose1)
                .afterTime(1.1, new ParallelAction(setElbowHook(), setArmHook()))
                .strafeToConstantHeading(new Vector2d(2, -27));
        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose2 = new Pose2d(2, -27, tR(90));

        toPickupFromChamberPath2 = drive.actionBuilder(hookFromPickupWithTimedElbowAndArmPathFinalPose2)
                .strafeToConstantHeading(new Vector2d(38, -64.3));
        Pose2d toPickupFromChamberPathFinalPose2 = new Pose2d(38, -64.3, tR(90));

        hookFromPickupWithTimedElbowAndArmPath2 = drive.actionBuilder(toPickupFromChamberPathFinalPose2)
                .afterTime(1.1, new ParallelAction(setElbowHook(), setArmHook()))
                .strafeToConstantHeading(new Vector2d(5, -27));
        //Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose3 = new Pose2d(5, -27, tR(90));

    }

    public void buildPaths()
    {
        moveToChamberPathAction = moveToChamberPath.build();
        pushSamplesPathAction = pushSamplesPath.build();
        pickupFromPushingPathAction = pickupFromPushingPath.build();
        hookFromPickupWithTimedElbowAndArmPathAction1 = hookFromPickupWithTimedElbowAndArmPath1.build();
        toPickupFromChamberPathAction1 = toPickupFromChamberPath1.build();
        hookFromPickupWithTimedElbowAndArmPathAction2 = hookFromPickupWithTimedElbowAndArmPath2.build();
        toPickupFromChamberPathAction2 = toPickupFromChamberPath2.build();
        hookFromPickupWithTimedElbowAndArmPathAction3 = hookFromPickupWithTimedElbowAndArmPath3.build();
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
            toPickupFromChamber(toPickupFromChamberPathAction2),
            atPickup(),
            nonPreloadHook(hookFromPickupWithTimedElbowAndArmPathAction3),
            resetForTeleOp()
        );
    }


    public static double tR(double degrees)
    {
        return Math.toRadians(degrees);
    }
}
