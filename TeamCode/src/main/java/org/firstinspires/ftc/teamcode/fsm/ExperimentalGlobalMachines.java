package org.firstinspires.ftc.teamcode.fsm;

import android.app.usage.NetworkStats;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

import com.sfdev.assembly.state.State;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Button;

public class ExperimentalGlobalMachines
{
    public Intake intake;
    public Outtake outtake;

    public Gamepads gamepads;

    public StateMachine intakeFSM;
    public StateMachine intakeToBucketFSM;
    public StateMachine bucketFSM;
    public StateMachine bucketToSpecimenFSM;
    public StateMachine specimenFSM;
    public StateMachine specimenToBucketFSM;
    public StateMachine bucketToIntakeFSM;
    public StateMachine specimenToIntakeFSM;
    public StateMachine intakeToDropFSM;
    public StateMachine dropToBucketFSM;

    public double triggerThreshold = 0.6;

    public ExperimentalGlobalMachines(Intake intake, Outtake outtake, Gamepads gamepads)
    {
        this.intake = intake;
        this.outtake = outtake;
        this.gamepads = gamepads;

        intakeFSM = getIntakeMachine();
        intakeToBucketFSM = getIntakeToBucketMachine();
        intakeToDropFSM = getIntakeToDropMachine();
        dropToBucketFSM = getDropToBucketMachine();
        bucketFSM = getBucketMachine();
        bucketToSpecimenFSM = getBucketToSpecimenMachine();
    }

    public enum GlobalStates
    {
        INIT,
        INTAKE,
        INTAKE_TO_DROP,
        DROP_TO_BUCKET, // actually for specimen wall pickup - make sure to retract h slides
        INTAKE_TO_BUCKET,
        BUCKET,
        BUCKET_TO_SPECIMEN,
        SPECIMEN,
        SPECIMEN_TO_BUCKET,
        BUCKET_TO_INTAKE,
        SPECIMEN_TO_INTAKE
    }

    public enum IntakeStates
    {
        RETRACT_ARM,
        LOW_EXTEND_CLAW_OPEN,
        ARM_GRAB,
        CLAW_GRAB,
        ARM_BACK_UP,
        RETRACT_ARM_CENTER_SWIVEL,
        RETRACT_SLIDES,
        READY_TO_TRANSFER,
        READY_TO_DROP
    }

    public StateMachine getIntakeMachine() {
        return new StateMachineBuilder()
                .state(IntakeStates.RETRACT_ARM)
                .onEnter(() -> {
                    intake.getArm().retract();
                    intake.getSwivel().center();
                })
                .loop( () -> intake.updateExtendoPowerExperimental(gamepads))
                .transition( () -> intake.getExtendo().inExtendZone())
                .state(IntakeStates.LOW_EXTEND_CLAW_OPEN)
                .onEnter(() -> {
                    intake.getArm().extendExperimental();
                    intake.getClaw().open();
                })
                .loop(() -> {
                    intake.updateExtendoPowerExperimental(gamepads);
                    intake.updateSwivelExperimental(gamepads);
                })
                .transition(() -> gamepads.justPressed(Button.GP1_Y))
                .state(IntakeStates.ARM_GRAB)
                .onEnter(() -> {
                    intake.getArm().probe();
                })
                .transitionTimed(0.2)
                .state(IntakeStates.CLAW_GRAB)
                .onEnter(() -> {
                    intake.getClaw().close();
                })
                .transitionTimed(0.15)
                .state(IntakeStates.ARM_BACK_UP)
                .onEnter(() -> {
                    intake.getArm().extendExperimental();
                })
                .transition(() -> gamepads.justPressed(Button.GP1_Y), IntakeStates.LOW_EXTEND_CLAW_OPEN)
                .transition(() -> gamepads.justPressed(Button.GP1_B), IntakeStates.RETRACT_ARM_CENTER_SWIVEL)
                .transition(() -> gamepads.justPressed(Button.GP1_A), IntakeStates.READY_TO_DROP)

                .state(IntakeStates.RETRACT_ARM_CENTER_SWIVEL)
                .onEnter(() -> {
                    intake.getArm().retract();
                    intake.getSwivel().center();
                })
                .transitionTimed(0.2)

                .state(IntakeStates.RETRACT_SLIDES)
                .onEnter(() -> intake.getExtendo().retractHorizontalExtendo())
                .transition(() -> intake.getExtendo().retracted(), IntakeStates.READY_TO_TRANSFER)
                .onExit(() -> intake.getExtendo().brakeSlides())

                .state(IntakeStates.READY_TO_TRANSFER)

                .state(IntakeStates.READY_TO_DROP)
                .build();
    }

    public enum IntakeToBucketStates
    {
        OPEN_OUTTAKE_CLAW,
        TRANSFER_OUTTAKE_ARM, // 0.37 s
        CLOSE_OUTTAKE_CLAW,
        OPEN_INTAKE_CLAW,
        MOVE_SLIDES_FOR_CLEARANCE,
        BUCKET_BACK_ARM,
        BUCKET_BACK_ELBOW_LOWER_SLIDES,
        AT_BUCKET
    }

    public StateMachine getIntakeToBucketMachine()
    {
        return new StateMachineBuilder()
                .state(IntakeToBucketStates.OPEN_OUTTAKE_CLAW)
                .onEnter(() -> {
                    outtake.getClaw().open();
                })
                .transitionTimed(0.10)

                .state(IntakeToBucketStates.TRANSFER_OUTTAKE_ARM)
                .onEnter(() -> {
                    outtake.getArm().transfer();
                })
                .transitionTimed(0.4)

                .state(IntakeToBucketStates.CLOSE_OUTTAKE_CLAW)
                .onEnter( () -> outtake.getClaw().close())
                .transitionTimed(0.15)

                .state(IntakeToBucketStates.OPEN_INTAKE_CLAW)
                .onEnter( () -> intake.getClaw().open())
                .transitionTimed(0.15)

                .state(IntakeToBucketStates.MOVE_SLIDES_FOR_CLEARANCE)
                .onEnter(() -> outtake.getExtendo().createBucketClearance())
                .transition(() -> outtake.getExtendo().clearanceMade())
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(IntakeToBucketStates.BUCKET_BACK_ARM)
                .onEnter( () -> outtake.getArm().clip())
                .transitionTimed(0.5)

                .state(IntakeToBucketStates.BUCKET_BACK_ELBOW_LOWER_SLIDES)
                .onEnter( () -> {
                    outtake.getElbow().clip();
                    outtake.getExtendo().setBottom();
                })
                .transition(() -> outtake.getExtendo().reachedBottom())
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(IntakeToBucketStates.AT_BUCKET)
                .build();
    }

    public enum IntakeToDropStates
    {
        MOVE_ARM_DOWN,
        OPEN_CLAW,
        MOVE_ARM_BACK_UP,
        CENTER_SWIVEL_RETRACT_ARM_RETRACT_SLIDES,
        DROP_COMPLETE
    }

    public StateMachine getIntakeToDropMachine()
    {
        return new StateMachineBuilder()
                .state(IntakeToDropStates.MOVE_ARM_DOWN)
                .onEnter(() -> intake.getArm().probe())
                .transitionTimed(0.35)

                .state(IntakeToDropStates.OPEN_CLAW)
                .onEnter(() -> intake.getClaw().open())
                .transitionTimed(0.13)

                .state(IntakeToDropStates.MOVE_ARM_BACK_UP)
                .onEnter(() -> intake.getArm().extendExperimental())
                .transitionTimed(0.4)

                .state(IntakeToDropStates.CENTER_SWIVEL_RETRACT_ARM_RETRACT_SLIDES)
                .onEnter(() -> {
                    intake.getSwivel().center();
                    intake.getArm().retract();
                    intake.getExtendo().retractHorizontalExtendo();
                })
                .transition(() -> intake.getExtendo().isRetracted())
                .onExit(() -> intake.getExtendo().brakeSlides())

                .state(IntakeToDropStates.DROP_COMPLETE)
                .build();
    }

    public enum DropToBucketStates
    {
        CLOSE_BACK_CLAW,
        MOVE_SLIDES_CLEARANCE,
        ELBOW_BUCKET_ARM_BUCKET,
        MOVE_SLIDES_DOWN,
        OPEN_CLAW,
        AT_BUCKET
    }

    public StateMachine getDropToBucketMachine()
    {
        return new StateMachineBuilder()
                .state(DropToBucketStates.CLOSE_BACK_CLAW)
                .onEnter(() -> outtake.getClaw().close())
                .transitionTimed(0.15)

                .state(DropToBucketStates.MOVE_SLIDES_CLEARANCE)
                .onEnter(() -> outtake.getExtendo().createBucketClearance())
                .transition(() -> outtake.getExtendo().clearanceMade())
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(DropToBucketStates.ELBOW_BUCKET_ARM_BUCKET)
                .onEnter(() -> {
                    outtake.getElbow().bucket();
                    outtake.getArm().bucket();
                })
                .transitionTimed(1.05)

                .state(DropToBucketStates.MOVE_SLIDES_DOWN)
                .onEnter(() -> outtake.getExtendo().setBottom())
                .transition(() -> outtake.getExtendo().reachedBottom())
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(DropToBucketStates.OPEN_CLAW)
                .onEnter(() -> outtake.getClaw().open())
                .transitionTimed(0.1)

                .state(DropToBucketStates.AT_BUCKET)
                .build();
    }

    public enum BucketStates
    {
        FUNCTIONS_ACTIVE, // should be able to use slides, claw
        RAISING_TO_BUCKET, // shouldn't be able to use slides, but can use claw
        LOWERING_TO_BOTTOM,// shouldn't be able to use slides, but can use claw
        EXITING_TO_INTAKE,
        EXITING_TO_SPECIMEN,
        READY_FOR_INTAKE,
        READY_FOR_SPECIMEN
    }

    public StateMachine getBucketMachine()
    {
        return new StateMachineBuilder()
                .state(BucketStates.FUNCTIONS_ACTIVE)
                .loop(() ->{
                    outtake.updateExtendoPowerExperimental(gamepads);
                    outtake.updateClawExperimental(gamepads);
                } )
                .transition(() -> gamepads.justPressed(Button.GP1_DPAD_UP), BucketStates.RAISING_TO_BUCKET)
                .transition(() -> gamepads.justPressed(Button.GP1_DPAD_DOWN), BucketStates.LOWERING_TO_BOTTOM)
                .transition(() -> gamepads.withinThreshold(Analog.GP1_RIGHT_TRIGGER, triggerThreshold),
                        BucketStates.EXITING_TO_SPECIMEN) // needs work - multiple diff options
                .transition(() -> gamepads.justPressed(Button.GP1_B),
                        BucketStates.EXITING_TO_INTAKE)

                .state(BucketStates.RAISING_TO_BUCKET)
                .onEnter(() -> outtake.getExtendo().setHighBucket())
                .loop(() -> outtake.updateClawExperimental(gamepads))
                .transition(() -> outtake.getExtendo().reachedHighBucket(), BucketStates.FUNCTIONS_ACTIVE)
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(BucketStates.LOWERING_TO_BOTTOM)
                .onEnter(() -> outtake.getExtendo().setBottom())
                .loop(() -> outtake.updateClawExperimental(gamepads))
                .transition(() -> outtake.getExtendo().reachedBottom(), BucketStates.FUNCTIONS_ACTIVE)
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(BucketStates.EXITING_TO_INTAKE)
                .onEnter(() -> outtake.getExtendo().setBottom())
                .transition(() -> outtake.getExtendo().reachedBottom(), BucketStates.READY_FOR_INTAKE)
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(BucketStates.READY_FOR_INTAKE)

                .state(BucketStates.EXITING_TO_SPECIMEN)
                .onEnter(() -> outtake.getExtendo().setBottom())
                .transition(() -> outtake.getExtendo().reachedBottom(), BucketStates.READY_FOR_SPECIMEN)
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(BucketStates.READY_FOR_SPECIMEN)

                .build();
    }

    public enum BucketToSpecimenStates
    {
        MOVE_SLIDES_CLEARANCE,
        MOVE_ARM_ELBOW_HOOK,
        READY_FOR_SPECIMEN // slides aren't fully retracted because it's unnecessary
    }

    public StateMachine getBucketToSpecimenMachine()
    {
        return new StateMachineBuilder()
                .state(BucketToSpecimenStates.MOVE_SLIDES_CLEARANCE)
                .onEnter(() -> outtake.getExtendo().createBucketClearance())
                .transition(() -> outtake.getExtendo().clearanceMade())
                .onExit(() -> outtake.getExtendo().brakeSlides())

                .state(BucketToSpecimenStates.MOVE_ARM_ELBOW_HOOK)
                .onEnter(() -> {
                    outtake.getArm().transfer();
                    outtake.getElbow().frontHook();
                })
                .transitionTimed(1)

                .state(BucketToSpecimenStates.READY_FOR_SPECIMEN)

                .build();
    }


    public StateMachine getGlobalMachines()
    {
        return new StateMachineBuilder()
                .state(GlobalStates.INIT)
                .onEnter(() ->
                {
                    intake.getArm().retract();
                    intake.getClaw().open();
                    intake.getSwivel().center();
                    outtake.getArm().rest();
                    outtake.getElbow().transfer();
                    outtake.getClaw().open();
                })
                .transitionTimed(1)

                .state(GlobalStates.INTAKE)
                .onEnter(() -> intakeFSM.start())
                .loop(() -> intakeFSM.update())
                .transition(() -> (intakeFSM.getState() == IntakeStates.READY_TO_TRANSFER),
                        GlobalStates.INTAKE_TO_BUCKET)
                .transition(() -> ((intakeFSM.getState() == IntakeStates.READY_TO_DROP)),
                        GlobalStates.INTAKE_TO_DROP)
                .onExit(() ->
                    {   intakeFSM.stop();
                        intakeFSM.reset(); }
                )

                .state(GlobalStates.INTAKE_TO_BUCKET)
                .onEnter(() -> { intakeToBucketFSM.start();
                })
                .loop(() -> intakeToBucketFSM.update())
                .transition(() -> intakeToBucketFSM.getState() == IntakeToBucketStates.AT_BUCKET, GlobalStates.BUCKET)
                .onExit(() ->
                        {   intakeToBucketFSM.stop();
                            intakeToBucketFSM.reset(); }
                )

                .state(GlobalStates.INTAKE_TO_DROP)
                .onEnter(() -> intakeToDropFSM.start())
                .loop(() -> intakeToDropFSM.update())
                .transition(() -> intakeToDropFSM.getState() == IntakeToDropStates.DROP_COMPLETE, GlobalStates.DROP_TO_BUCKET)
                .onExit(() -> {
                    intakeToDropFSM.stop();
                    intakeToDropFSM.reset();
                })

                .state(GlobalStates.DROP_TO_BUCKET)
                .onEnter(() -> dropToBucketFSM.start())
                .loop(() -> dropToBucketFSM.update())
                .transition(() -> dropToBucketFSM.getState() == DropToBucketStates.AT_BUCKET, GlobalStates.BUCKET)
                .onExit(() -> {
                    dropToBucketFSM.stop();
                    dropToBucketFSM.reset();
                })

                .state(GlobalStates.BUCKET)
                .onEnter(() -> bucketFSM.start())
                .loop(() -> bucketFSM.update())
                .transition(() -> bucketFSM.getState() == BucketStates.READY_FOR_SPECIMEN,
                        GlobalStates.BUCKET_TO_SPECIMEN)
                .transition(() -> bucketFSM.getState() == BucketStates.READY_FOR_INTAKE,
                        GlobalStates.BUCKET_TO_INTAKE)
                .onExit(() -> {
                    bucketFSM.stop();
                    bucketFSM.reset();
                })

                .state(GlobalStates.BUCKET_TO_SPECIMEN)
                .onEnter(() -> bucketToSpecimenFSM.start())
                .loop(() -> bucketToSpecimenFSM.update())
                .transition(() -> bucketToSpecimenFSM.getState() == BucketToSpecimenStates.READY_FOR_SPECIMEN, GlobalStates.SPECIMEN)
                .onExit(() -> {
                    bucketToSpecimenFSM.stop();
                    bucketToSpecimenFSM.reset();
                })

                .state(GlobalStates.BUCKET_TO_INTAKE)
                .state(GlobalStates.SPECIMEN)



                .build();
    }
}
