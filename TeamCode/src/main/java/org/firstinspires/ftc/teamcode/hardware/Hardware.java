package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

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
    public IMU imu;
    public IMU.Parameters imuParameters;

    public Drivetrain drivetrain;

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
        imu = hardwareMap.get(IMU.class, "imu");
        imuParameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(imuParameters);

        drivetrain = new Drivetrain(hardwareMap);

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
