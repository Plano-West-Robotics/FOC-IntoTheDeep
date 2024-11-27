package org.firstinspires.ftc.teamcode.IMUTest;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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

@TeleOp(name = "Angle Test")
public class AngleTest extends LinearOpMode
{
    double integralSum = 0;

    // Use dashboard to tune the constants
    double Kp = PIDConstants.Kp;
    double Ki = PIDConstants.Ki;
    double Kd = PIDConstants.Kd;

    ElapsedTime timer = new ElapsedTime();
    public double lastError = 0;

    public IMU imu;
    public IMU.Parameters params;
    Orientation myRobotOrientationDeg;
    Orientation myRobotOrientationRad;
    float yawDeg;
    float yawRad;

    public DcMotor fr, fl, br, bl;

    public boolean g1_y;
    public boolean lastg1_y;

    public static final double DEGREE_ADD = 90.0;

    @Override
    public void runOpMode() throws InterruptedException
    {

        initMotors();

        lastg1_y = gamepad1.y;

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Need to add to the config atm
        imu = hardwareMap.get(IMU.class, "imu");

        // Heading/yaw is for the z axis (upwards) (use thumb trick w/ right hand)
        // We just care about the yaw
        params = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        imu.initialize(params);

        double referenceAngle = Math.toRadians(90);

        waitForStart();

        while(opModeIsActive()){

            g1_y = gamepad1.y;

            referenceAngle = checkRefAngle(referenceAngle, g1_y, lastg1_y);

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

            double powerMotors = PIDControl(referenceAngle, yawRad);

            power(powerMotors, fl, fr, bl, br);

            lastg1_y = g1_y;

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
        while(radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while(radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
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

    public void power(double output, DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight)
    {
        frontLeft.setPower(-output);
        backLeft.setPower(-output);
        frontRight.setPower(output);
        backRight.setPower(output);
    }

    public double checkRefAngle(double refAngle, boolean button, boolean buttonLast)
    {
        if (justPressed(button, buttonLast)) {
            refAngle = Math.toRadians(Math.toDegrees(refAngle) + DEGREE_ADD);
        }

        return refAngle;
    }

    public boolean justPressed(boolean currentInput, boolean lastInput)
    {
        return currentInput && !lastInput;
    }

}