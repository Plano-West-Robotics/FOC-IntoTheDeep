package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Button;

@TeleOp
public class TempTesting extends BaseTeleOpTesting {

    TestMachine testMachine1;
    StateMachine testMachine;

    @Override
    public void setup()
    {
        testMachine1 = new TestMachine();
        testMachine = testMachine1.getGlobalMachine(gamepads);
        testMachine.start();
    }

    @Override
    public void run()
    {
        testMachine.update();
        telemetry.addData("Global State", testMachine.getState());
        telemetry.addData("Global is Running", testMachine.isRunning());
        telemetry.addData("Transfer State", testMachine1.transferMachine.getState());
        telemetry.addData("Transfer is Running", testMachine1.transferMachine.isRunning());
        telemetry.addData("A button pressed", gamepads.justPressed(Button.GP1_A));
    }

}
