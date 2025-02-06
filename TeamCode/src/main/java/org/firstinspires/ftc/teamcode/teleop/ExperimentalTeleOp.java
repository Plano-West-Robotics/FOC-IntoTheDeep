package org.firstinspires.ftc.teamcode.teleop;

import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.FieldCentricDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

// TODO: Add revisions from discord messages

/*
Changes to how the TeleOp could work:

- Have a single button to go from the intake holding the block to the outtake claw holding in the outtake position
- Change how the intake probing thing works - the intake arm should be closer to the ground so it is easier to see where the claw would go - also shouldn't have to press a second button for the claw to close and go back up - should work on a timer
- Same state for specimens && bucket samples
- All on one controller

Controls:
Y -  move the arm down (slightly) and close the intake claw around the sample, then go back up
B - complete transfer to outtake
X - release outtake claw
A - redo intake (if missed sample)

Left and Right Bumpers - move swivel

Double Press DPAD_UP - Vertical slides go to the high bucket position
Single press DPAD_UP - Vertical slides go to specimen chamber position
Single press DPAD_DOWN - Vertical slides go to the bottom

Left Stick X and Y - moving and strafing
Right Stick X - turning
Right Stick Y - intake slides or outtake slides, depending on the state

Robot Centric - more intuitive - the robot should never be turned around anyway
 */

public class ExperimentalTeleOp extends BaseTeleOp {
    public Drive drive;
    public Intake intake;
    public Outtake outtake;

    public StateMachine experimentalFSM;

    public int doublePressTimeMilliseconds = 300;

    public enum Experimentals
    {
        ON_START,
        INTAKE,
        OUTTAKE,
    }

    @Override
    public void setup()
    {
        drive = new FieldCentricDrive(hardware);
        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        experimentalFSM = new StateMachineBuilder()
                .state(Experimentals.ON_START)
                .onEnter(() -> {

                    telemetry.addLine("Starting the state machine!");
                    intake.getArm().retract();
                    intake.getClaw().open();
                    intake.getSwivel().center();
                    outtake.getArm().rest();
                    outtake.getElbow().transfer();
                    outtake.getClaw().open();

                })
                .transitionTimed(1)


                .state(Experimentals.INTAKE)
                .onEnter(() -> intake.getArm().extend())
                .loop( () -> {

                    intake.updateExtendoPowerExperimental(gamepads);
                    intake.updateSwivel(gamepads);

                    if (gamepads.justPressed(Button.GP1_Y)) // have to replace with a fsm :(
                    {

                    }

                })

                .build();

        experimentalFSM.start();
    }

    @Override
    public void run()
    {
        experimentalFSM.update();
    }
}
