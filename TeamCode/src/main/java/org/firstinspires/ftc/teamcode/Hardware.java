package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.BackArm;
import org.firstinspires.ftc.teamcode.hardware.BackClaw;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.FrontArm;
import org.firstinspires.ftc.teamcode.hardware.FrontClaw;
import org.firstinspires.ftc.teamcode.hardware.HorizontalExtendo;
import org.firstinspires.ftc.teamcode.hardware.VerticalExtendo;

public class Hardware
{
    public Drivetrain drivetrain;
    public HorizontalExtendo horizontalExtendo;
    public VerticalExtendo verticalExtendo;
    public FrontArm frontArm;
    public FrontClaw frontClaw;
    public BackArm backArm;
    public BackClaw backClaw;

    public Hardware(HardwareMap hardwareMap)
    {
        drivetrain = new Drivetrain(hardwareMap);
        horizontalExtendo = new HorizontalExtendo(hardwareMap);
        verticalExtendo = new VerticalExtendo(hardwareMap);
        frontArm = new FrontArm(hardwareMap);
        frontClaw = new FrontClaw(hardwareMap);
        backArm = new BackArm(hardwareMap);
        backClaw = new BackClaw(hardwareMap);
    }
}
