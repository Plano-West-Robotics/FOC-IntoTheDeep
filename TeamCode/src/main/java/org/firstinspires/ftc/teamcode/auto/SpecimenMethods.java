package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public class SpecimenMethods {

    Hardware hardware;

    public Intake intake;
    public Outtake outtake;

    public MecanumDrive drive;
    public Pose2d initPose;

    public SpecimenMethods(){

    }

    public enum Init_State_Preload
    {
        CLOSE_BACK_CLAW,
        SET_ELBOW_FRONT,
        SET_ARM_FRONT,
        SET_INTAKE_UPRIGHT
    }

    public enum Approach_State_Preload
    {
        RAISE_SLIDES_TO_UNDER_CHAMBER__MOVE_TO_CHAMBER
    }

    public enum At_Chamber_State
    {
        RAISE_SLIDES_TO_HOOK,
        OPEN_CLAW
    }

    public enum Pushing_State
    {
        ELBOW_TO_WALL__ARM_TO_WALL__LOWER_SLIDES_TO_BOTTOM__PUSH_SAMPLES_PATH
    }

    public enum Wall_Pickup_State_From_Push
    {
        GO_TO_PICKUP_PATH_FROM_PUSH
    }

    public enum At_Pickup
    {
        CLOSE_CLAW
    }

    public enum Hook_Non_Preload
    {
        MOVE_SLIDES_UP_TIMED_ACTION,
        MOVE_ARM_HOOK__MOVE_ELBOW_HOOK
    }

    public enum To_Pickup_From_Chamber
    {
        ELBOW_TO_WALL__ARM_TO_WALL__LOWER_SLIDES_TO_BOTTOM__GO_TO_PICKUP_FROM_CHAMBER_PATH
    }

    public enum Reset_For_Teleop
    {
        RETRACT_HORIZONTAL_SLIDES__RETRACT_VERTICAL_SLIDES,
        INTAKE_ARM_RETRACT__OUTTAKE_ARM_REST__ELBOW_TRANSFER
    }

    //plan - return a sequential action that contains everything - pass in trajectory action
    // init should have a separate one

    public static double tR(double degrees)
    {
        return Math.toRadians(degrees);
    }
}
