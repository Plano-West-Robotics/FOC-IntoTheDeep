package org.firstinspires.ftc.teamcode.auto.experimental;

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

public class SpecimenAutoActionsExperimental {

    public Intake intake;
    public Outtake outtake;
    public MecanumDrive drive;

    public SpecimenAutoActionsExperimental(Intake intake, Outtake outtake, MecanumDrive drive){
        this.intake = intake;
        this.outtake = outtake;
        this.drive = drive;
    }

    public Action setIntakeExtend() { return intake.getArm().extend(250); }
    public Action setSwivelCenter() { return intake.getSwivel().center(300); }
    public Action setBackClawClose() { return outtake.getClaw().tightClose(60); } // TODO: need to adjust if it messes stuff up - also lower hc3
    public Action setElbowHook(){ return outtake.getElbow().frontHook(300); }
    public Action setArmHook(){ return outtake.getArm().transfer(450); }
    public Action setIntakeRetract(){ return intake.getArm().retract(400); }
    public Action setSlidesToUnderChamber(){ return outtake.getExtendo().hc2(); }
    public Action setSlidesToAboveChamber(){ return outtake.getExtendo().hc3(); }
    public Action setBackClawOpen(){ return outtake.getClaw().open(60); } // TODO: Same
    public Action setElbowWall(){ return outtake.getElbow().wall(300); }
    public Action setArmWall(){ return outtake.getArm().wall(350); }
    public Action setSlidesToBottom(){ return outtake.getExtendo().lowerFully(); }
    public Action setArmHookUp(){ return outtake.getArm().hookUp(100); }
    public Action setElbowHookUp(){ return outtake.getElbow().hookUp(100); }

    public TrajectoryActionBuilder moveToChamberPath;
    public TrajectoryActionBuilder pushSamplesPath;
    public TrajectoryActionBuilder pickupFromPushingPath;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath1;
    public TrajectoryActionBuilder toPickupFromChamberPath1;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath2;
    public TrajectoryActionBuilder toPickupFromChamberPath2;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath3;
    public TrajectoryActionBuilder toPickupFromChamberPath3;
    public TrajectoryActionBuilder hookFromPickupWithTimedElbowAndArmPath4;

    public Action moveToChamberPathAction;
    public Action pushSamplesPathAction;
    public Action pickupFromPushingPathAction;
    public Action hookFromPickupWithTimedElbowAndArmPathAction1;
    public Action toPickupFromChamberPathAction1;
    public Action hookFromPickupWithTimedElbowAndArmPathAction2;
    public Action toPickupFromChamberPathAction2;
    public Action hookFromPickupWithTimedElbowAndArmPathAction3;
    public Action toPickupFromChamberPathAction3;
    public Action hookFromPickupWithTimedElbowAndArmPathAction4;

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
                new ParallelAction(setSlidesToAboveChamber(), setArmHookUp(), setElbowHookUp()),
                setBackClawOpen()
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
        Pose2d initPose = new Pose2d(0, -63, Math.toRadians(90));

        moveToChamberPath = drive.actionBuilder(initPose).splineToConstantHeading(new Vector2d(0, -28), tR(90));
        Pose2d moveToChamberPathFinalPose = new Pose2d(0, -28, tR(90));

        pushSamplesPath = drive.actionBuilder(moveToChamberPathFinalPose)
                .splineToConstantHeading(new Vector2d(0, -28), tR(90)) // go to the bar with preload 1
                .splineToConstantHeading(new Vector2d(12, -42), tR(0)) // intermediate stage in spline
                .splineToConstantHeading(new Vector2d(33, -36), tR(90)) // reached in between submersible and sample
                .splineToConstantHeading(new Vector2d(42, -16), 0)
                .splineToConstantHeading(new Vector2d(47, -20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(47, -36), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(47, -16), tR(90))
                .splineToConstantHeading(new Vector2d(50, -10), 0)
                .splineToConstantHeading(new Vector2d(54, -14), tR(270))
                .splineToConstantHeading(new Vector2d(54, -40), tR(270))
                .splineToConstantHeading(new Vector2d(54, -14), tR(90))
                .splineToConstantHeading(new Vector2d(57, -10), tR(0))
                .splineToConstantHeading(new Vector2d(59, -16), tR(270))
                .splineToConstantHeading(new Vector2d(59, -40), tR(270));
        Pose2d pushSamplesPathFinalPose = new Pose2d(59, -40, tR(90));

        pickupFromPushingPath = drive.actionBuilder(pushSamplesPathFinalPose)
                .splineToConstantHeading(new Vector2d(57, -50), tR(180)) // intermediate to pickup
                .splineToConstantHeading(new Vector2d(53, -64.3), tR(270));
        Pose2d pickupFromPushingPathFinalPose = new Pose2d(53, -64.3, tR(90));

        hookFromPickupWithTimedElbowAndArmPath1 = drive.actionBuilder(pickupFromPushingPathFinalPose)
                .afterTime(0.7, new ParallelAction(setElbowHook(), setArmHook()))
                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(2, -27), tR(90));

        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose1 = new Pose2d(2, -27, tR(90));

        toPickupFromChamberPath1 = drive.actionBuilder(hookFromPickupWithTimedElbowAndArmPathFinalPose1)
                .setTangent(tR(270))
                .splineToConstantHeading(new Vector2d(38, -64.3), tR(270));
        Pose2d toPickupFromChamberPathFinalPose1 = new Pose2d(38, -64.3, tR(90));

        hookFromPickupWithTimedElbowAndArmPath2 = drive.actionBuilder(toPickupFromChamberPathFinalPose1)
                .afterTime(0.7, new ParallelAction(setElbowHook(), setArmHook()))
                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(3, -27), tR(90));
        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose2 = new Pose2d(3, -27, tR(90));

        toPickupFromChamberPath2 = drive.actionBuilder(hookFromPickupWithTimedElbowAndArmPathFinalPose2)
                .setTangent(tR(270))
                .splineToConstantHeading(new Vector2d(38, -64.3), tR(270));
        Pose2d toPickupFromChamberPathFinalPose2 = new Pose2d(38, -64.3, tR(90));

        hookFromPickupWithTimedElbowAndArmPath3 = drive.actionBuilder(toPickupFromChamberPathFinalPose2)
                .afterTime(0.7, new ParallelAction(setElbowHook(), setArmHook()))
                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(5, -27), tR(90));
        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose3 = new Pose2d(5, -27, tR(90));

        toPickupFromChamberPath3 = drive.actionBuilder(hookFromPickupWithTimedElbowAndArmPathFinalPose3)
                .setTangent(tR(270))
                .splineToConstantHeading(new Vector2d(38, -64.3), tR(270));
        Pose2d toPickupFromChamberPathFinalPose3 = new Pose2d(38, -64.3, tR(90));

        hookFromPickupWithTimedElbowAndArmPath4 = drive.actionBuilder(toPickupFromChamberPathFinalPose3)
                .afterTime(0.7, new ParallelAction(setElbowHook(), setArmHook()))
                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(6, -27), tR(90));
        Pose2d hookFromPickupWithTimedElbowAndArmPathFinalPose4 = new Pose2d(6, -27, tR(90));

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
        toPickupFromChamberPathAction3 = toPickupFromChamberPath3.build();
        hookFromPickupWithTimedElbowAndArmPathAction4 = hookFromPickupWithTimedElbowAndArmPath4.build();
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
            atChamber(),
                // 5th specimen
                toPickupFromChamber(toPickupFromChamberPathAction3),
                atPickup(),
                nonPreloadHook(hookFromPickupWithTimedElbowAndArmPathAction4),
                atChamber(),
            resetForTeleOp() // need to remove - make the offset thing with Faizan
        );
    }


    public static double tR(double degrees)
    {
        return Math.toRadians(degrees);
    }
}
