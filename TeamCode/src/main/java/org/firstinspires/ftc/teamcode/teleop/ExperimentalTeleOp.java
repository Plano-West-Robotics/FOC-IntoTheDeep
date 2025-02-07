package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.teamcode.control.Button;
import org.firstinspires.ftc.teamcode.fsm.ExperimentalGlobalMachines;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.FieldCentricDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.RobotCentricDrive;

// TODO: Add revisions from discord messages - also need intake to outtake and outtake to intake states
//  because you need to use states any time there are timings
//  also put each machine in a separate class, unlike the normal tele
//  make sure to start, update, stop, and reset each nested state machine

/*
Changes to how the TeleOp could work:

- Have a single button to go from the intake holding the block to the outtake claw holding it in the outtake position
- Change how the intake probing thing works - the intake arm should be closer to the ground so it is easier to see where the claw would go -
also shouldn't have to press a second button for the claw to close and go back up - should work on a timer
- All on one controller

Controls:
Y -  move the arm down and close the intake claw around the sample, then go back up - if pressed again open intake
B - complete transfer intake to outtake or outtake to intake
X - open/close outtake claw
A - intake to specimen sequence - if in intake mode, reset swivel, move arm down, release block, retract intake slides,
go to wall pickup position for specimen without doing transfer of block

Left and Right Bumpers - move swivel

Right Trigger - move arm and elbow to front hook - slightly after the slides start moving up -
pressing again should move it back to bucket position

DPAD_UP - Vertical slides go to the bucket position - need to find - set it at about 3000 for now
DPAD_DOWN - Vertical slides go to the bottom - make sure to set it at

Left Stick X and Y - moving and strafing
Right Stick X - turning
Right Stick Y - intake slides or outtake slides, depending on the state

Robot Centric - more intuitive - the robot should never be turned around anyway
 */

@TeleOp (name = "Experimental")
public class ExperimentalTeleOp extends BaseTeleOp {

    public Drive drive;
    public Intake intake;
    public Outtake outtake;

    public ExperimentalGlobalMachines experimentalFSMClass;
    public StateMachine experimentalFSM;

    @Override
    public void setup()
    {
        drive = new RobotCentricDrive(hardware);
        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        experimentalFSMClass = new ExperimentalGlobalMachines(intake, outtake, gamepads);
        experimentalFSM = experimentalFSMClass.getGlobalMachines();

        experimentalFSM.start();
    }

    @Override
    public void run()
    {
        drive.update(gamepads);
        experimentalFSM.update();
        telemetry.addData("Global State", experimentalFSM.getState());
    }
}
