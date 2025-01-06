package org.firstinspires.ftc.teamcode.tune;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.intake.HorizontalExtendo;

@Config
@TeleOp(group = "Tune")
public class HorizontalExtendoPIDFTuner extends OpMode
{
    public static double p = 0.0001, i = 0, d = 0.00002, f = 0;
    public static double pSync = 0.0005, maxCorrection = 0.1;
    public static int targetPosition = 0;

    public PIDFController leftController, rightController;
    public HorizontalExtendo extendo;
    public double leftPosition, rightPosition;
    public double leftOutput, rightOutput;
    public double syncError, syncCorrection;

    @Override
    public void init()
    {
        leftController = new PIDFController(p, i, d, f);
        rightController = new PIDFController(p, i, d, f);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        extendo = new HorizontalExtendo(hardwareMap);
    }

    @Override
    public void loop()
    {
        leftController.setPIDF(p, i, d, f);
        rightController.setPIDF(p, i, d, f);

        leftPosition = extendo.getLeft().getPosition();
        rightPosition = extendo.getRight().getPosition();

        leftOutput = leftController.calculate(leftPosition, targetPosition);
        rightOutput = rightController.calculate(rightPosition, targetPosition);

        syncError = leftPosition - rightPosition;
        syncCorrection = Range.clip(syncError * pSync, -maxCorrection, maxCorrection);

        extendo.setPower(leftOutput - syncCorrection, rightOutput + syncCorrection);

        telemetry.addData("Left Position", leftPosition);
        telemetry.addData("Right Position", rightPosition);
        telemetry.addData("Sync Error", syncError);
        telemetry.addData("Sync Correction", syncCorrection);
        telemetry.update();
    }
}
