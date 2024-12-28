package org.firstinspires.ftc.teamcode.hardware.concrete;

import com.qualcomm.robotcore.hardware.HardwareMap;

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
