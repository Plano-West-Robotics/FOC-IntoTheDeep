package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.FieldCentricDrivetrain;
import org.firstinspires.ftc.teamcode.hardware.RobotCentricDrivetrain;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontArm;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontClaw;
import org.firstinspires.ftc.teamcode.hardware.intake.FrontSwivel;
import org.firstinspires.ftc.teamcode.hardware.intake.HorizontalExtendo;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackClaw;
import org.firstinspires.ftc.teamcode.hardware.outtake.BackElbow;
import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;

public class Hardware
{
    public RobotCentricDrivetrain rDrivetrain;
    public FieldCentricDrivetrain fDrivetrain;

    // Intake.
    public HorizontalExtendo horizontalExtendo;
    public FrontArm frontArm;
    public FrontClaw frontClaw;
    public FrontSwivel frontSwivel;

    // Outtake.
    public VerticalExtendo verticalExtendo;
    public BackArm backArm;
    public BackElbow backElbow;
    public BackClaw backClaw;

    public Hardware(HardwareMap hardwareMap)
    {
        // TODO: Pick one drivetrain out of the two.
        rDrivetrain = new RobotCentricDrivetrain(hardwareMap);
        // fDrivetrain = new FieldCentricDrivetrain(hardwareMap);

        horizontalExtendo = new HorizontalExtendo(hardwareMap);
        frontArm = new FrontArm(hardwareMap);
        frontClaw = new FrontClaw(hardwareMap);
        frontSwivel = new FrontSwivel(hardwareMap);

        verticalExtendo = new VerticalExtendo(hardwareMap);
        backArm = new BackArm(hardwareMap);
        backElbow = new BackElbow(hardwareMap);
        backClaw = new BackClaw(hardwareMap);
    }
}
