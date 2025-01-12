package org.firstinspires.ftc.teamcode.debug;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.wrappers.ServoPair;

@Config
@TeleOp(group = "Debug")
public class BackArmDebugger extends OpMode
{
    public static double leftPosition;

    public ServoPair arm;
    public final double positionDiff = 0.08;

    @Override
    public void init()
    {
        arm = new ServoPair(hardwareMap, "bal", "bar", positionDiff);
    }

    @Override
    public void loop()
    {
        if (!(leftPosition >= positionDiff && leftPosition <= 1)) leftPosition = positionDiff;
        arm.setPosition(leftPosition);
    }
}
