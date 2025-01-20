package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.fsm.OuttakeMachines;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.RobotCentricDrive;

@TeleOp
public class MainTeleOp extends BaseTeleOp
{
    public RobotCentricDrive drive;
    public Intake intake;
    public Outtake outtake;

    public StateMachine robotFSM, intakeFSM, outtakeFSM, ioFSM, oiFSM, transferFSM;
    public StateMachine brFSM, rbFSM, crFSM, bcFSM, cbFSM; // Inner FSMs for RobotState.OUTTAKE.

    public double triggerThreshold = 0.75;

    public enum RobotState
    {
        INTAKE,
        OUTTAKE,
        INTAKE_TO_OUTTAKE,
        OUTTAKE_TO_INTAKE,
        TRANSFER
    }

    public enum IntakeState
    {
        RETRACT,
        EXTEND,
        PROBE
    }

    public enum OuttakeState
    {
        INIT, // Immediately transitions to BUCKET or CLIP state.
        BUCKET,
        REST,
        CLIP,

        BUCKET_TO_REST,
        REST_TO_BUCKET,
        CLIP_TO_REST,
        BUCKET_TO_CLIP,
        CLIP_TO_BUCKET
    }

    public enum IntakeToOuttakeState
    {
        UPRIGHT_FRONT_ARM_AND_CLOSE_BACK_CLAW,
        CLIP_BACK_ARM,
        CLIP_BACK_ELBOW,
        OPEN_BACK_CLAW,
        DONE
    }

    public enum OuttakeToIntakeState
    {
        RETRACT_FRONT_ARM,
        DONE
    }

    public enum TransferState
    {
        OPEN_BACK_CLAW_AND_TRANSFER_BACK_ARM,
        CLOSE_BACK_CLAW,
        OPEN_FRONT_CLAW,
        BUCKET_BACK_ARM,
        BUCKET_BACK_ELBOW_AND_UPRIGHT_FRONT_ARM,
        DONE
    }

    @Override
    public void setup()
    {
        drive = new RobotCentricDrive(hardware);
        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        brFSM = OuttakeMachines.getBucketToRestFSM(outtake);
        rbFSM = OuttakeMachines.getRestToBucketFSM(outtake);
        crFSM = OuttakeMachines.getClipToRestFSM(outtake);
        bcFSM = OuttakeMachines.getBucketToClipFSM(outtake);
        cbFSM = OuttakeMachines.getClipToBucketFSM(outtake);

        // Intake FSM.
        {
            intakeFSM = new StateMachineBuilder()
                    .state(IntakeState.RETRACT)
                    .onEnter( () -> {
                        intake.getArm().retract();
                        intake.getSwivel().center();
                    })
                    .loop( () -> {
                        intake.updateExtendoPower(gamepads);
                        intake.updateClaw(gamepads);
                    })
                    .transition( () -> intake.getExtendo().inExtendZone(), IntakeState.EXTEND)

                    .state(IntakeState.EXTEND)
                    .onEnter( () -> intake.getArm().extend())
                    .loop( () -> {
                        intake.updateExtendoPower(gamepads);
                        intake.updateClaw(gamepads);
                        intake.updateSwivel(gamepads);
                    })
                    .transition( () -> intake.getExtendo().inRetractZone(), IntakeState.RETRACT)
                    .transition( () ->
                        gamepads.getAnalogValue(Analog.GP2_LEFT_TRIGGER) >= triggerThreshold,
                        IntakeState.PROBE
                    )

                    .state(IntakeState.PROBE)
                    .onEnter( () -> intake.getArm().probe())
                    .loop( () -> {
                        intake.updateExtendoPower(gamepads);
                        intake.updateClaw(gamepads);
                        intake.updateSwivel(gamepads);
                    })
                    .transition( () -> intake.getExtendo().inRetractZone(), IntakeState.RETRACT)
                    .transition( () ->
                        gamepads.getAnalogValue(Analog.GP2_LEFT_TRIGGER) < triggerThreshold,
                        IntakeState.EXTEND
                    )

                    .build();
        }

        // Outtake FSM.
        {
            outtakeFSM = new StateMachineBuilder()
                    .state(OuttakeState.INIT)
                    .transition( () ->
                        outtake.getArm().atStage(BackArm.Stage.BUCKET),
                        OuttakeState.BUCKET
                    )
                    .transition( () ->
                        outtake.getArm().atStage(BackArm.Stage.REST),
                        OuttakeState.REST
                    )
                    .transition( () ->
                        outtake.getArm().atStage(BackArm.Stage.CLIP),
                        OuttakeState.CLIP
                    )

                    .state(OuttakeState.BUCKET)
                    .loop( () -> {
                        outtake.updateClaw(gamepads);
                        outtake.updateExtendoPower(gamepads);
                    })
                    .transition( () ->
                        gamepads.justPressed(Button.GP2_B),
                        OuttakeState.BUCKET_TO_REST
                    )
                    .transition( () ->
                        gamepads.justEnteredThreshold(Analog.GP2_LEFT_TRIGGER, triggerThreshold),
                        OuttakeState.BUCKET_TO_CLIP
                    )

                    .state(OuttakeState.REST)
                    .loop( () -> {
                        outtake.updateClaw(gamepads);
                        outtake.updateExtendoPower(gamepads);
                    })
                    .transition( () ->
                        gamepads.justEnteredThreshold(Analog.GP2_LEFT_TRIGGER, triggerThreshold),
                        OuttakeState.REST_TO_BUCKET
                    )

                    .state(OuttakeState.CLIP)
                    .loop( () -> {
                        outtake.updateClaw(gamepads);
                        outtake.updateExtendoPower(gamepads);
                    })
                    .transition( () ->
                        gamepads.justPressed(Button.GP2_B),
                        OuttakeState.CLIP_TO_REST
                    )
                    .transition( () ->
                        gamepads.justEnteredThreshold(Analog.GP2_LEFT_TRIGGER, triggerThreshold),
                        OuttakeState.CLIP_TO_BUCKET
                    )

                    .state(OuttakeState.BUCKET_TO_REST)
                    .onEnter( () -> brFSM.start())
                    .loop( () -> brFSM.update())
                    .transition( () ->
                        brFSM.getState() == OuttakeMachines.BRState.DONE,
                        OuttakeState.REST
                    )
                    .onExit( () -> {
                        brFSM.reset();
                        brFSM.stop();
                    })

                    .state(OuttakeState.REST_TO_BUCKET)
                    .onEnter( () -> rbFSM.start())
                    .loop( () -> rbFSM.update())
                    .transition( () ->
                        rbFSM.getState() == OuttakeMachines.RBState.DONE,
                        OuttakeState.BUCKET
                    )
                    .onExit( () -> {
                        rbFSM.reset();
                        rbFSM.stop();
                    })

                    .state(OuttakeState.CLIP_TO_REST)
                    .onEnter( () -> crFSM.start())
                    .loop( () -> crFSM.update())
                    .transition( () ->
                        crFSM.getState() == OuttakeMachines.CRState.DONE,
                        OuttakeState.REST
                    )
                    .onExit( () -> {
                        crFSM.reset();
                        crFSM.stop();
                    })

                    .state(OuttakeState.BUCKET_TO_CLIP)
                    .onEnter( () -> bcFSM.start())
                    .loop( () -> bcFSM.update())
                    .transition( () ->
                        bcFSM.getState() == OuttakeMachines.BCState.DONE,
                        OuttakeState.CLIP
                    )
                    .onExit( () -> {
                        bcFSM.reset();
                        bcFSM.stop();
                    })

                    .state(OuttakeState.CLIP_TO_BUCKET)
                    .onEnter( () -> cbFSM.start())
                    .loop( () -> cbFSM.update())
                    .transition( () ->
                        cbFSM.getState() == OuttakeMachines.CBState.DONE,
                        OuttakeState.BUCKET
                    )
                    .onExit( () -> {
                        cbFSM.reset();
                        cbFSM.stop();
                    })

                    .build();
        }

        // Intake to Outtake FSM.
        {
            ioFSM = new StateMachineBuilder()
                    .state(IntakeToOuttakeState.UPRIGHT_FRONT_ARM_AND_CLOSE_BACK_CLAW)
                    .onEnter( () -> {
                        intake.getArm().upright();
                        outtake.getClaw().close();
                    })
                    .transitionTimed(0.2)

                    .state(IntakeToOuttakeState.CLIP_BACK_ARM)
                    .onEnter( () -> outtake.getArm().clip())
                    .transitionTimed(0.4)

                    .state(IntakeToOuttakeState.CLIP_BACK_ELBOW)
                    .onEnter( () -> outtake.getElbow().clip())
                    .transitionTimed(0.45)

                    .state(IntakeToOuttakeState.OPEN_BACK_CLAW)
                    .onEnter( () -> outtake.getClaw().open())
                    .transitionTimed(0.15)

                    .state(IntakeToOuttakeState.DONE)

                    .build();
        }

        // Outtake to Intake FSM.
        {
            oiFSM = new StateMachineBuilder()
                    .state(OuttakeToIntakeState.RETRACT_FRONT_ARM)
                    .onEnter( () -> intake.getArm().retract())
                    .transitionTimed(0.4)

                    .state(OuttakeToIntakeState.DONE)

                    .build();
        }

        // Transfer FSM.
        {
            transferFSM = new StateMachineBuilder()
                    .state(TransferState.OPEN_BACK_CLAW_AND_TRANSFER_BACK_ARM)
                    .onEnter( () -> {
                        outtake.getClaw().open();
                        outtake.getArm().transfer();
                    })
                    .transitionTimed(1)

                    .state(TransferState.CLOSE_BACK_CLAW)
                    .onEnter( () -> outtake.getClaw().close())
                    .transitionTimed(0.2)

                    .state(TransferState.OPEN_FRONT_CLAW)
                    .onEnter( () -> intake.getClaw().open())
                    .transitionTimed(0.2)

                    .state(TransferState.BUCKET_BACK_ARM)
                    .onEnter( () -> outtake.getArm().bucket())
                    .transitionTimed(0.1)

                    .state(TransferState.BUCKET_BACK_ELBOW_AND_UPRIGHT_FRONT_ARM)
                    .onEnter( () -> {
                        outtake.getElbow().bucket();
                        intake.getArm().upright();
                    })
                    .transitionTimed(0.85)

                    .state(TransferState.DONE)

                    .build();
        }

        // Robot FSM.
        {
            robotFSM = new StateMachineBuilder()
                    .state(RobotState.INTAKE)
                    .onEnter( () -> intakeFSM.start())
                    .loop( () -> intakeFSM.update())
                    /*
                    Multiple transitions work like an if-elseif-elseif... statement in the order the
                    transitions are declared.
                     */
                    .transition( () ->
                        // If this evaluates to true, robotFSM will transition to a different state.
                        intakeFSM.getState() == IntakeState.RETRACT
                        && gamepads.getAnalogValue(Analog.GP2_RIGHT_TRIGGER) >= triggerThreshold
                        && gamepads.isPressed(Button.GP2_Y),
                        RobotState.TRANSFER // The next state to transition to.
                    )
                    .transition( () ->
                        intakeFSM.getState() == IntakeState.RETRACT
                        && gamepads.isPressed(Button.GP2_Y),
                        RobotState.INTAKE_TO_OUTTAKE
                    )
                    .onExit( () -> {
                        intake.getExtendo().halt();
                        intakeFSM.reset();
                        intakeFSM.stop();
                    })

                    .state(RobotState.OUTTAKE)
                    .onEnter( () -> outtakeFSM.start())
                    .loop( () -> outtakeFSM.update())
                    .transition( () ->
                        outtakeFSM.getState() == OuttakeState.REST
                        && gamepads.isPressed(Button.GP2_Y),
                        RobotState.OUTTAKE_TO_INTAKE
                    )
                    .onExit( () -> {
                        outtake.getExtendo().halt();
                        outtakeFSM.reset();
                        outtakeFSM.stop();
                    })

                    .state(RobotState.INTAKE_TO_OUTTAKE)
                    .onEnter( () -> ioFSM.start())
                    .loop( () -> ioFSM.update())
                    .transition( () ->
                        ioFSM.getState() == IntakeToOuttakeState.DONE,
                        RobotState.OUTTAKE
                    )
                    .onExit( () -> {
                        ioFSM.reset();
                        ioFSM.stop();
                    })

                    .state(RobotState.OUTTAKE_TO_INTAKE)
                    .onEnter( () -> oiFSM.start())
                    .loop( () -> oiFSM.update())
                    .transition( () ->
                        oiFSM.getState() == OuttakeToIntakeState.DONE,
                        RobotState.INTAKE
                    )
                    .onExit( () -> {
                        oiFSM.reset();
                        oiFSM.stop();
                    })

                    .state(RobotState.TRANSFER)
                    .onEnter( () -> transferFSM.start())
                    .loop( () -> transferFSM.update())
                    .transition( () ->
                        transferFSM.getState() == TransferState.DONE,
                        RobotState.OUTTAKE
                    )
                    .onExit( () -> {
                        transferFSM.reset();
                        transferFSM.stop();
                    })

                    .build();
        }
    }

    @Override
    public void start()
    {
        // Starting pose.
        intake.getArm().retract();
        intake.getClaw().open();
        intake.getSwivel().center();
        outtake.getArm().rest();
        outtake.getElbow().transfer();
        outtake.getClaw().open();

        robotFSM.start();
    }

    @Override
    public void run()
    {
        drive.update(gamepads);
        robotFSM.update();

        telemetry.addData("HL Position", intake.getExtendo().getLeft().getPosition());
        telemetry.addData("HR Position", intake.getExtendo().getRight().getPosition());

        telemetry.addData("VL Position", outtake.getExtendo().getLeft().getPosition());
        telemetry.addData("VR Position", outtake.getExtendo().getRight().getPosition());

        telemetry.addData("Robot State", robotFSM.getState());
        telemetry.addData("Intake State", intakeFSM.getState());
        telemetry.addData("Outtake State", outtakeFSM.getState());
        telemetry.addData("Intake to Outtake State", ioFSM.getState());
        telemetry.addData("Outtake to Intake State", oiFSM.getState());
        telemetry.addData("Transfer State", transferFSM.getState());
    }
}
