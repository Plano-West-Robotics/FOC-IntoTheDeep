package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sfdev.assembly.state.StateMachine;

import org.firstinspires.ftc.teamcode.fsm.ExperimentalGlobalMachines;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.RobotCentricDrive;

// TODO: Add revisions from discord messages - also need intake to outtake and outtake to intake states
//  because you need to use states any time there are timings
//  also put each machine in a separate class, unlike the normal tele
//  make sure to start, update, stop, and reset each nested state machine

/*
Changes to how the TeleOp works:

- Have a single button to go from the intake holding the block to the outtake claw holding the block in the bucket position
- Have a separate button for the intake -> specimen sequence
- Change how the intake works - the intake arm should be closer to the ground so it is easier to see where the claw would go -
also shouldn't have to press a second button for the claw to close and go back up - should work on a timer
- All on one controller - both slides (vertical and horizontal) use right stick y - changes based on whether you're in intake/outtake mode

Controls:
A - intake to specimen sequence - if in intake mode, drop block on ground (should be done in the observation zone),
go to wall pickup position for specimen without doing transfer of block (to pick up clipped specimen from wall)
B - complete transfer intake to outtake or outtake to intake (for bucket sequence and going back to intake mode)
X - open/close outtake claw
Y -  move the arm down and close the intake claw around the sample, then go back up - if pressed again reset intake

Left and Right Bumpers - move swivel - only when in intake mode

Right Trigger - move arm and elbow to front hook - after the slides finish moving up -
pressing it again should move slides to the clip position and release claw
pressing it again should move the slides and arm back to bucket position (to pickup another specimen from the wall)

DPAD_UP - Vertical slides go to the bucket position - only operates in bucket mode
DPAD_DOWN - Vertical slides go to the bottom - only operates in bucket mode (specimen mode has a different mechanism for going to the bottom)

Left Stick Y - forward/backward movement
Left Stick X - left/right strafing
Right Stick X - turning left/right in place
Right Stick Y - intake slides or outtake slides, depending on the state

Robot Centric - more intuitive (and works better than the field centric atm) - the robot should never be turned around anyway
 */

// TODO: NEED TO FIND THE RIGHT SPECIMEN AND BUCKET POSITIONS

@TeleOp (name = "Experimental")
public class ExperimentalTeleOp extends BaseTeleOp {

    public Drive drive;
    public Intake intake;
    public Outtake outtake;

    public ExperimentalGlobalMachines experimentalFSMClass;
    public StateMachine experimentalFSM;

    public StateMachine specimenFSM;

    @Override
    public void setup()
    {
        drive = new RobotCentricDrive(hardware);
        intake = new Intake(hardware);
        outtake = new Outtake(hardware);

        experimentalFSMClass = new ExperimentalGlobalMachines(intake, outtake, gamepads);
        experimentalFSM = experimentalFSMClass.getGlobalMachines();

        specimenFSM = experimentalFSMClass.specimenFSM;

        experimentalFSM.start();
    }

    @Override
    public void run()
    {
        drive.update(gamepads);
        experimentalFSM.update();
        telemetry.addData("Global State", experimentalFSM.getState());
        telemetry.addData("Specimen State", specimenFSM.getState());
    }
}
