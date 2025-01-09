package org.firstinspires.ftc.teamcode.debug;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.intake.HorizontalExtendo;
import org.firstinspires.ftc.teamcode.hardware.outtake.VerticalExtendo;
import org.firstinspires.ftc.teamcode.wrappers.Motor;

@TeleOp(name = "ExtendoDebugger", group = "Debug")
public class ExtendoDebugger extends OpMode
{
    public HorizontalExtendo horizontalExtendo;
    public VerticalExtendo verticalExtendo;
    public Motor hl, hr, vl, vr;

    @Override
    public void init()
    {
        horizontalExtendo = new HorizontalExtendo(hardwareMap);
        verticalExtendo = new VerticalExtendo(hardwareMap);
        hl = horizontalExtendo.getLeft();
        hr = horizontalExtendo.getRight();
        vl = verticalExtendo.getLeft();
        vr = verticalExtendo.getRight();

        hl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        hr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        vl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        vr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        hl.zeroPower();
        hr.zeroPower();
        vl.zeroPower();
        vr.zeroPower();

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
