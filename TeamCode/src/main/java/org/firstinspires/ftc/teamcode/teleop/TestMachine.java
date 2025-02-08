package org.firstinspires.ftc.teamcode.teleop;

import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.control.Gamepads;

public class TestMachine {

    public double triggerThresh = 0.75;

    public StateMachine transferMachine;

    public enum Transfer
    {
        GRAB,
        ARM_UP,
        COMPLETE
    }

    public enum Globals
    {
        INIT,
        INTAKE,
        TRANSFER,
        OUTTAKE
    }


    private static StateMachine getTransferMachine(Gamepads gamepads) {
        return new StateMachineBuilder()
                .state(Transfer.GRAB)
                .transitionTimed(2)
                .state(Transfer.ARM_UP)
                .transition(() -> gamepads.justPressed(Button.GP1_A))
                .state(Transfer.COMPLETE)
                .build();
    }

    public static void placeholder()
    {
    }

    public StateMachine getGlobalMachine(Gamepads gamepads)
    {
        transferMachine = getTransferMachine(gamepads);

        return new StateMachineBuilder()
                .state(Globals.INIT)
                .transitionTimed(1)
                .state(Globals.INTAKE)
                .transition(() -> gamepads.justPressed(Button.GP1_RIGHT_STICK_BUTTON))
                .state(Globals.TRANSFER)
                .onEnter(() -> transferMachine.start())
                .loop(() -> transferMachine.update())
                .transition(() -> transferMachine.getState() == Transfer.COMPLETE)
                .onExit(() ->
                {
                    transferMachine.stop();
                    transferMachine.reset();
                })

                .state(Globals.OUTTAKE)
                .transition(() -> gamepads.withinThreshold(Analog.GP1_RIGHT_TRIGGER, triggerThresh),
                        Globals.INTAKE)
                .build();
    }
}

