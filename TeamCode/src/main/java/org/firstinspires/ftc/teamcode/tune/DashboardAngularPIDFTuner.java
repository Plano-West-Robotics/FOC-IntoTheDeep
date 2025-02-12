package org.firstinspires.ftc.teamcode.tune;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.control.Analog;
import org.firstinspires.ftc.teamcode.control.Gamepads;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.subsystems.FieldCentricDrive;

@Config
@TeleOp(group = "Tune")
public class DashboardAngularPIDFTuner extends OpMode
{
    public static double p = 0;
    public static double i = 0;
    public static double d = 0;
    public static double f = 0;
    public static double targetAngle = 0;

    public Hardware hardware;
    public FieldCentricDrive drive;
    public Gamepads gamepads;
    public PIDFController controller;

    @Override
    public void init()
    {
        hardware = new Hardware(hardwareMap);
        drive = new FieldCentricDrive(hardware);
        gamepads = new Gamepads(gamepad1, gamepad2);
        controller = new PIDFController(p, i, d, f);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drive.imu.resetYaw();
    }

    @Override
    public void loop()
    {
        controller.setPIDF(p, i, d, f);

        double currentAngle = drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        targetAngle = Range.clip(targetAngle, -180, 180);

        if (Math.abs(targetAngle - currentAngle) > 180)
            // If targetAngle is negative, add 360, otherwise (targetAngle is positive) subtract 360.
            targetAngle -= Math.signum(targetAngle) * 360;

        double rx = -controller.calculate(currentAngle, targetAngle);
        drive.drive(0, 0, rx);

        telemetry.addData("Current Angle (degrees)", currentAngle);
        telemetry.addData("Target Angle (degrees)", targetAngle);
        telemetry.addData("RX", rx);

        telemetry.addData("FR Power", drive.drivetrain.fr.getPower());
        telemetry.addData("FL Power", drive.drivetrain.fl.getPower());
        telemetry.addData("BR Power", drive.drivetrain.br.getPower());
        telemetry.addData("BL Power", drive.drivetrain.bl.getPower());

        gamepads.update(gamepad1, gamepad2);
        telemetry.update();
    }
}
