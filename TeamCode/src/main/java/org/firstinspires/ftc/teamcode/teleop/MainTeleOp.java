package org.firstinspires.ftc.teamcode.teleop;

import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
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
        PROBE,
        EXTEND,
        RETRACT
    }

    public enum OuttakeState
    {
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
    public void run()
    {

    }
}
