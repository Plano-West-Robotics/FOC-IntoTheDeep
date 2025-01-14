package org.firstinspires.ftc.teamcode.tune;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;

@Config
@TeleOp(group = "Tune")
public class VerticalExtendoPIDFTuner extends OpMode
{
    public static double p = VerticalExtendo.P;
    public static double i = VerticalExtendo.I;
    public static double d = VerticalExtendo.D;
    public static double f = VerticalExtendo.F;
    public static int targetPosition = 0;

    public PIDFController controller;
    public VerticalExtendo extendo;
    public double currentPosition, controllerOutput;

    @Override
    public void init()
    {
        controller = new PIDFController(p, i, d, f);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        extendo = new VerticalExtendo(hardwareMap);
    }

    @Override
    public void loop()
    {
        controller.setPIDF(p, i, d, f);
        currentPosition = extendo.getAveragePosition();
        controllerOutput = controller.calculate(currentPosition, targetPosition);
        extendo.setPower(controllerOutput, false);

        telemetry.addData("Current", currentPosition);
        telemetry.addData("Target", targetPosition);
        telemetry.update();
    }
}
