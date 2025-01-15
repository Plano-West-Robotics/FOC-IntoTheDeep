package org.firstinspires.ftc.teamcode.teleop;

import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.RobotCentricDrive;

@Photon
@TeleOp
public class MainTeleOp extends BaseTeleOp
{
    public RobotCentricDrive drive;
    public Intake intake;
    public Outtake outtake;

    public StateMachine robotFSM, intakeFSM, outtakeFSM, ioFSM, oiFSM, transferFSM;

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
        CLIP
    }

    public enum IntakeToOuttakeState
    {
        CLEAR_FRONT_ARM,
        CLIP_BACK_ARM,
        CLIP_BACK_ELBOW,
        DONE
    }

    public enum OuttakeToIntakeState
    {
        RETRACT_FRONT_ARM,
        DONE
    }

    public enum TransferState
    {
        OPEN_BACK_CLAW,
        TRANSFER_BACK_ARM,
        CLOSE_BACK_CLAW,
        OPEN_FRONT_CLAW,
        CLEAR_FRONT_ARM,
        CLIP_BACK_ARM,
        CLIP_BACK_ELBOW,
        DONE
    }

    @Override
    public void setup()
    {
        drive = new RobotCentricDrive(hardware);
        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

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
                        outtake.getArm().atStage(BackArm.Stage.CLIP),
                        OuttakeState.CLIP
                    )

                    .state(OuttakeState.BUCKET)
                    .onEnter( () -> {
                        outtake.getArm().bucket();
                        outtake.getElbow().bucket();
                    })
                    .loop( () -> outtake.updateClaw(gamepads))
                    .transition( () ->
                        gamepads.justPressed(Button.GP2_B),
                        OuttakeState.REST
                    )
                    .transition( () ->
                        gamepads.justEnteredThreshold(Analog.GP2_LEFT_TRIGGER, triggerThreshold),
                        OuttakeState.CLIP
                    )

                    .state(OuttakeState.REST)
                    .onEnter( () -> {
                        outtake.getArm().rest();
                        outtake.getElbow().transfer();
                    })
                    .loop( () -> outtake.updateClaw(gamepads))
                    .transition( () ->
                        gamepads.justEnteredThreshold(Analog.GP2_LEFT_TRIGGER, triggerThreshold),
                        OuttakeState.BUCKET
                    )

                    .state(OuttakeState.CLIP)
                    .onEnter( () -> {
                        outtake.getArm().clip();
                        outtake.getElbow().clip();
                    })
                    .loop( () -> outtake.updateClaw(gamepads))
                    .transition( () ->
                        gamepads.justPressed(Button.GP2_B),
                        OuttakeState.REST
                    )
                    .transition( () ->
                        gamepads.justEnteredThreshold(Analog.GP2_LEFT_TRIGGER, triggerThreshold),
                        OuttakeState.BUCKET
                    )

                    .build();
        }

        // Intake to Outtake FSM.
        {
            ioFSM = new StateMachineBuilder()
                    .state(IntakeToOuttakeState.CLEAR_FRONT_ARM)
                    .onEnter( () -> intake.getArm().clear())
                    .transitionTimed(1)

                    .state(IntakeToOuttakeState.CLIP_BACK_ARM)
                    .onEnter( () -> outtake.getArm().clip())
                    .transitionTimed(0.3)

                    .state(IntakeToOuttakeState.CLIP_BACK_ELBOW)
                    .onEnter( () -> outtake.getElbow().clip())
                    .transitionTimed(0.7)

                    .state(IntakeToOuttakeState.DONE)

                    .build();
        }

        // Outtake to Intake FSM.
        {
            oiFSM = new StateMachineBuilder()
                    .state(OuttakeToIntakeState.RETRACT_FRONT_ARM)
                    .onEnter( () -> intake.getArm().retract())
                    .transitionTimed(1)

                    .state(OuttakeToIntakeState.DONE)

                    .build();
        }

        // Transfer FSM.
        {
            transferFSM = new StateMachineBuilder()
                    .state(TransferState.OPEN_BACK_CLAW)
                    .onEnter( () -> outtake.getClaw().open())
                    .transitionTimed(1)

                    .state(TransferState.TRANSFER_BACK_ARM)
                    .onEnter( () -> outtake.getArm().transfer())
                    .transitionTimed(1)

                    .state(TransferState.CLOSE_BACK_CLAW)
                    .onEnter( () -> outtake.getClaw().close())
                    .transitionTimed(1)

                    .state(TransferState.OPEN_FRONT_CLAW)
                    .onEnter( () -> intake.getClaw().open())
                    .transitionTimed(1)

                    .state(TransferState.CLEAR_FRONT_ARM)
                    .onEnter( () -> intake.getArm().clear())
                    .transitionTimed(1)

                    .state(TransferState.CLIP_BACK_ARM)
                    .onEnter( () -> outtake.getArm().clip())
                    .transitionTimed(0.3)

                    .state(TransferState.CLIP_BACK_ELBOW)
                    .onEnter( () -> outtake.getElbow().clip())
                    .transitionTimed(0.7)

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
        // Starting pose
        {
            intake.getArm().retract();
            intake.getClaw().open();
            intake.getSwivel().center();
            outtake.getArm().rest();
            outtake.getElbow().transfer();
            outtake.getClaw().open();
        }

        robotFSM.start();
    }

    @Override
    public void run()
    {
        drive.update(gamepads);
        robotFSM.update();
    }
}
