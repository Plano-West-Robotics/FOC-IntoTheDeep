package org.firstinspires.ftc.teamcode.fsm;

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
        OPEN_CLAW
    }

    public StateMachine getDropToBucketMachine()
    {
        return new StateMachineBuilder()

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


                .state(GlobalStates.BUCKET)




                .build();
    }
}
