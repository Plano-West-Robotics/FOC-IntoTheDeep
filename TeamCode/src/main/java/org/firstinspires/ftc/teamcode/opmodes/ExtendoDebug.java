package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.intake.HorizontalExtendo;
import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;
import org.firstinspires.ftc.teamcode.wrappers.Motor;

@TeleOp(name = "Extendo", group = "Debug")
public class ExtendoDebug extends OpMode
{
    public HorizontalExtendo horizontalExtendo;
    public VerticalExtendo verticalExtendo;
    public Motor hl, hr, vl, vr;

    @Override
    public void init()
    {
        horizontalExtendo = new HorizontalExtendo(hardwareMap);
        verticalExtendo = new VerticalExtendo(hardwareMap);
        hl = horizontalExtendo.getLeftMotor();
        hr = horizontalExtendo.getRightMotor();
        vl = verticalExtendo.getLeftMotor();
        vr = verticalExtendo.getRightMotor();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop()
    {
        telemetry.addData("HL", "%s, %d", hl.getDirection(), hl.getPosition());
        telemetry.addData("HR", "%s, %d", hr.getDirection(), hr.getPosition());
        telemetry.addData("VL", "%s, %d", vl.getDirection(), vl.getPosition());
        telemetry.addData("VR", "%s, %d", vr.getDirection(), vr.getPosition());
        telemetry.update();
    }
}
