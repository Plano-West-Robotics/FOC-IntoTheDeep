package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Config
public abstract class BaseAuto extends LinearOpMode
{
    public Hardware hardware;

    @Override
    public void runOpMode() throws InterruptedException
    {
        hardware = new Hardware(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        setup();

        waitForStart();

        while (opModeIsActive())
        {
            log();
            telemetry.update();
        }

        if (isStopRequested()) return;

        run();
    }

    public void setup() {}

    public void log() {}

    public void run() {}
}
