package org.firstinspires.ftc.teamcode.tune;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    public static double angVelControlOverrideThreshold = 0.01;
    public static double minAngVel = 0.0015;

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
    }

    @Override
    public void loop()
    {
        controller.setPIDF(p, i, d, f);

        double x = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_X);
        double y = gamepads.getAnalogValue(Analog.GP1_LEFT_STICK_Y);

        double rx = gamepads.getAnalogValue(Analog.GP1_RIGHT_STICK_X);
        double currentAngVel = drive.imu.getRobotAngularVelocity(AngleUnit.RADIANS).zRotationRate;
        if (Math.abs(currentAngVel) <= minAngVel) currentAngVel = 0;
        double targetAngVel = Math.abs(rx) > angVelControlOverrideThreshold ? currentAngVel : 0;

        double rxCorrection = controller.calculate(currentAngVel, targetAngVel);
        rx -= rxCorrection;

        drive.drive(y, x, rx);

        telemetry.addData("RX", rx);
        telemetry.addData("Current Angular Velocity (rad/sec)", currentAngVel);
        telemetry.addData("Target Angular Velocity (rad/sec)", targetAngVel);

        gamepads.update(gamepad1, gamepad2);
        telemetry.update();
    }
}
