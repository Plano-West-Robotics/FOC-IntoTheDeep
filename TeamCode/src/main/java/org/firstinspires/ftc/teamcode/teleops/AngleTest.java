package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name="Angle Test")
public class AngleTest extends LinearOpMode
{
    public DcMotor fr, fl, br, bl;
    public BHI260IMU imu;
    public IMU.Parameters params;
    public Orientation myRobotOrientationDeg;
    public Orientation myRobotOrientationRad;
    public float yawDeg;
    public float yawRad;

    public ElapsedTime timer = new ElapsedTime();
    public double Kp;
    public double Ki;
    public double Kd;
    public double integralSum = 0;
    public double lastError = 0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        // Need to add to the config atm
        imu = hardwareMap.get(BHI260IMU.class, "imu");

        // Heading/yaw is for the z axis (upwards) (use thumb trick w/ right hand)
        // We just care about the yaw
        params = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(params);

        Kp = PIDConstants.Kp;
        Ki = PIDConstants.Ki;
        Kd = PIDConstants.Kd;
        double referenceAngle = Math.toRadians(90);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        initMotors();

        waitForStart();

        while (opModeIsActive())
        {
            telemetry.addData("Target IMU Angle", Math.toDegrees(referenceAngle));

            myRobotOrientationDeg = imu.getRobotOrientation(
                    AxesReference.INTRINSIC,
                    AxesOrder.ZYX,
                    AngleUnit.DEGREES
            );

            yawDeg = myRobotOrientationDeg.firstAngle;

            myRobotOrientationRad = imu.getRobotOrientation(
                    AxesReference.INTRINSIC,
                    AxesOrder.ZYX,
                    AngleUnit.RADIANS
            );

            yawRad = myRobotOrientationRad.firstAngle;

            telemetry.addData("Current IMU Angle", yawDeg);

            double powerOutput = PIDControl(referenceAngle, yawRad);
            power(powerOutput);

            telemetry.update();
        }

    }

    public double PIDControl(double reference, double state)
    {
        double error = angleWrap(reference - state);
        telemetry.addData("Error: ", error);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        return (error * Kp) + (derivative * Kd) + (integralSum * Ki);
    }

    public double angleWrap(double radians)
    {
        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI) {
            radians += 2 * Math.PI;
        }
        return radians;
    }

    public void power(double output)
    {
        fl.setPower(-output);
        bl.setPower(-output);
        fr.setPower(output);
        br.setPower(output);
    }

    public void initMotors()
    {
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }
}
