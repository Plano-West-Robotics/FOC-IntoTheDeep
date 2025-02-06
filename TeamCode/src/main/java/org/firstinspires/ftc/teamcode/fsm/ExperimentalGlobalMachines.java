package org.firstinspires.ftc.teamcode.fsm;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

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
        ARM_BACK_UP
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
                    intake.updateSwivel(gamepads);
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
                .onExit(() -> intake.getArm().retract())
                .transition(() -> gamepads.justPressed(Button.GP1_Y), IntakeStates.LOW_EXTEND_CLAW_OPEN)
                .build();
    }

    public enum IntakeToBucketStates
    {
        RETRACT_SLIDES_CENTER_SWIVEL_OPEN_OUTTAKE_CLAW, // 1.1 s
        TRANSFER_OUTTAKE_ARM, // 0.37 s
        CLOSE_OUTTAKE_CLAW,
        OPEN_INTAKE_CLAW,
        BUCKET_BACK_ARM,
        BUCKET_BACK_ELBOW_UPRIGHT_FRONT_ARM,
        AT_BUCKET
    }

    public StateMachine getIntakeToBucketMachine()
    {
        return new StateMachineBuilder()
                .state(IntakeToBucketStates.RETRACT_SLIDES_CENTER_SWIVEL_OPEN_OUTTAKE_CLAW)
                .onEnter(() -> {
                    intake.getExtendo().retractHorizontalExtendo();
                    intake.getSwivel().center();
                    outtake.getClaw().open();
                })
                .transition(() -> intake.getExtendo().isRetracted())

                .state(IntakeToBucketStates.TRANSFER_OUTTAKE_ARM)
                .onEnter(() -> {
                    outtake.getArm().transfer();
                })
                .transitionTimed(0.37)

                .state(IntakeToBucketStates.CLOSE_OUTTAKE_CLAW)
                .onEnter( () -> outtake.getClaw().close())
                .transitionTimed(0.13)

                .state(IntakeToBucketStates.OPEN_INTAKE_CLAW)
                .onEnter( () -> intake.getClaw().open())
                .transitionTimed(0.13)

                .state(IntakeToBucketStates.BUCKET_BACK_ARM)
                .onEnter( () -> outtake.getArm().clip())
                .transitionTimed(0.1)

                .state(IntakeToBucketStates.BUCKET_BACK_ELBOW_UPRIGHT_FRONT_ARM)
                .onEnter( () -> {
                    outtake.getElbow().clip();
                    intake.getArm().upright();
                })
                .transitionTimed(0.4)

                .state(IntakeToBucketStates.AT_BUCKET)
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
                .transition(() -> ((intakeFSM.getState() == IntakeStates.ARM_BACK_UP) && (gamepads.justPressed(Button.GP1_B))),
                        GlobalStates.INTAKE_TO_BUCKET)
                .transition(() -> ((intakeFSM.getState() == IntakeStates.ARM_BACK_UP) &&
                                (gamepads.withinThreshold(Analog.GP1_RIGHT_TRIGGER, triggerThreshold))),
                        GlobalStates.INTAKE_TO_DROP)
                .onExit(() ->
                    {   intakeFSM.stop();
                        intakeFSM.reset(); }
                )

                .state(GlobalStates.INTAKE_TO_BUCKET)
                .onEnter(() -> { intakeToBucketFSM.start();
                })
                .loop(() -> intakeToBucketFSM.update())
                .transition(() -> intakeToBucketFSM.getState() == IntakeToBucketStates.AT_BUCKET)
                .onExit(() ->
                        {   intakeToBucketFSM.stop();
                            intakeToBucketFSM.reset(); }
                )


                .build();
    }
}
