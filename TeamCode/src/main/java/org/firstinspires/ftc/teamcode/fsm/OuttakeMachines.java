package org.firstinspires.ftc.teamcode.fsm;

import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public final class OuttakeMachines
{
    public enum BRState
    {
        CLOSE_BACK_CLAW,
        REST_BACK_ARM_AND_TRANSFER_BACK_ELBOW,
        DONE
    }

    public enum RBState
    {
        CLOSE_BACK_CLAW_AND_BUCKET_BACK_ARM_AND_BUCKET_BACK_ELBOW,
        BUCKET_OPEN_BACK_CLAW,
        DONE
    }

    public enum CRState
    {
        CLOSE_BACK_CLAW_AND_REST_BACK_ARM,
        TRANSFER_ELBOW_AND_OPEN_BACK_CLAW,
        DONE
    }

    public enum BCState
    {
        CLOSE_BACK_CLAW,
        CLIP_BACK_ARM_AND_CLIP_BACK_ELBOW,
        OPEN_BACK_CLAW,
        DONE
    }

    public enum CBState
    {
        CLOSE_BACK_CLAW,
        BUCKET_BACK_ARM,
        BUCKET_BACK_ELBOW,
        BUCKET_OPEN_BACK_CLAW,
        DONE
    }

    public static StateMachine getBucketToRestFSM(Outtake outtake)
    {
        return new StateMachineBuilder()
                .state(BRState.CLOSE_BACK_CLAW)
                .onEnter( () -> outtake.getClaw().close())
                .transitionTimed(1)

                .state(BRState.REST_BACK_ARM_AND_TRANSFER_BACK_ELBOW)
                .onEnter( () -> {
                    outtake.getArm().rest();
                    outtake.getElbow().transfer();
                })
                .transitionTimed(1)

                .state(BRState.DONE)

                .build();
    }

    public static StateMachine getRestToBucketFSM(Outtake outtake)
    {
        return new StateMachineBuilder()
                .state(RBState.CLOSE_BACK_CLAW_AND_BUCKET_BACK_ARM_AND_BUCKET_BACK_ELBOW)
                .onEnter( () -> {
                    outtake.getClaw().close();
                    outtake.getArm().bucket();
                    outtake.getElbow().bucket();
                })
                .transitionTimed(1)

                .state(RBState.BUCKET_OPEN_BACK_CLAW)
                .onEnter( () -> outtake.getClaw().bucketOpen())
                .transitionTimed(1)

                .state(RBState.DONE)

                .build();
    }

    public static StateMachine getClipToRestFSM(Outtake outtake)
    {
        return new StateMachineBuilder()
                .state(CRState.CLOSE_BACK_CLAW_AND_REST_BACK_ARM)
                .onEnter( () -> {
                    outtake.getClaw().close();
                    outtake.getArm().rest();
                })
                .transitionTimed(1)

                .state(CRState.TRANSFER_ELBOW_AND_OPEN_BACK_CLAW)
                .onEnter( () -> {
                    outtake.getElbow().transfer();
                    outtake.getClaw().open();
                })
                .transitionTimed(1)

                .state(CRState.DONE)

                .build();
    }

    public static StateMachine getBucketToClipFSM(Outtake outtake)
    {
        return new StateMachineBuilder()
                .state(BCState.CLOSE_BACK_CLAW)
                .onEnter( () -> outtake.getClaw().close())
                .transitionTimed(1)

                .state(BCState.CLIP_BACK_ARM_AND_CLIP_BACK_ELBOW)
                .onEnter( () -> {
                    outtake.getArm().clip();
                    outtake.getElbow().clip();
                })
                .transitionTimed(1)

                .state(BCState.OPEN_BACK_CLAW)
                .onEnter( () -> outtake.getClaw().open())
                .transitionTimed(1)

                .state(BCState.DONE)

                .build();
    }

    public static StateMachine getClipToBucketFSM(Outtake outtake)
    {
        return new StateMachineBuilder()
                .state(CBState.CLOSE_BACK_CLAW)
                .onEnter( () -> outtake.getClaw().close())
                .transitionTimed(1)

                .state(CBState.BUCKET_BACK_ARM)
                .onEnter( () -> outtake.getArm().bucket())
                .transitionTimed(0.5)

                .state(CBState.BUCKET_BACK_ELBOW)
                .onEnter( () -> outtake.getElbow().bucket())
                .transitionTimed(1)

                .state(CBState.BUCKET_OPEN_BACK_CLAW)
                .onEnter( () -> outtake.getClaw().bucketOpen())
                .transitionTimed(1)

                .state(CBState.DONE)

                .build();
    }

    private OuttakeMachines() {}
}
