package org.firstinspires.ftc.teamcode.debug;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.outtake.BackArm;
import org.firstinspires.ftc.teamcode.wrappers.ServoPair;

@Config
@TeleOp(group = "Debug")
public class BackArmDebugger extends OpMode
{
    public static double leftPosition;

    public ServoPair arm;
    public final double positionDiff = BackArm.POSITION_DIFF;

    @Override
    public void init()
    {
        arm = new ServoPair(hardwareMap, "bal", "bar", positionDiff);
    }

    @Override
    public void loop()
    {
        leftPosition = Range.clip(leftPosition, positionDiff, 1);
        arm.setPosition(leftPosition);
    }
}
