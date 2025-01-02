package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;

@TeleOp(name = "Main")
public class MainTeleOp extends BaseTeleOp
{
    public TeleDrive drive;
    public Intake intake;
    public Outtake outtake;

    // io --> "intake to outtake" ; oi --> "outtake to intake"
    public StateMachine robotFSM, intakeFSM, outtakeFSM, ioFSM, oiFSM, transferFSM;

    public double triggerThreshold = 0.65;

    public enum RobotState
    {
        INTAKE,
        OUTTAKE,
        INTAKE_TO_OUTTAKE, // When switching from intake to outtake but no sample is present.
        OUTTAKE_TO_INTAKE, // When switching from outtake to intake but no sample is present.
        TRANSFER // When the intake hands a sample to the outtake.
    }

    public enum IntakeState
    {
        RETRACT,
        EXTEND,
        PROBE
    }

    public enum OuttakeState
    {
        /*
        outtakeFSM starts with INIT state before moving on to HOLD or RELEASE, because the outtake
        claw could be either state at start
        (e.x., TRANSFER -> OUTTAKE, claw is closed; INTAKE_TO_OUTTAKE -> OUTTAKE, claw is open)
         */
        INIT,
        HOLD,
        RELEASE
    }

    public enum IntakeToOuttakeState
    {
        CLEAR_INTAKE,
        EXTEND_OUTTAKE,
        SCORE_ELBOW,
        DONE
    }

    public enum OuttakeToIntakeState
    {
        RELEASE,
        RETRACT_OUTTAKE,
        REST_ELBOW,
        UNCLEAR_INTAKE,
        DONE
    }

    public enum TransferState
    {
        DESCEND_OUTTAKE, // Outtake claw opens; outtake arm descends parallel with ground.
        CLAMP_OUTTAKE, // Outtake claw closes.
        RELEASE_INTAKE, // Intake claw opens.
        CLEAR_INTAKE, // Intake arm goes below parallel with ground to give outtake clearance.
        DELIVER_OUTTAKE, // Outtake swoops underneath and gets next to vertical linear slides.
        SCORE_ELBOW, // Previous state is running, outtake elbow positions for scoring.
        DONE
    }

    @Override
    public void setup()
    {
        drive = new TeleDrive(hardware);
        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        // Intake FSM.
        {
            intakeFSM = new StateMachineBuilder()
                    .state(IntakeState.RETRACT)
                    .onEnter( () -> {
                        intake.retractArm();
                        intake.centerSwivel();
                    })
                    .loop( () -> intake.updateExtendoPower(gamepads))
                    .transition( () -> intake.inExtendZone(), IntakeState.EXTEND)

                    .state(IntakeState.EXTEND)
                    .onEnter( () -> intake.extendArm())
                    .loop( () -> {
                        intake.updateExtendoPower(gamepads);
                        intake.updateClaw(gamepads);
                        intake.updateSwivel(gamepads);
                    })
                    .transition( () -> intake.inRetractZone(), IntakeState.RETRACT)
                    .transition( () ->
                        gamepads.getAnalogValue(Analog.GP2_LEFT_TRIGGER) >= triggerThreshold,
                        IntakeState.PROBE
                    )

                    .state(IntakeState.PROBE)
                    .onEnter( () -> intake.probeArm())
                    .loop( () -> {
                        intake.updateExtendoPower(gamepads);
                        intake.updateClaw(gamepads);
                        intake.updateSwivel(gamepads);
                    })
                    .transition( () -> intake.inRetractZone(), IntakeState.RETRACT)
                    .transition( () ->
                        gamepads.getAnalogValue(Analog.GP2_LEFT_TRIGGER) < triggerThreshold,
                        IntakeState.EXTEND)

                    .build();
        }

        // Outtake FSM.
        {
            outtakeFSM = new StateMachineBuilder()
                    // Refer to enum state definition for purpose of INIT.
                    .state(OuttakeState.INIT)
                    .transition( () -> outtake.clawIsClosed(), OuttakeState.HOLD)
                    .transition( () -> outtake.clawIsOpen(), OuttakeState.RELEASE)

                    .state(OuttakeState.HOLD)
                    .onEnter( () -> outtake.closeClaw())
                    .loop( () -> outtake.updateExtendoPower(gamepads))
                    .transition( () -> gamepads.isPressed(Button.GP2_X), OuttakeState.RELEASE)

                    .state(OuttakeState.RELEASE)
                    .onEnter( () -> outtake.openClaw())
                    .loop( () -> outtake.updateExtendoPower(gamepads))
                    .transition( () -> gamepads.isPressed(Button.GP2_X), OuttakeState.HOLD)

                    .minimumTransitionTimed(0.2, 2, 3)

                    .build();
        }

        // IntakeToOuttakeFSM.
        {
            ioFSM = new StateMachineBuilder()
                    .state(IntakeToOuttakeState.CLEAR_INTAKE)
                    .onEnter( () -> intake.clearArm())
                    .transitionTimed(1)

                    .state(IntakeToOuttakeState.EXTEND_OUTTAKE)
                    .onEnter( () -> {
                        outtake.extendArm();
                        outtake.openClaw();
                    })
                    .transitionTimed(0.2)

                    .state(IntakeToOuttakeState.SCORE_ELBOW)
                    .onEnter( () -> outtake.scoreElbow())
                    .transitionTimed(1.8)

                    .state(IntakeToOuttakeState.DONE)

                    .build();
        }

        // OuttakeToIntakeFSM.
        {
            oiFSM = new StateMachineBuilder()
                    .state(OuttakeToIntakeState.RELEASE)
                    .onEnter( () -> outtake.openClaw())
                    .transitionTimed(1)

                    .state(OuttakeToIntakeState.RETRACT_OUTTAKE)
                    .onEnter( () -> outtake.retractArm())
                    .transitionTimed(0.2)

                    .state(OuttakeToIntakeState.REST_ELBOW)
                    .onEnter( () -> outtake.restElbow())
                    .transitionTimed(1.8)

                    .state(OuttakeToIntakeState.UNCLEAR_INTAKE)
                    .onEnter( () -> intake.retractArm())
                    .transitionTimed(1)

                    .state(OuttakeToIntakeState.DONE)

                    .build();
        }

        // TransferFSM.
        {
            transferFSM = new StateMachineBuilder()
                    .state(TransferState.DESCEND_OUTTAKE)
                    .onEnter( () -> {
                        outtake.openClaw();
                        outtake.handoverArm();
                    })
                    .transitionTimed(1)

                    .state(TransferState.CLAMP_OUTTAKE)
                    .onEnter( () -> outtake.closeClaw())
                    .transitionTimed(1)

                    .state(TransferState.RELEASE_INTAKE)
                    .onEnter( () -> intake.openClaw())
                    .transitionTimed(1)

                    .state(TransferState.CLEAR_INTAKE)
                    .onEnter( () -> intake.clearArm())
                    .transitionTimed(1)

                    .state(TransferState.DELIVER_OUTTAKE)
                    .onEnter( () -> outtake.extendArm())
                    .transitionTimed(0.2)

                    .state(TransferState.SCORE_ELBOW)
                    .onEnter( () -> outtake.scoreElbow())
                    .transitionTimed(1.8)

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
                        intake.stopSlides();
                        intakeFSM.reset();
                        intakeFSM.stop();
                    })

                    .state(RobotState.OUTTAKE)
                    .onEnter( () -> outtakeFSM.start())
                    .loop( () -> outtakeFSM.update())
                    .transition( () ->
                        gamepads.isPressed(Button.GP2_Y),
                        RobotState.OUTTAKE_TO_INTAKE)
                    .onExit( () -> {
                        outtake.stopSlides();
                        outtakeFSM.reset();
                        outtakeFSM.stop();
                    })

                    .state(RobotState.INTAKE_TO_OUTTAKE)
                    .onEnter( () -> ioFSM.start())
                    .loop( () -> ioFSM.update())
                    .transition( () ->
                        ioFSM.getState() == IntakeToOuttakeState.DONE,
                        RobotState.OUTTAKE)
                    .onExit( () -> {
                        ioFSM.reset();
                        ioFSM.stop();
                    })

                    .state(RobotState.OUTTAKE_TO_INTAKE)
                    .onEnter( () -> oiFSM.start())
                    .loop( () -> oiFSM.update())
                    .transition( () ->
                        oiFSM.getState() == OuttakeToIntakeState.DONE,
                        RobotState.INTAKE)
                    .onExit( () -> {
                        oiFSM.reset();
                        oiFSM.stop();
                    })

                    .state(RobotState.TRANSFER)
                    .onEnter( () -> transferFSM.start())
                    .loop( () -> transferFSM.update())
                    .transition( () ->
                        transferFSM.getState() == TransferState.DONE,
                        RobotState.OUTTAKE)
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
        intake.retractArm();
        intake.openClaw();
        intake.centerSwivel();
        outtake.retractArm();
        outtake.restElbow();
        outtake.openClaw();
        robotFSM.start();
    }

    @Override
    public void run()
    {
        drive.update(gamepads);
        robotFSM.update();
    }
}
